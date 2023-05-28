package com.github.imort.news.data

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertNotNull
import org.junit.Test

class NewsRepositoryTest {
    @Test
    fun savesResultsInStorage() {
        // usually mocks instead of fakes
        val storage = NewsStorageImpl()
        val repo = NewsRepositoryImpl(
            dispatchers = TestAppDispatchers,
            service = TestNewsService,
            storage = storage,
        )
        runBlocking {
            repo.headlines("whatever")
        }
        assertNotNull(storage.get("preview"))
    }
}

object TestNewsService : NewsService {
    override suspend fun topHeadlines(country: String) = topHeadlines
}
