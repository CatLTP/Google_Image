package com.example.googleimage.data.convert

import com.example.googleimage.domain.model.GoogleImage
import com.example.googleimage.domain.model.ImageEntity

fun ImageEntity.toGoogleImage(): GoogleImage {
    return GoogleImage(
        title,
        imageUrl,
        imageWidth,
        imageHeight,
        thumbnailUrl,
        thumbnailWidth,
        thumbnailHeight,
        source,
        domain,
        link,
        googleUrl,
        position
    )
}