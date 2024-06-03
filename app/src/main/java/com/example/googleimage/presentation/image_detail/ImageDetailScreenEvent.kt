package com.example.googleimage.presentation.image_detail

sealed class ImageDetailScreenEvent {
    data class OnSwipe(val id: Int) : ImageDetailScreenEvent()
}