package com.posite.modern.data.service.meal

import com.posite.modern.data.model.meal.CategoryResponse
import retrofit2.http.GET

interface MealService {

    @GET("categories.php")
    suspend fun getCategories(): CategoryResponse
}