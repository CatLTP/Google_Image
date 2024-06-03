package com.example.googleimage.presentation.image_list

import androidx.paging.PagingData
import com.example.googleimage.domain.model.GoogleImage
import com.example.googleimage.domain.model.local.ImageEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

data class ImageScreenState(
    val imageFlow: Flow<PagingData<GoogleImage>> = emptyFlow(),
    val searchQuery: String = "",
    val isLoading : Boolean = false,
    val scrollPosition: Int = 0,
)