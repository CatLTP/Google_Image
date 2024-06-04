package com.example.googleimage.presentation.image_detail.components

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.ArcMode
import androidx.compose.animation.core.ExperimentalAnimationSpecApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.googleimage.R
import com.example.googleimage.domain.model.GoogleImage
import com.example.googleimage.ui.theme.Typography

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalAnimationSpecApi::class)
@Composable
fun ImageDetailItem(
    image: GoogleImage,
    modifier: Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    val transitionTime = integerResource(id = R.integer.transition_time)
    //Customize transition time
    val boundsTransformTime = BoundsTransform { initialBounds, targetBounds ->
        keyframes {
            durationMillis = transitionTime
            initialBounds at 0 using ArcMode.ArcBelow using FastOutSlowInEasing
            targetBounds at transitionTime
        }
    }

    with(sharedTransitionScope) {
        Column(
            modifier = modifier
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight / 2)
                    .sharedBounds(
                        sharedTransitionScope.rememberSharedContentState(key = "image-${image.id}"),
                        animatedVisibilityScope = animatedContentScope,
                        boundsTransform = boundsTransformTime
                    ),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image.imageUrl)
                    .crossfade(true)
                    .placeholderMemoryCacheKey("image-${image.id}") //  same key as shared element key
                    .memoryCacheKey("image-${image.id}") // same key as shared element key
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
            Spacer(
                modifier = Modifier.height(dimensionResource(id = R.dimen.spacer))
            )
            Text(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.screen_padding))
                    .fillMaxWidth()
                    .sharedBounds(
                        sharedTransitionScope.rememberSharedContentState(key = "text-${image.id}"),
                        animatedVisibilityScope = animatedContentScope,
                        resizeMode = SharedTransitionScope.ResizeMode.RemeasureToBounds,
                        boundsTransform = boundsTransformTime,
                    ),
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
                    fontStyle = FontStyle.Italic,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
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
                    fontStyle = FontStyle.Italic,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}