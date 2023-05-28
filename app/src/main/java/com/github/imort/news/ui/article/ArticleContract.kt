package com.github.imort.news.ui.article

import androidx.compose.runtime.Immutable
import com.github.imort.news.data.Article
import com.github.imort.news.ui.MviEffect
import com.github.imort.news.ui.MviEvent
import com.github.imort.news.ui.MviState

interface ArticleContract {
    sealed class Event : MviEvent {
        object Return : Event()
    }

    @Immutable
    data class State(
        val article: Article? = null,
    ) : MviState

    sealed class Effect : MviEffect {
        object Return : Effect()
    }
}
