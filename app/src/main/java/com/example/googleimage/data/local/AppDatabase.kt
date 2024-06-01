package com.example.googleimage.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.googleimage.domain.model.local.ImageEntity
import com.example.googleimage.domain.model.local.SearchParamEntity

@Database(
    entities = [
        ImageEntity::class,
        SearchParamEntity::class,
    ],
    version = 7,
)
abstract class AppDatabase : RoomDatabase() {
    abstract val imageDao: GoogleImageDao
    abstract val searchParamDao: SearchParamsDao
}