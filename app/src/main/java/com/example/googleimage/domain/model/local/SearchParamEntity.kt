package com.example.googleimage.domain.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "param")
data class SearchParamEntity(
    val q: String,
    val type: String,
    @PrimaryKey val page: Int,
    val engine: String,
    val num: Int,
)