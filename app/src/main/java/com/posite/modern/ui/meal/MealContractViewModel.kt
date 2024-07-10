package com.posite.modern.ui.meal

import androidx.lifecycle.viewModelScope
import com.posite.modern.data.repository.meal.MealRepository
import com.posite.modern.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealContractViewModelImpl @Inject constructor(private val repository: MealRepository) :
    BaseViewModel<MealContract.Event, MealContract.State, MealContract.Effect>() {
    override fun createInitialState(): MealContract.State {
        return MealContract.State(
            MealContract.MealListState.Before,
            MealContract.MealListState.Meals(emptyList())
        )
    }

    override fun handleEvent(event: MealContract.Event) {
        when (event) {
            is MealContract.Event.GetCategories -> {
                viewModelScope.launch {
                    try {
                        setState { copy(loadState = MealContract.MealListState.Loading) }
                        repository.getCategories().collect {
                            setState {
                                copy(
                                    loadState = MealContract.MealListState.Success,
                                    categories = MealContract.MealListState.Meals(it.categories)
                                )
                            }
                        }
                    } catch (e: Exception) {
                        e.message?.let {
                            setState { copy(loadState = MealContract.MealListState.Error(it)) }
                        }

                    }

                }
            }
        }
    }

    fun getCategories() {
        setEvent(MealContract.Event.GetCategories)
    }

}