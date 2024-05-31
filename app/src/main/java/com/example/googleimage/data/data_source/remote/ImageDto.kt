package com.example.googleimage.data.data_source.remote

data class ImageDto(
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