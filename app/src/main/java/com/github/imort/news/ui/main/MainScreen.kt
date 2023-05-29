package com.github.imort.news.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.imort.news.data.BiometricHelper
import com.github.imort.news.ui.MviEffectCollector
import com.github.imort.news.ui.main.MainContract.Effect
import com.github.imort.news.ui.main.MainContract.Event
import com.github.imort.news.ui.main.MainContract.State
import kotlinx.coroutines.flow.Flow

@Composable
fun MainScreen(
    biometricHelper: BiometricHelper,
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val activity = LocalContext.current as FragmentActivity
    Main(
        state = viewModel.viewState.value,
        effects = viewModel.effects,
        onEvent = viewModel::emit,
        onEffect = {},
        onShowBiometricPrompt = {
            biometricHelper.showPrompt(activity) {
                viewModel.emit(Event.BiometricSuccess)
            }
        },
        modifier = modifier,
    )
}

@Suppress("UNUSED_PARAMETER")
@Composable
private fun Main(
    state: State,
    effects: Flow<Effect>,
    onEvent: (Event) -> Unit,
    onEffect: (Effect) -> Unit,
    onShowBiometricPrompt: () -> Unit,
    modifier: Modifier = Modifier,
) {
    MviEffectCollector(flow = effects, handler = onEffect)

    val biometric = state.biometric
    LaunchedEffect(biometric) {
        if (biometric) onShowBiometricPrompt()
    }
    if (biometric) {
        Lock(modifier = modifier) {
            onShowBiometricPrompt()
        }
    } else {
        MainNavHost(modifier = modifier)
    }
}

@Composable
private fun Lock(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Button(onClick = onClick) {
            Text(text = "Biometric auth")
        }
    }
}
