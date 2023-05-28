package com.github.imort.news.ui.news

import androidx.compose.runtime.Immutable
import com.github.imort.news.data.Article
import com.github.imort.news.ui.MviEffect
import com.github.imort.news.ui.MviEvent
import com.github.imort.news.ui.MviState

interface NewsContract {
    sealed class Event : MviEvent {
        object Retry : Event()
        data class Select(val article: Article) : Event()
    }

    @Immutable
    data class State(
        val country: String,
        val loading: Boolean,
        val articles: List<Article>,
        val error: Boolean,
    ) : MviState

    sealed class Effect : MviEffect {
        data class Select(val article: Article) : Effect()
    }
}
