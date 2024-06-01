package com.example.googleimage.domain.model.remote

import com.example.googleimage.domain.model.local.ImageEntity
import com.example.googleimage.domain.model.local.SearchParamEntity

data class ResponseDto(
    val searchParameters: SearchParamDto,
    val images: List<ImageDto>,
)
