package com.example.googleimage.presentation.image_detail

import androidx.paging.PagingData
import com.example.googleimage.domain.model.local.ImageEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class ImageDetailScreenState(
    val currentId: Int = 0,
    val imageFlow: Flow<PagingData<ImageEntity>> = emptyFlow(),
)