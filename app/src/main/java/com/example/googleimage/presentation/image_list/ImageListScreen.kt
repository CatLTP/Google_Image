package com.example.googleimage.presentation.image_list

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.googleimage.R
import com.example.googleimage.domain.model.GoogleImage
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageListScreen(
    viewModel: ImageListViewModel,
    state: ImageScreenState,
) {
    var searchQuery by remember {
        mutableStateOf("")
    }
    val imageList = state.imageFlow.collectAsLazyPagingItems()

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            SearchBar(
                modifier = Modifier
                    .padding(10.dp)
                    .wrapContentHeight()
                    .wrapContentWidth(),
                placeholder = {
                    Text(text = "Search your images here")
                },
                active = true,
                onActiveChange = {},
                query = searchQuery,
                onSearch = {
                    viewModel.getImages(searchQuery)
                },
                onQueryChange = {
                    searchQuery = it
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                    )
                },
                tonalElevation = 0.dp,
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner)),
                content = {
                    Spacer(modifier = Modifier.height(20.dp))
                    LazyVerticalGrid(
                        modifier = Modifier.fillMaxSize(),
                        columns = GridCells.Fixed(2),
                    ) {
                        items(imageList.itemCount) { index ->
                            if (imageList[index] != null) {
                                ImageItem(
                                    image = imageList[index]!!,
                                    modifier = Modifier.padding(10.dp).height(220.dp)
                                )
                            }
                        }
                        item {
                            if (imageList.loadState.append is LoadState.Loading) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                },
            )

        }
    }
}