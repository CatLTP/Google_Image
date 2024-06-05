package com.example.googleimage.data.remote

import android.net.http.HttpException
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.googleimage.data.local.AppDatabase
import com.example.googleimage.domain.model.local.ImageEntity
import com.example.googleimage.domain.model.app.toImageEntity
import com.example.googleimage.domain.model.app.toSearchParamEntity
import java.io.IOException
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ImageRemoteMediator @Inject constructor(
    private val database: AppDatabase,
    private val service: ImageApi,
    private val query: String,
) : RemoteMediator<Int, ImageEntity>() {


    override suspend fun initialize(): InitializeAction {
        val cachedData = database.searchParamDao.getParam()

        // We only need to call api when there is no cache data currently
        return if (cachedData != null) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ImageEntity>
    ): MediatorResult {
        return try {
            // Figure out which page to load
            val loadKey = when (loadType) {
                // If UI is refreshed then start from the beginning
                LoadType.REFRESH -> 1
                // Since REFRESH start from first page and then scroll down, we don't need to prepend
                LoadType.PREPEND ->
                    return MediatorResult.Success(
                        endOfPaginationReached = true
                    )
                // if remote key is null, it means we are at the first page since there are cached
                // record of the key
                LoadType.APPEND -> {
                    val remoteKey = database.withTransaction {
                        database.searchParamDao.getParam()
                    }
                    if (remoteKey.isNullOrEmpty()) {
                        1
                    } else {
                        val page = remoteKey.last().page
                        page + 1
                    }
                }
            }

            // call API to load data
            // Retrofit will dispatch the call on a worker thread so UI will intact
            val response = service.getImages(
                query = query,
                page = loadKey,
                pageCount = 10,
            )


            //Cache the data using Room
            database.withTransaction {
                val param = database.searchParamDao.getParam()
                //We clear the cached data of the previous query
                if (loadType == LoadType.REFRESH || (!param.isNullOrEmpty() && param.first().q != query)) {
                    database.imageDao.clearAll()
                    database.searchParamDao.clearAll()
                }
                //insert new updates to local database for caching
                val searchParam = response.searchParameters.toSearchParamEntity()
                database.searchParamDao.insert(searchParam)

                val imageList = response.images.map {
                    it.toImageEntity(response.searchParameters)
                }
                database.imageDao.insertAll(imageList)
            }

            MediatorResult.Success(
                endOfPaginationReached = response.images.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

}