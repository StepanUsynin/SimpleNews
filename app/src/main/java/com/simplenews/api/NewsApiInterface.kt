package com.simplenews.api

import com.simplenews.Constants
import com.simplenews.model.ResponseModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiInterface {

    @GET("everything")
    fun getNews(
        @Query("q") q: String = "android",
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("apiKey") apiKey: String = Constants.API_KEY,
        @Query("page") page: String
    ): Observable<ResponseModel>

}