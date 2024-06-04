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
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.googleimage.R
import com.example.googleimage.presentation.image_detail.ImageDetailScreenEvent
import com.example.googleimage.presentation.image_detail.ImageDetailScreenState
import com.example.googleimage.presentation.image_detail.ImageDetailViewModel
import com.example.googleimage.ui.theme.Typography


@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class)
@Composable
fun ImageDetailScreen(
    viewModel: ImageDetailViewModel,
    state: ImageDetailScreenState,
    onClickWebNavigateButton: (String) -> Unit,
    onBackButtonPressed: (Int) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {

    val image = state.imageFlow.collectAsLazyPagingItems()

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
                        onClick = { onBackButtonPressed(state.currentItem) }
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
            ) { index ->
                if (image[index] != null) {
                    viewModel.onEvent(ImageDetailScreenEvent.OnSwipe(index))
                    ImageDetailItem(
                        image = image[index]!!,
                        modifier = Modifier
                            .fillMaxSize(),
                        sharedTransitionScope,
                        animatedContentScope,
                    )
                }
            }
            //Web navigation's button
            Button(
                modifier = Modifier
                    .padding(bottom = dimensionResource(id = R.dimen.screen_padding))
                    .align(Alignment.BottomCenter),
                onClick = {
                    onClickWebNavigateButton(image[state.currentItem]!!.link)
                },
            ) {
                Text(
                    text = stringResource(id = R.string.web_navigate),
                    style = Typography.titleLarge,
                )
            }

            //Override back button
            BackHandler {
                onBackButtonPressed(state.currentItem)
            }
        }
    }
}