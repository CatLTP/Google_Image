package com.example.googleimage.domain.model

data class SearchParameterDto(
    val q: String,
    val type: String,
    val page: Int,
    val engine: String,
    val num: Int,
)
