package com.example.googleimage.domain.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.googleimage.data.local.AppDatabase
import com.example.googleimage.data.local.GoogleImageDao
import com.example.googleimage.data.remote.ImageApi
import com.example.googleimage.data.remote.ImageRemoteMediator
import com.example.googleimage.domain.model.ImageEntity
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ImageRepository @Inject constructor(
    private val imageDao: GoogleImageDao,
    private val database: AppDatabase,
    private val service: ImageApi,
) {

    suspend fun clearDatabase() {
        imageDao.clearAll()
        Log.i("HELLO WORLD", "DONE")
    }

    fun getImages(query: String): Pager<Int, ImageEntity> {
        val pager = Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                imageDao.pagingSource()
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