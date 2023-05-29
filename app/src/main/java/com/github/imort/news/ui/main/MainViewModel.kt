package com.github.imort.news.ui.main

import com.github.imort.news.data.BiometricHelper
import com.github.imort.news.ui.MviViewModel
import com.github.imort.news.ui.main.MainContract.Effect
import com.github.imort.news.ui.main.MainContract.Event
import com.github.imort.news.ui.main.MainContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    biometricHelper: BiometricHelper,
) : MviViewModel<Event, State, Effect>(State(biometric = biometricHelper.available())) {

    override fun handle(event: Event) = when (event) {
        is Event.BiometricSuccess -> update { copy(biometric = false) }
    }
}