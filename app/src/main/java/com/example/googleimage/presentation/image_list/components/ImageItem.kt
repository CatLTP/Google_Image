package com.example.googleimage.presentation.image_list.components

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.BoundsTransform
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.animation.core.ArcMode
import androidx.compose.animation.core.ExperimentalAnimationSpecApi
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.keyframes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.integerResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.googleimage.MainActivity
import com.example.googleimage.R
import com.example.googleimage.domain.model.GoogleImage


@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalAnimationSpecApi::class)
@Composable
fun ImageItem(
    image: GoogleImage,
    modifier: Modifier,
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope
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

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner))
    ) {
        with(sharedTransitionScope) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(
                        shape = RoundedCornerShape(dimensionResource(id = R.dimen.rounded_corner))
                    )
                    .sharedElement(
                        sharedTransitionScope.rememberSharedContentState(key = MainActivity.CacheKey.getImageKey(image.id)),
                        animatedVisibilityScope = animatedContentScope,
                        //override overlay to match image layout
                        clipInOverlayDuringTransition = OverlayClip(RoundedCornerShape(20.dp)),
                        boundsTransform = boundsTransformTime
                    ),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image.imageUrl)
                    .crossfade(true)
                    .placeholderMemoryCacheKey(MainActivity.CacheKey.getImageKey(image.id)) //  same key as shared element key
                    .memoryCacheKey(MainActivity.CacheKey.getImageKey(image.id)) // same key as shared element key
                    .build(),
                contentScale = ContentScale.FillBounds,
                contentDescription = null,
            )
        }
    }
}