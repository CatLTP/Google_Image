package com.example.googleimage.presentation.image_list

sealed class ImageListScreenEvent {
    data class OnQueryChange(val query: String): ImageListScreenEvent()
    data class OnSearchImages(val query: String): ImageListScreenEvent()
}