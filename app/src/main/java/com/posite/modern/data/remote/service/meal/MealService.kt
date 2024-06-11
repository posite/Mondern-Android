package com.posite.modern.data.remote.service.meal

import com.posite.modern.data.remote.model.meal.CategoryResponse
import retrofit2.http.GET

interface MealService {

    @GET("categories.php")
    suspend fun getCategories(): CategoryResponse
}