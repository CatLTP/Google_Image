package com.example.googleimage.domain.model.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "images",
    foreignKeys = [ForeignKey(
        entity = SearchParamEntity::class,
        parentColumns = arrayOf("page"),
        childColumns = arrayOf("pageNum"),
        onDelete = ForeignKey.CASCADE,
    )]
)
data class ImageEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val pageNum: Int,
    val title: String,
    val imageUrl: String,
    val imageWidth: Int,
    val imageHeight: Int,
    val thumbnailUrl: String,
    val thumbnailWidth: String,
    val thumbnailHeight: String,
    val source: String,
    val domain: String,
    val link: String,
    val googleUrl: String,
    val position: Int,
)