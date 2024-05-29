package com.posite.modern.counter

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class CounterViewModelImpl : ViewModel(), CounterViewModel {
    private val _count = mutableStateOf(0)
    override val count: State<Int>
        get() = _count

    override fun increment() {
        _count.value++
    }

    override fun decrement() {
        _count.value--
    }
}