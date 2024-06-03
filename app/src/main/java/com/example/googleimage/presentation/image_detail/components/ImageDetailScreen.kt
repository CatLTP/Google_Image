package com.example.googleimage.presentation.image_detail.components

import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.googleimage.presentation.image_detail.ImageDetailScreenState

@Composable
fun ImageDetailScreen(
    state: ImageDetailScreenState,
) {

    val image = state.imageFlow.collectAsLazyPagingItems()

    Scaffold { paddingValue ->
        LazyRow(
            modifier = Modifier.padding(paddingValue)
        ) {
            items(image.itemCount) { index ->
                if (image[index] != null) {
                    ImageDetailItem(image = image[index]!!)
                }
            }
        }
    }
}