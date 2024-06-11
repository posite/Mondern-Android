package com.posite.modern.module

import com.posite.modern.ModernApplication.Companion.getString
import com.posite.modern.R
import com.posite.modern.data.remote.service.meal.MealService
import com.posite.modern.data.remote.service.shopping.MapService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


object RetrofitInstance {
    private val okHttpClient: OkHttpClient = OkHttpClient.Builder().build()
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    private val retrofit = Retrofit.Builder()
        .baseUrl(getString(R.string.meal_url))
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    private val mapRetrofit = Retrofit.Builder()
        .baseUrl(getString(R.string.map_url))
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()

    val mealService: MealService by lazy {
        retrofit.create(MealService::class.java)
    }

    val mapService: MapService by lazy {
        mapRetrofit.create(MapService::class.java)
    }
}