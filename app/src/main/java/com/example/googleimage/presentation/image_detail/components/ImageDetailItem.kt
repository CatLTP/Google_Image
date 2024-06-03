package com.example.googleimage.presentation.image_detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import coil.compose.AsyncImage
import com.example.googleimage.domain.model.local.ImageEntity

@Composable
fun ImageDetailItem(
    image: ImageEntity
) {
    Column {
        AsyncImage(model = image.imageUrl, contentDescription = null)
    }
}