package com.posite.modern.ui.counter

import androidx.lifecycle.viewModelScope
import com.posite.modern.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CounterContractViewModel @Inject constructor() :
    BaseViewModel<CounterContract.Event, CounterContract.State, CounterContract.Effect>() {
    override fun createInitialState(): CounterContract.State {
        return CounterContract.State(CounterContract.CounterNumberState.Change(0))
    }

    override fun handleEvent(event: CounterContract.Event) {
        viewModelScope.launch {
            when (event) {
                is CounterContract.Event.IncrementButtonClicked -> {
                    setState {
                        copy(count = CounterContract.CounterNumberState.Change(this.count.number + 1))
                    }
                }

                else -> {
                    setState {
                        copy(count = CounterContract.CounterNumberState.Change(this.count.number - 1))
                    }
                }
            }
        }
    }

    fun increment() {
        setEvent(CounterContract.Event.IncrementButtonClicked)
    }

    fun decrement() {
        setEvent(CounterContract.Event.DecrementButtonClicked)
    }
}