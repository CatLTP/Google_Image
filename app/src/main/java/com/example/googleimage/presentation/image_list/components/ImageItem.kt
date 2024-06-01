package com.example.googleimage.presentation.image_list.components

import android.util.TypedValue
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.googleimage.R
import com.example.googleimage.domain.model.ImageEntity


@Composable
fun ImageItem(
    image: ImageEntity,
    modifier: Modifier,
) {
    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner))
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxWidth().height(150.dp),
            model = image.imageUrl,
            contentScale = ContentScale.FillBounds,
            contentDescription = null,
        )
        Text(
            modifier = Modifier.padding(8.dp),
            text = image.title,
            maxLines = 3,
            minLines = 2,
            overflow = TextOverflow.Ellipsis,
            style = TextStyle(
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
            )
        )
    }
}