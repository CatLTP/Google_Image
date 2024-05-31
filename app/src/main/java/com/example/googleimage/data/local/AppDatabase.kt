package com.example.googleimage.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.googleimage.domain.model.ImageEntity

@Database(entities = [ImageEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract val imageDao: GoogleImageDao
}