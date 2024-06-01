package com.example.googleimage.presentation.image_list

import androidx.paging.PagingData
import com.example.googleimage.domain.model.local.ImageEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class ImageScreenState(
    val imageFlow: Flow<PagingData<ImageEntity>> = emptyFlow(),
    val searchQuery: String = "",
)