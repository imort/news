package com.github.imort.news.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun <Effect : MviEffect> MviEffectCollector(flow: Flow<Effect>, handler: (Effect) -> Unit) {
    LaunchedEffect("mvi-effects") {
        flow
            .onEach { effect -> handler(effect) }
            .collect()
    }
}