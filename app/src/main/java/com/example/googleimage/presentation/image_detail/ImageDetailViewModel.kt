package com.example.googleimage.presentation.image_detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.googleimage.domain.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ImageDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    repository: ImageRepository
) : ViewModel() {

    private val _screenState = MutableStateFlow(ImageDetailScreenState())
    val screenState = _screenState.asStateFlow()

    init {
        val id : Int = savedStateHandle["id"]!!
        // Initialize the current images flow
        val imagePagingFlow = repository.pager
            .flow
            .cachedIn(viewModelScope)
        _screenState.value = _screenState.value.copy(
            imageFlow = imagePagingFlow,
            currentId = id
        )
    }
    fun onEvent(event: ImageDetailScreenEvent) {
        when (event) {
            is ImageDetailScreenEvent.OnLoadImage -> {
                onLoadImage(event.id)
            }
        }
    }

    private fun onLoadImage(id: Int) {

    }
}