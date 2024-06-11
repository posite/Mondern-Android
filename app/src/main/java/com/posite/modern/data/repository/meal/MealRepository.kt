package com.posite.modern.data.repository.meal

import com.posite.modern.data.remote.service.meal.MealService

class MealRepository(private val mealApi: MealService) {
    suspend fun getCategories() = mealApi.getCategories()
}