package com.github.imort.news

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.github.imort.news.data.Article
import com.github.imort.news.data.DataModule
import com.github.imort.news.data.NewsService
import com.github.imort.news.data.NewsStorage
import com.github.imort.news.data.NewsStorageImpl
import com.github.imort.news.data.Status
import com.github.imort.news.data.TopHeadlines
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Rule
import org.junit.Test

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@HiltAndroidTest
@UninstallModules(DataModule::class)
class HiltComposeInstrumentedTest {
    @get:Rule(order = 1)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @BindValue
    @JvmField
    val service: NewsService = object : NewsService {
        override suspend fun topHeadlines(country: String): TopHeadlines = TopHeadlines(
            status = Status.OK,
            articles = listOf(Article.preview),
        )
    }

    @BindValue
    @JvmField
    val storage: NewsStorage = NewsStorageImpl()

    @Test
    fun loadAndOpensArticle() {
        with(composeRule) {
            onNodeWithTag("article-preview")
                .performClick()

            onNodeWithTag("image")
                .assertIsDisplayed()
            onNodeWithTag("title")
                .assertIsDisplayed()
                .assertTextEquals("Zelda: Tears of the Kingdom duplication glitch patched out by Nintendo - Polygon")
            onNodeWithTag("description")
                .assertIsDisplayed()
                .assertTextEquals("A new patch for The Legend of Zelda: Tears of Kingdom (version 1.1.2) removes the easy duplication glitches for the Nintendo Switch game.")
            onNodeWithTag("content")
                .assertIsDisplayed()
                .assertTextEquals("A new patch for The Legend of Zelda: Tears of the Kingdom released Thursday addresses a number of glitches in the Switch game that let players duplicate items. Version 1.1.2 for Tears of the Kingdom,… [+1341 chars]")
        }
    }
}