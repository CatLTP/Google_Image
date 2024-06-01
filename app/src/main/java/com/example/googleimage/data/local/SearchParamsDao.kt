package com.example.googleimage.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.googleimage.domain.model.SearchParamEntity

@Dao
interface SearchParamsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(params: SearchParamEntity)

    @Query("DELETE FROM param")
    suspend fun clearAll()

    @Query("SELECT * FROM param WHERE q = :query")
    suspend fun getParam(query: String) : SearchParamEntity?
}