package com.example.googleimage.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.googleimage.domain.model.ImageEntity

@Dao
interface GoogleImageDao {

    @Query("DELETE FROM images")
    suspend fun clearAll()

    @Query("SELECT * FROM images")
    fun pagingSource(): PagingSource<Int, ImageEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(images: List<ImageEntity>)
}