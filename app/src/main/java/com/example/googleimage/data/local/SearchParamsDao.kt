package com.example.googleimage.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.googleimage.domain.model.local.SearchParamEntity

@Dao
interface SearchParamsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(params: SearchParamEntity)

    @Query("DELETE FROM param")
    suspend fun clearAll()

    @Query("SELECT * FROM param")
    suspend fun getParam() : List<SearchParamEntity>?
}