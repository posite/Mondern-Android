package com.posite.modern.data.service.shopping

import com.posite.modern.data.model.shopping.GeocodingResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface MapService {
    @GET("geocode/json")
    suspend fun getAddressFromCoordinates(
        @Query("latlng") latlng: String,
        @Query("key") apiKey: String
    ): GeocodingResponse
}