package com.simplenews.model

data class ResponseModel(
    val status: String,
    val totalResults: Int,
    val articles: ArrayList<Article>
)