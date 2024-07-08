package com.posite.modern.ui.shopping

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.posite.modern.ModernApplication.Companion.getString
import com.posite.modern.R
import com.posite.modern.data.remote.model.location.Location
import com.posite.modern.data.remote.model.shopping.GeocodingResult
import com.posite.modern.data.repository.shopping.ShoppingRepository
import com.posite.modern.module.RetrofitInstance
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModelImpl @Inject constructor(private val repository: ShoppingRepository) : ViewModel(), ShoppingViewModel {
    private val _location = mutableStateOf<Location?>(null)
    override val location: State<Location?>
        get() = _location

    private val _address = mutableStateOf(listOf<GeocodingResult>())
    override val address: State<List<GeocodingResult>>
        get() = _address

    private val _locationButtonSelect = mutableStateOf(false)
    override val locationButtonSelect: State<Boolean>
        get() = _locationButtonSelect

    override fun updateLocation(location: Location) {
        _location.value = location
    }

    override fun fetchAddress(latlng: String) {
        try {
            viewModelScope.launch {
                Log.d("address", latlng)
                val result = repository.getAddress(latlng, getString(R.string.map_key))
                _address.value = result.results
                Log.d("address status", result.status)
                Log.d(
                    "address success",
                    address.value.firstOrNull()?.formatted_address ?: "No address found"
                )
            }
        } catch (e: Exception) {
            Log.d("address", "${e.cause} ${e.message}")
        }
    }

    override fun locationButtonSelect() {
        _locationButtonSelect.value = _locationButtonSelect.value.not()
    }
}