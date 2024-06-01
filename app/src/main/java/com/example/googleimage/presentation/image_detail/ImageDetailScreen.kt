package com.example.googleimage.presentation.image_detail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ImageDetailScreen(
    viewModel: ImageDetailViewModel,
) {
    Scaffold { paddingValue ->
        Column(
            modifier = Modifier.padding(paddingValues = paddingValue)
        ) {
            Text(text = "123")
        }
    }
}