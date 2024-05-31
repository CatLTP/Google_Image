package com.example.googleimage.domain.model

data class ImageDto(
    val searchParameters: SearchParameterDto,
    val images: List<ImageEntity>,
)
