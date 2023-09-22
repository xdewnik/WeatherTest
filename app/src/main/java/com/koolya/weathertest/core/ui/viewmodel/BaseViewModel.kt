package com.koolya.weathertest.core.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.koolya.weathertest.core.ui.contract.UiEvent
import com.koolya.weathertest.core.ui.contract.UiSideEffect
import com.koolya.weathertest.core.ui.contract.UiState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch


abstract class BaseViewModel<E : UiEvent, S : UiState, SE : UiSideEffect> : ViewModel() {

    val currentState: S
        get() = state.value
    val state: StateFlow<S>
    val event: SharedFlow<E>
    val sideEffect: Flow<SE>

    protected val defaultErrorHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("exception handler", "${throwable.message}: $throwable")
    }

    private val initialState: S by lazy { createInitialState() }
    private val stateFlow: MutableStateFlow<S> = MutableStateFlow(initialState)
    private val evenSharedFlow: MutableSharedFlow<E> = MutableSharedFlow()
    private val sideEffectChannel: Channel<SE> = Channel<SE>()

    init {
        state = stateFlow.asStateFlow()
        event = evenSharedFlow.asSharedFlow()
        sideEffect = sideEffectChannel.receiveAsFlow()
        subscribeEvents()
    }

    abstract fun createInitialState(): S
    abstract fun handleEvent(event: E)

    protected fun launch(
        scope: CoroutineScope = viewModelScope,
        block: suspend CoroutineScope.() -> Unit,
    ): Job {
        return scope.launch(defaultErrorHandler) {
            this.block()
        }
    }

    fun setEvent(event: E) {
        val newEvent = event
        viewModelScope.launch(defaultErrorHandler) { evenSharedFlow.emit(newEvent) }
    }

    protected fun setState(reduce: S.() -> S) {
        val newState = currentState.reduce()
        stateFlow.value = newState
    }

    protected fun setSideEffect(builder: () -> SE) {
        val effectValue = builder()
        viewModelScope.launch(defaultErrorHandler) { sideEffectChannel.send(effectValue) }
    }

    private fun subscribeEvents() {
        viewModelScope.launch(defaultErrorHandler) {
            event.collect {
                handleEvent(it)
            }
        }
    }
}