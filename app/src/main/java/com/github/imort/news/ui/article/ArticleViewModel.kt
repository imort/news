package com.github.imort.news.ui.article

import androidx.lifecycle.SavedStateHandle
import com.github.imort.news.data.Article
import com.github.imort.news.data.NewsStorage
import com.github.imort.news.ui.MviViewModel
import com.github.imort.news.ui.article.ArticleContract.Effect
import com.github.imort.news.ui.article.ArticleContract.Event
import com.github.imort.news.ui.article.ArticleContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArticleViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val storage: NewsStorage,
) : MviViewModel<Event, State, Effect>(State()) {
    private val id: String = savedStateHandle["id"] ?: error("Missing id")
    private val stateId = "state-article-$id"

    init {
        load(id)
    }

    override fun handle(event: Event) = when (event) {
        is Event.Return -> send(Effect.Return)
    }

    private fun load(id: String) {
        val article = savedStateHandle.get<Article>(stateId) ?: storage.get(id).also {
            // cause we are lazy and have only mem storage
            // should save current article in case of process recreation
            savedStateHandle[stateId] = it
        }
        update { State(article = article) }
    }
}