package com.example.googleimage.di

import com.example.googleimage.data.local.AppDatabase
import com.example.googleimage.data.local.GoogleImageDao
import com.example.googleimage.data.remote.ImageApi
import com.example.googleimage.domain.repository.ImageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideImageRepository(
        imageDao: GoogleImageDao,
        database: AppDatabase,
        service: ImageApi,
    ): ImageRepository = ImageRepository(imageDao, database, service)
}