package com.posite.modern.ui.meal

import androidx.compose.runtime.State
import com.posite.modern.data.model.Category

interface MealViewModel {
    val categories: State<List<Category>>
    val isLoding: State<Boolean>
    suspend fun getCategories()
}