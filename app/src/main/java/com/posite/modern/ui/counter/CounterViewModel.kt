package com.posite.modern.ui.counter

import androidx.compose.runtime.State

interface CounterViewModel {
    val count: State<Int>
    fun increment()
    fun decrement()
}