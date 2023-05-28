package com.github.imort.news.data

import androidx.collection.LruCache
import javax.inject.Inject

interface NewsStorage {
    fun put(article: Article)
    fun get(id: String): Article?
}

internal class NewsStorageImpl @Inject constructor() : NewsStorage {
    // for simplicity just use mem storage
    private val cache = LruCache<String, Article>(32)

    override fun put(article: Article) {
        cache.put(article.id, article)
    }

    override fun get(id: String): Article? {
        return cache.get(id)
    }
}
