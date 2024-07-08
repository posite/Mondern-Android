package com.posite.modern.data.repository.meal

import com.posite.modern.data.remote.service.meal.MealService
import kotlinx.coroutines.flow.flow

class MealRepository(private val mealApi: MealService) {
    suspend fun getCategories() = flow {
        emit(mealApi.getCategories())
    }
}