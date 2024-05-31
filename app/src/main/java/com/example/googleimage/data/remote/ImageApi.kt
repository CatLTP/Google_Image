package com.example.googleimage.data.remote

import com.example.googleimage.domain.model.ImageDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageApi {
    companion object {
        val BASE_URL : String = "https://google.serper.dev/"
        val API_KEY : String = "427e9f5aad1abb167cd9973023cf5a2e859dde78"
    }

    @GET("images")
    suspend fun getImages(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("num") pageCount: Int,
    ) : ImageDto
}