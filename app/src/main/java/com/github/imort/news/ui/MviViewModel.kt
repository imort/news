package com.github.imort.news.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

interface MviEvent

interface MviState

interface MviEffect

abstract class MviViewModel<Ev : MviEvent, S : MviState, Ef : MviEffect>(
    initialState: S,
) : ViewModel() {

    protected open fun handle(event: Ev) = Unit

    private val _viewState: MutableState<S> = mutableStateOf(initialState)
    val viewState: State<S> = _viewState

    private val _event: MutableSharedFlow<Ev> = MutableSharedFlow()

    private val _effects: Channel<Ef> = Channel()
    val effects = _effects.receiveAsFlow()

    init {
        viewModelScope.launch { _event.collect(::handle) }
    }

    fun emit(event: Ev) {
        viewModelScope.launch { _event.emit(event) }
    }

    protected fun update(reducer: S.() -> S) {
        _viewState.value = viewState.value.reducer()
    }

    protected fun send(effect: Ef) {
        viewModelScope.launch { _effects.send(effect) }
    }
}