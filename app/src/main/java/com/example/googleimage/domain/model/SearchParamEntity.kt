package com.example.googleimage.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "param")
data class SearchParamEntity(
    @PrimaryKey val q: String,
    val type: String,
    val page: Int,
    val engine: String,
    val num: Int,
)
