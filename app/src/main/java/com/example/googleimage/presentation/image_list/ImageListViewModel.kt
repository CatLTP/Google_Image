package com.example.googleimage.presentation.image_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.googleimage.domain.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageListViewModel @Inject constructor(
    private val repository: ImageRepository
) : ViewModel() {

    private val _screenState = MutableStateFlow(ImageScreenState())
    val screenState = _screenState.asStateFlow()

    fun onEvent(event: ImageListScreenEvent) {
        when (event) {
            is ImageListScreenEvent.OnSearchImages -> {
                getImages(query = event.query)
            }

            is ImageListScreenEvent.OnQueryChange -> {
                onQueryChange(query = event.query)
            }
        }
    }

    private fun onQueryChange(query: String) {
        _screenState.value = _screenState.value.copy(
            searchQuery = query
        )
    }

    private fun getImages(query: String) {
        viewModelScope.launch {
            repository.clearDatabase()
            val imagePagingFlow = repository.getImages(query)
                .flow
                .cachedIn(viewModelScope)
            _screenState.value = _screenState.value.copy(
                imageFlow = imagePagingFlow,
            )
        }
    }
}