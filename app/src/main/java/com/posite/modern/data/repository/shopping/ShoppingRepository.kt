package com.posite.modern.data.repository.shopping

import com.posite.modern.data.service.shopping.MapService

class ShoppingRepository(private val mapService: MapService) {
    suspend fun getAddress(latlng: String, apiKey: String) =
        mapService.getAddressFromCoordinates(latlng, apiKey)
}