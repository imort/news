package com.github.imort.news.data

interface Endpoint {
    val apiKey: String
    val baseUrl: String
}

internal object NewsEndpoint : Endpoint {
    override val apiKey: String = "024ac5d0a0c440e286b44d8eda033bc1"
    override val baseUrl: String = "https://newsapi.org/v2/"
}