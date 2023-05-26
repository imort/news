package com.github.imort.news.data

import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("top-headlines")
    suspend fun topHeadlines(@Query("country") country: String): TopHeadlines
}