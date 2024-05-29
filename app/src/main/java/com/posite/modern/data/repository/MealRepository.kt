package com.posite.modern.data.repository

import com.posite.modern.data.service.MealService

class MealRepository(private val mealApi: MealService) {
    suspend fun getCategories() = mealApi.getCategories()
}