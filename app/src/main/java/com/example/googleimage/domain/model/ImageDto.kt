package com.example.googleimage.domain.model

data class ImageDto(
    val searchParameters: SearchParamEntity,
    val images: List<ImageEntity>,
)
