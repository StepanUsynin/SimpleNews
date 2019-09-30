package com.simplenews.di.module

import android.content.Context
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import okhttp3.OkHttpClient
import dagger.Provides


@Module(includes = [OkHttpModule::class])
class PicassoModule {

    @Provides
    fun picasso(context: Context, downloader: OkHttp3Downloader): Picasso {
        return Picasso.Builder(context).downloader(downloader).build()
    }

    @Provides
    fun okHttp3Downloader(client: OkHttpClient): OkHttp3Downloader {
        return OkHttp3Downloader(client)
    }

}
