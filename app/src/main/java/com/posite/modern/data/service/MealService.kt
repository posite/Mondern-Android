package com.posite.modern.data.service

import com.posite.modern.data.model.CategoryResponse
import retrofit2.http.GET

interface MealService {

    @GET("categories.php")
    suspend fun getCategories(): CategoryResponse
}