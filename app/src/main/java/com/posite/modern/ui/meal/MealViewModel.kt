package com.posite.modern.ui.meal

import androidx.compose.runtime.State
import com.posite.modern.data.model.Category
import kotlinx.coroutines.flow.StateFlow

interface MealViewModel {
    val categories: StateFlow<List<Category>>
    val isLoding: State<Boolean>
    suspend fun getCategories()
}