package com.github.imort.news.data

import javax.inject.Inject

interface Endpoint {
    val apiKey: String
    val baseUrl: String
}

internal class NewsEndpoint @Inject constructor() : Endpoint {
    override val apiKey: String = "024ac5d0a0c440e286b44d8eda033bc1"
    override val baseUrl: String = "https://newsapi.org/v2/"
}