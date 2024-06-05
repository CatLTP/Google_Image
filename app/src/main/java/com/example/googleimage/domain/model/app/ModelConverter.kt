package com.example.googleimage.domain.model.app

import com.example.googleimage.domain.model.app.GoogleImage
import com.example.googleimage.domain.model.local.ImageEntity
import com.example.googleimage.domain.model.local.SearchParamEntity
import com.example.googleimage.domain.model.remote.ImageDto
import com.example.googleimage.domain.model.remote.SearchParamDto

fun ImageDto.toImageEntity(searchParam: SearchParamDto): ImageEntity {
    return ImageEntity(
        title = title,
        imageUrl = imageUrl,
        imageWidth = imageWidth,
        imageHeight = imageHeight,
        thumbnailUrl = thumbnailUrl,
        thumbnailWidth = thumbnailWidth,
        thumbnailHeight = thumbnailHeight,
        source = source,
        domain = domain,
        link = link,
        googleUrl = googleUrl,
        position = position,
        pageNum = searchParam.page,
    )
}

fun SearchParamDto.toSearchParamEntity(): SearchParamEntity {
    return SearchParamEntity(
        q = q,
        type = type,
        page = page,
        engine = engine,
        num = num,
    )
}

fun ImageEntity.toGoogleImage() : GoogleImage {
    return GoogleImage(
        id = id!!,
        title = title,
        imageUrl = imageUrl,
        imageWidth = imageWidth,
        imageHeight = imageHeight,
        thumbnailUrl = thumbnailUrl,
        thumbnailWidth = thumbnailWidth,
        thumbnailHeight = thumbnailHeight,
        source = source,
        domain = domain,
        link = link,
        googleUrl = googleUrl,
        position = position,
        pageNum = pageNum,
    )
}

