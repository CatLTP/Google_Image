package com.example.googleimage.presentation.image_list.components

import android.util.Log
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.googleimage.R
import com.example.googleimage.domain.model.local.ImageEntity
import com.example.googleimage.presentation.image_list.ImageListScreenEvent
import com.example.googleimage.presentation.image_list.ImageListViewModel
import com.example.googleimage.presentation.image_list.ImageScreenState
import kotlinx.coroutines.flow.emptyFlow

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun ImageListScreen(
    viewModel: ImageListViewModel,
    state: ImageScreenState,
    onClickImageItem: (Int) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    val imageList = state.imageFlow.collectAsLazyPagingItems()
    val lazyGridState = rememberLazyGridState()

    /*
        Handle state of the screen
    */
    if (imageList.loadState.refresh == LoadState.Loading && state.imageFlow != emptyFlow<PagingData<ImageEntity>>()) {
        // if we are loading a query then show a progress indicator
        viewModel.onEvent(ImageListScreenEvent.OnLoadingQuery(true))
    } else {
        // Stop loading and show the result
        viewModel.onEvent(ImageListScreenEvent.OnLoadingQuery(false))
    }

    LaunchedEffect(state.scrollPosition) {
        lazyGridState.scrollToItem(state.scrollPosition)
    }
    Scaffold { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            SearchBar(
                inputField = {
                    SearchBarDefaults.InputField(
                        query = state.searchQuery,
                        onQueryChange = {
                            viewModel.onEvent(ImageListScreenEvent.OnQueryChange(it))
                        },
                        onSearch = {
                            // start searching when we submit the query
                            viewModel.onEvent(ImageListScreenEvent.OnSearchImages(it))
                        },
                        expanded = false,
                        onExpandedChange = {},
                        enabled = true,
                        placeholder = {
                            Text(text = "Search your images here")
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null,
                            )
                        },
                        trailingIcon = null,
                        interactionSource = null,
                    )
                },
                expanded = false,
                onExpandedChange = {},
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentHeight()
                    .fillMaxWidth(),
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner)),
                tonalElevation = 0.dp,
                shadowElevation = 0.dp,
                content = {}
            )
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.Center)
                        .size(100.dp)
                )
            } else {
                Spacer(modifier = Modifier.height(20.dp))
                LazyVerticalGrid(
                    modifier = Modifier.fillMaxSize(),
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.Center,
                    state = lazyGridState,
                ) {
                    items(imageList.itemCount) { index ->
                        if (imageList[index] != null) {
                            ImageItem(
                                image = imageList[index]!!,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .height(220.dp)
                                    .clickable {
                                        //Navigate to image detail screen
                                        onClickImageItem(index)
                                    },
                                sharedTransitionScope = sharedTransitionScope,
                                animatedContentScope = animatedContentScope
                            )
                        }
                    }
                    item(
                        span = {
                            GridItemSpan(2)
                        }
                    ) {
                        if (imageList.loadState.append is LoadState.Loading) {
                            Spacer(modifier = Modifier.height(30.dp))
                            CircularProgressIndicator(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .wrapContentSize(Alignment.Center)
                                    .size(80.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}