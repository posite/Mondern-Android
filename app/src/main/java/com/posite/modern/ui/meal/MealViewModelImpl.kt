package com.posite.modern.ui.meal

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.posite.modern.data.remote.model.meal.Category
import com.posite.modern.data.repository.meal.MealRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealViewModelImpl @Inject constructor(private val repository: MealRepository) : ViewModel(),
    MealViewModel {

    private val _categories = MutableStateFlow(emptyList<Category>())
    override val categories: StateFlow<List<Category>>
        get() = _categories

    private val _isLoding = mutableStateOf(true)
    override val isLoding: State<Boolean>
        get() = _isLoding

    override suspend fun getCategories() {
        viewModelScope.launch {
            try {
                repository.getCategories().collect {
                    _categories.value = it.categories
                }
            } catch (e: Exception) {
                _isLoding.value = false
                e.printStackTrace()
            }
        }
    }
}