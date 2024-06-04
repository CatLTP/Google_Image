package com.example.googleimage.domain.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.googleimage.data.local.AppDatabase
import com.example.googleimage.data.remote.ImageApi
import com.example.googleimage.data.remote.ImageRemoteMediator
import com.example.googleimage.domain.model.local.ImageEntity
import com.example.googleimage.domain.model.local.SearchParamEntity
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ImageRepository @Inject constructor(
    private val database: AppDatabase,
    private val service: ImageApi,
) {

    var pager: Pager<Int, ImageEntity>
        private set

    init {
        pager = Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                database.imageDao.pagingSource()
            },
            initialKey = 1,
            remoteMediator = ImageRemoteMediator(
                database = database,
                service = service,
                query = "",
            )
        )
    }

    //clear the whole database
    suspend fun clearDatabase() {
        database.imageDao.clearAll()
        database.searchParamDao.clearAll()
    }

    //get the latest search param
    suspend fun getSearchParam(): SearchParamEntity? {
        return if (database.searchParamDao.getParam().isNullOrEmpty()) {
            null
        } else {
            database.searchParamDao.getParam()?.first()
        }
    }

    fun getImages(query: String): Pager<Int, ImageEntity> {

        pager = Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                database.imageDao.pagingSource()
            },
            remoteMediator = ImageRemoteMediator(
                database = database,
                service = service,
                query = query,
            )
        )
        return pager
    }
}