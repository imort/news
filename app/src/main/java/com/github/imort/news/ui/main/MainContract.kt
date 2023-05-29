package com.github.imort.news.ui.main

import androidx.compose.runtime.Immutable
import com.github.imort.news.ui.MviEffect
import com.github.imort.news.ui.MviEvent
import com.github.imort.news.ui.MviState

interface MainContract {
    sealed class Event : MviEvent {
        object BiometricSuccess : Event()
    }

    @Immutable
    data class State(
        val biometric: Boolean = false,
    ) : MviState

    sealed class Effect : MviEffect
}