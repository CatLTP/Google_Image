package com.example.googleimage.domain.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.googleimage.data.local.AppDatabase
import com.example.googleimage.data.remote.ImageApi
import com.example.googleimage.data.remote.ImageRemoteMediator
import com.example.googleimage.domain.model.local.ImageEntity
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ImageRepository @Inject constructor(
    private val database: AppDatabase,
    private val service: ImageApi,
) {

    suspend fun clearDatabase() {
        database.imageDao.clearAll()
        database.searchParamDao.clearAll()
    }

    fun getImages(query: String): Pager<Int, ImageEntity> {
        val pager = Pager(
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