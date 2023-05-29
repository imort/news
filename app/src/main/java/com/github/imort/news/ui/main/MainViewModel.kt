package com.github.imort.news.ui.main

import androidx.fragment.app.FragmentActivity
import com.github.imort.news.data.BiometricHelper
import com.github.imort.news.ui.MviViewModel
import com.github.imort.news.ui.main.MainContract.Effect
import com.github.imort.news.ui.main.MainContract.Event
import com.github.imort.news.ui.main.MainContract.State
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val biometricHelper: BiometricHelper,
) : MviViewModel<Event, State, Effect>(State(biometric = biometricHelper.available())) {

    override fun handle(event: Event) = when (event) {
        is Event.ShowPrompt -> showPrompt(event.activity)
    }

    private fun showPrompt(activity: FragmentActivity) {
        biometricHelper.showPrompt(activity) {
            update { copy(biometric = false) }
        }
    }
}