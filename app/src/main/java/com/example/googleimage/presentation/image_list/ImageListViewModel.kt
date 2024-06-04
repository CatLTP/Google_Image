package com.example.googleimage.presentation.image_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.googleimage.domain.model.toGoogleImage
import com.example.googleimage.domain.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageListViewModel @Inject constructor(
    private val repository: ImageRepository
) : ViewModel() {

    private val _screenState = MutableStateFlow(ImageScreenState())
    val screenState = _screenState.asStateFlow()

    private var getImageJob : Job? = null

    init {
        //Initialize new images list flow
        viewModelScope.launch {
            val searchParam = repository.getSearchParam()

            val imagePagingFlow =
                if (searchParam != null)
                    repository
                        .getImages(searchParam.q)
                        .flow
                        .map { pagingData ->
                            pagingData.map {
                                it.toGoogleImage()
                            }
                        }
                        .cachedIn(viewModelScope)
                else
                    emptyFlow()
            _screenState.value = _screenState.value.copy(
                imageFlow = imagePagingFlow,
            )
        }
    }

    //One focused function to determine what to do
    fun onEvent(event: ImageListScreenEvent) {
        when (event) {
            is ImageListScreenEvent.OnSearchImages -> {
                getImages(query = event.query)
            }

            is ImageListScreenEvent.OnQueryChange -> {
                onQueryChange(query = event.query)
            }

            is ImageListScreenEvent.OnLoadingQuery -> {
                onLoadingQuery(event.loading)
            }
            is ImageListScreenEvent.OnNavigateBack -> {
                onNavigateBack(event.index)
            }
        }
    }

    //Update the query on search bar when user is typing
    private fun onQueryChange(query: String) {
        _screenState.value = _screenState.value.copy(
            searchQuery = query
        )
    }

    //On user navigates back to list screen
    private fun onNavigateBack(index: Int) {
        _screenState.value = _screenState.value.copy(
            scrollPosition = index
        )
    }

    //Update the screen state when viewmodel is loading for data
    private fun onLoadingQuery(isLoading: Boolean) {
        _screenState.value = _screenState.value.copy(
            isLoading = isLoading,
        )
    }

    //Get images from user's query
    private fun getImages(query: String) {
        //One call at a time
        getImageJob?.cancel()

        getImageJob = viewModelScope.launch {
            //Clear the current database since we only cache the latest query result
            repository.clearDatabase()

            //Initialize new images list flow
            val imagePagingFlow = repository.getImages(query)
                .flow
                .map { pagingData ->
                    pagingData.map {
                        it.toGoogleImage()
                    }
                }
                .cachedIn(viewModelScope)
            _screenState.value = _screenState.value.copy(
                imageFlow = imagePagingFlow,
            )
        }
    }
}