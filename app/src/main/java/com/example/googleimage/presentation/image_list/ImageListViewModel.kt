package com.example.googleimage.presentation.image_list

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.googleimage.data.convert.toGoogleImage
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
    fun getImages(query: String) {
        viewModelScope.launch {
            val imagePagingFlow = repository.getImages(query)
                .flow
                .map { pagingData ->
                    pagingData.map {
                        it.toGoogleImage()
                    }
                }
                .cachedIn(viewModelScope)
            _screenState.value = _screenState.value.copy(
                imageFlow = imagePagingFlow
            )
        }

    }
}