package com.example.googleimage

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.disk.DiskCache
import coil.memory.MemoryCache
import coil.request.CachePolicy
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class GoogleImageApp : Application(), ImageLoaderFactory {

    //Implementing our own ImageLoader for caching images using Coil
    override fun newImageLoader(): ImageLoader {
        return ImageLoader(this)
            .newBuilder()
            /*Memory cache are enabled by default in Coil
            But we have to make sure in case of future change*/
            .memoryCachePolicy(CachePolicy.ENABLED)
            .memoryCache {
                MemoryCache.Builder(this)
                    .maxSizePercent(0.1) // only using 10% of memory size for memory cache
                    .strongReferencesEnabled(enable = true) // stop the garbage collector from erase the cache
                    .build()
            }
            /*Disk cache are enabled by default in Coil
            But we have to make sure in case of future change*/
            .diskCachePolicy(CachePolicy.ENABLED)
            .diskCache {
                DiskCache.Builder()
                    .maxSizePercent(0.05)
                    .directory(cacheDir) // where to store the cache
                    .build()
            }
            .build()
    }
}