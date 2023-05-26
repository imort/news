package com.github.imort.news.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date

@JsonClass(generateAdapter = false)
enum class Status {
    @Json(name = "ok")
    OK,

    @Json(name = "error")
    ERROR,
}

@JsonClass(generateAdapter = true)
data class TopHeadlines(
    val status: Status,
    val articles: List<Article> = emptyList(),
)

@JsonClass(generateAdapter = true)
data class Article(
    val author: String?,
    val title: String?,
    val description: String?,
    val urlToImage: String?,
    val publishedAt: Date,
)