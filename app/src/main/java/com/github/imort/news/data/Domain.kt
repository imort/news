package com.github.imort.news.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.util.Date
import java.util.UUID

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
    val id: String = UUID.randomUUID().toString(), // generate cause api missing it
    val author: String?,
    val title: String?,
    val description: String?,
    val urlToImage: String?,
    val publishedAt: Date,
) {
    companion object {
        val preview
            get() = Article(
                id = "preview",
                author = "Michael McWhertor",
                title = "Zelda: Tears of the Kingdom duplication glitch patched out by Nintendo - Polygon",
                description = "A new patch for The Legend of Zelda: Tears of Kingdom (version 1.1.2) removes the easy duplication glitches for the Nintendo Switch game.",
                urlToImage = "https://cdn.vox-cdn.com/thumbor/sEbIZdRAR4LwupSgsyMqcc_DtPw=/0x0:1920x1005/fit-in/1200x630/cdn.vox-cdn.com/uploads/chorus_asset/file/24678066/totk_return_to_hyrule_alt.jpg",
                publishedAt = Date(1685109395000),
            )
    }
}