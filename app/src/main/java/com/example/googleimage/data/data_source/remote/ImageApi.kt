package com.example.googleimage.data.data_source.remote

import retrofit2.http.GET

interface ImageApi {
    companion object {
        val BASE_URL : String = "https://google.serper.dev/"
        val API_KEY : String = "427e9f5aad1abb167cd9973023cf5a2e859dde78"
    }

    @GET("images")
    suspend fun getImages()
}