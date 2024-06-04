package com.posite.modern.ui.shopping

import androidx.compose.runtime.State
import com.posite.modern.data.model.location.Location
import com.posite.modern.data.model.shopping.GeocodingResult
import kotlinx.coroutines.flow.SharedFlow

interface ShoppingViewModel {
    val location: State<Location?>
    val address: State<List<GeocodingResult>>
    val locationButtonSelect: State<Boolean>

    fun updateLocation(location: Location)
    fun fetchAddress(latlng: String)
    fun locationButtonSelect()
}