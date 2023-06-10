package com.example.storyapp.cacheimage

import android.content.Context
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.cache.LruResourceCache
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class MyAppGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        // Mengatur ukuran cache yang digunakan oleh Glide
        val memoryCacheSizeBytes = 1024 * 1024 * 10 // 10 MB
        builder.setMemoryCache(LruResourceCache(memoryCacheSizeBytes.toLong()))
    }
}