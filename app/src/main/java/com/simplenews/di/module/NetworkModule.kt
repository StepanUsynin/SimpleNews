package com.simplenews.di.module

import com.simplenews.Constants
import com.simplenews.api.NewsApiInterface
import retrofit2.converter.gson.GsonConverterFactory
import dagger.Provides
import dagger.Module
import retrofit2.Retrofit
import okhttp3.OkHttpClient
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory


@Module(includes = [OkHttpModule::class])
class NetworkModule {

    @Provides
    fun getApiInterface(retrofit: Retrofit): NewsApiInterface {
        return retrofit.create(NewsApiInterface::class.java)
    }

    @Provides
    fun getRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

}