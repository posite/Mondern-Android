package com.posite.modern.ui.shopping

import androidx.compose.runtime.State
import com.posite.modern.data.remote.model.location.Location
import com.posite.modern.data.remote.model.shopping.GeocodingResult

interface ShoppingViewModel {
    val location: State<Location?>
    val address: State<List<GeocodingResult>>

    fun updateLocation(location: Location)
    fun fetchAddress(latlng: String)
}