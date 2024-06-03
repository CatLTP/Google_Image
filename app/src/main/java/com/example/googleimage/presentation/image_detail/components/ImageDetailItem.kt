package com.example.googleimage.presentation.image_detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.googleimage.R
import com.example.googleimage.domain.model.GoogleImage
import com.example.googleimage.ui.theme.Typography

@Composable
fun ImageDetailItem(
    image: GoogleImage,
    modifier: Modifier
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    Column(
        modifier = modifier
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight / 2),
            model = image.imageUrl,
            contentDescription = null,
            contentScale = ContentScale.FillBounds
        )
        Spacer(
            modifier = Modifier.height(dimensionResource(id = R.dimen.spacer))
        )
        Text(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.screen_padding)),
            text = image.title,
            minLines = 2,
            style = Typography.titleMedium,
        )
        Row(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.screen_padding)),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = "Source: ",
                style = Typography.bodyMedium,
            )
            Text(
                text = image.source,
                style = Typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            )
        }
        Row(
            modifier = Modifier.padding(dimensionResource(id = R.dimen.screen_padding)),
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = "Domain: ",
                style = Typography.bodyMedium,
            )
            Text(
                text = image.domain,
                style = Typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic
            )
        }
    }
}