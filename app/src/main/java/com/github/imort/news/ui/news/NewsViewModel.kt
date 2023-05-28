package com.github.imort.news.ui.news

import androidx.lifecycle.viewModelScope
import com.github.imort.news.data.Article
import com.github.imort.news.data.NewsRepository
import com.github.imort.news.data.Source
import com.github.imort.news.ui.MviViewModel
import com.github.imort.news.ui.news.NewsContract.Effect
import com.github.imort.news.ui.news.NewsContract.Event
import com.github.imort.news.ui.news.NewsContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val source: Source,
    private val repo: NewsRepository,
) : MviViewModel<Event, State, Effect>() {

    init {
        update { copy(country = source.country) }
        load()
    }

    override fun initialState() = State(
        country = "",
        loading = true,
        articles = emptyList(),
        error = false,
    )

    override fun handle(event: Event) = when (event) {
        is Event.Retry -> load()
        is Event.Select -> send(Effect.Select(event.article))
    }

    private fun load() {
        viewModelScope.launch {
            update { copy(loading = true) }
            repo.headlines(source.country)
                .map { headlines ->
                    headlines.copy(articles = headlines.articles.sortedBy(Article::publishedAt))
                }
                .onSuccess {
                    update { copy(loading = false, articles = it.articles) }
                }
                .onFailure {
                    update { copy(loading = false, error = true) }
                }
        }
    }
}