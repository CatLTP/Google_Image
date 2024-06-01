package com.example.googleimage.domain.model.remote

data class SearchParamDto(
    val q: String,
    val type: String,
    val page: Int,
    val engine: String,
    val num: Int,
)
