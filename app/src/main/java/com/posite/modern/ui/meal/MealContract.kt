package com.posite.modern.ui.meal

import com.posite.modern.data.remote.model.meal.Category
import com.posite.modern.ui.base.UiEffect
import com.posite.modern.ui.base.UiEvent
import com.posite.modern.ui.base.UiState

class MealContract {
    sealed class Event : UiEvent {
        object GetCategories : Event()
    }

    sealed class MealListState {
        object Before : MealListState()
        object Loading : MealListState()
        object Success : MealListState()
        data class Error(val message: String) : MealListState()
        data class Meals(val categories: List<Category>) : MealListState()
    }

    sealed class Effect : UiEffect {
        object ShowError : Effect()
    }

    data class State(val loadState: MealListState, val categories: MealListState.Meals) : UiState

}