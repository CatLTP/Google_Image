package com.example.googleimage.presentation.image_detail

import androidx.paging.PagingData
import com.example.googleimage.domain.model.GoogleImage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class ImageDetailScreenState(
    val currentItem: Int = 0,
    val imageFlow: Flow<PagingData<GoogleImage>> = emptyFlow(),
    val title: String = "",
)