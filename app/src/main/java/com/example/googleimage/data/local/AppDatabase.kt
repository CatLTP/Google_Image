package com.example.googleimage.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.googleimage.domain.model.ImageEntity
import com.example.googleimage.domain.model.SearchParamEntity

@Database(
    entities = [
        ImageEntity::class,
        SearchParamEntity::class,
    ],
    version = 3,
)
abstract class AppDatabase : RoomDatabase() {
    abstract val imageDao: GoogleImageDao
    abstract val searchParamDao: SearchParamsDao
}