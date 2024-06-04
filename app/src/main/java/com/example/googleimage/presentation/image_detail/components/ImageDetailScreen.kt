package com.example.googleimage.presentation.image_detail.components

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.googleimage.presentation.image_detail.ImageDetailScreenState


@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun ImageDetailScreen(
    state: ImageDetailScreenState,
    onClickWebNavigateButton: (String) -> Unit,
    onBackButtonPressed: (Int) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {

    val image = state.imageFlow.collectAsLazyPagingItems()
    val beyondViewPageCount = 2

    //Init pager state for HorizontalPager component
    val pagerState = rememberPagerState(
        initialPage = state.currentItem,
        pageCount = { image.itemCount }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = state.title)
                },
                navigationIcon = {
                    //Back button on the app bar
                    IconButton(
                        onClick = { onBackButtonPressed(pagerState.currentPage) }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValue ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
        ) {
            // Image's detail lists
            HorizontalPager(
                state = pagerState,
                beyondViewportPageCount = beyondViewPageCount
            ) { index ->
                if (image[index] != null) {
                    ImageDetailItem(
                        image = image[index]!!,
                        modifier = Modifier
                            .fillMaxSize(),
                        sharedTransitionScope,
                        animatedContentScope,
                        onClickWebNavigateButton,
                    )
                }
            }
            //Override back button
            BackHandler {
                onBackButtonPressed(pagerState.currentPage)
            }
        }
    }
}