package com.test.simplenews.api

import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiServiceInterface {

    @GET("everything")
    fun getNews(
        @Query("q") q: String = "android",
        @Query("sortBy") sortBy: String = "publi%C2%A0%20%20shedAt",
        @Query("apiKey") apiKey: String = KEY,
        @Query("page") page: String
    )

    companion object Factory {

        private const val BASE_URL = "https://newsapi.org/v2/"
        private const val KEY = "bd6895ac08e14f04989f61e69c060cec"

        fun create(): ApiServiceInterface {
            val retrofit = retrofit2.Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(ApiServiceInterface::class.java)
        }

    }
}