package com.example.googleimage.presentation.image_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.googleimage.domain.model.toGoogleImage
import com.example.googleimage.domain.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    repository: ImageRepository
) : ViewModel() {

    private val _screenState = MutableStateFlow(ImageDetailScreenState())
    val screenState = _screenState.asStateFlow()

    init {
        //get the title for the detail screen
        viewModelScope.launch {
            val title = repository.getSearchParam()?.q
            if (title != null) {
                _screenState.value = _screenState.value.copy(
                    title = title
                )
            }
        }

        //get the selected image's index
        val index : Int = savedStateHandle["index"]!!
        // Initialize the current images flow
        val imagePagingFlow = repository.pager
            .flow
            .map { pagingData ->
                pagingData.map {
                    it.toGoogleImage()
                }
            }
            .cachedIn(viewModelScope)
        _screenState.value = _screenState.value.copy(
            imageFlow = imagePagingFlow,
            currentItem = index,
        )
    }
    fun onEvent(event: ImageDetailScreenEvent) {
       when (event) {
           is ImageDetailScreenEvent.OnSwipe -> {
               onSwipe(event.id)
           }
       }
    }

    private fun onSwipe(id: Int) {
        _screenState.value = _screenState.value.copy(
            currentItem = id
        )
    }
}