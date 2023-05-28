package com.github.imort.news.data

import com.github.imort.news.AppDispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface NewsRepository {
    suspend fun headlines(country: String): Result<TopHeadlines>
    suspend fun article(id: String): Result<Article>
}

internal class NewsRepositoryImpl @Inject constructor(
    private val dispatchers: AppDispatchers,
    private val service: NewsService,
    private val storage: NewsStorage,
) : NewsRepository {
    override suspend fun headlines(country: String) = runCatching {
        withContext(dispatchers.io) {
            service.topHeadlines(country).also {
                if (it.status == Status.ERROR) error("Api returned error")
                it.articles.onEach(storage::put)
            }
        }
    }

    override suspend fun article(id: String): Result<Article> = runCatching {
        storage.get(id) ?: error("Missing article by $id")
    }
}