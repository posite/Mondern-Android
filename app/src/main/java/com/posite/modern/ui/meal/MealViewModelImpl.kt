package com.posite.modern.ui.meal

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.posite.modern.data.model.Category
import com.posite.modern.data.repository.MealRepository
import com.posite.modern.module.RetrofitInstance
import kotlinx.coroutines.launch

class MealViewModelImpl : ViewModel(), MealViewModel {
    private val repository: MealRepository = MealRepository(RetrofitInstance.mealService)
    private val _categories = mutableStateOf(listOf<Category>())
    override val categories: State<List<Category>>
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