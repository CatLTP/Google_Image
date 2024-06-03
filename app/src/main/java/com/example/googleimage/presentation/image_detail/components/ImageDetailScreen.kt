package com.example.googleimage.presentation.image_detail.components

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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


@Composable
fun ImageDetailScreen(
    viewModel: ImageDetailViewModel,
    state: ImageDetailScreenState,
    onClickWebNavigateButton: (String) -> Unit,
    onBackButtonPressed: (Int) -> Unit,
) {

    val image = state.imageFlow.collectAsLazyPagingItems()

    //Init pager state for HorizontalPager component
    val pagerState = rememberPagerState(
        initialPage = state.currentItem,
        pageCount = { image.itemCount }
    )

    Scaffold { paddingValue ->
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
                            .fillMaxSize()
                    )
                }
            }
            //Web navigation's button
            Button(
                modifier = Modifier
                    .padding(bottom = dimensionResource(id = R.dimen.spacer))
                    .align(Alignment.BottomCenter),
                onClick = {
                    onClickWebNavigateButton(image[state.currentItem]!!.link)
                },
            ) {
                Text(
                    text = stringResource(id = R.string.web_navigate),
                    style = Typography.titleMedium,
                )
            }

            //Override back button
            BackHandler {
                onBackButtonPressed(state.currentItem)
            }
        }
    }
}