package com.example.googleimage.presentation.image_detail

sealed class ImageDetailScreenEvent {
    data class OnLoadImage(val id: Int) : ImageDetailScreenEvent()
}