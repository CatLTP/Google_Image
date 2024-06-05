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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.googleimage.MainActivity
import com.example.googleimage.R
import com.example.googleimage.domain.model.app.GoogleImage
import com.example.googleimage.ui.theme.Typography

@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalAnimationSpecApi::class)
@Composable
fun ImageDetailItem(
    image: GoogleImage,
    modifier: Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    onClickWebNavigateButton: (String) -> Unit,
    shouldPlayTransition: Boolean, //Only play transition for the currently viewed image
) {
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
                modifier = if (shouldPlayTransition) {
                    Modifier
                        .fillMaxWidth()
                        .weight(9f)
                        .sharedBounds(
                            sharedTransitionScope.rememberSharedContentState(
                                MainActivity.CacheKey.getImageKey(
                                    image.id
                                )
                            ),
                            animatedVisibilityScope = animatedContentScope,
                            boundsTransform = boundsTransformTime
                        )
                } else {
                    Modifier
                        .fillMaxWidth()
                        .weight(9f)
                },
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image.imageUrl)
                    .crossfade(true)
                    .placeholderMemoryCacheKey(MainActivity.CacheKey.getImageKey(image.id)) //  same key as shared element key
                    .memoryCacheKey(MainActivity.CacheKey.getImageKey(image.id)) // same key as shared element key
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                filterQuality = FilterQuality.High
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer)))
            //Web navigation's button
            Button(
                modifier = Modifier
                    .padding(bottom = dimensionResource(id = R.dimen.screen_padding))
                    .weight(1f)
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    onClickWebNavigateButton(image.link)
                },
            ) {
                Text(
                    text = stringResource(id = R.string.web_navigate),
                    style = Typography.titleLarge,
                )
            }
        }
    }
}