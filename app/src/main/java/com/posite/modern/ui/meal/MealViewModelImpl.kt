package com.posite.modern.ui.meal

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.posite.modern.data.model.meal.Category
import com.posite.modern.data.repository.meal.MealRepository
import com.posite.modern.module.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MealViewModelImpl : ViewModel(), MealViewModel {
    private val repository: MealRepository = MealRepository(RetrofitInstance.mealService)
    private val _categories = MutableStateFlow(emptyList<Category>())
    override val categories: StateFlow<List<Category>>
        get() = _categories

    private val _isLoding = mutableStateOf(true)
    override val isLoding: State<Boolean>
        get() = _isLoding

    override suspend fun getCategories() {
        viewModelScope.launch {
            try {
                val response = repository.getCategories()
                _categories.value = response.categories
                _isLoding.value = false
            } catch (e: Exception) {
                _isLoding.value = false
                e.printStackTrace()
            }
        }
    }
}