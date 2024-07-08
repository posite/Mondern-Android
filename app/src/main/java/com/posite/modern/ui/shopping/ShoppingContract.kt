package com.posite.modern.ui.shopping

import com.posite.modern.ui.base.UiEffect
import com.posite.modern.ui.base.UiEvent
import com.posite.modern.ui.base.UiState

class ShoppingContract {
    sealed class Event : UiEvent {
        object IncrementButtonClicked : Event()
        object DecrementButtonClicked : Event()
    }

    sealed class CounterNumberState(val number: Int) {
        data class Change(val cNumber: Int) : CounterNumberState(cNumber)
    }

    sealed class Effect : UiEffect {
        object IncrementEffect : Effect()
        object DecrementEffect : Effect()
    }

    data class State(val count: CounterNumberState) : UiState
}