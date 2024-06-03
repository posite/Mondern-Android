package com.posite.modern.ui.location

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.posite.modern.data.model.location.Location

class LocationViewModelImpl : ViewModel(), LocationViewModel {
    private val _location = mutableStateOf<Location?>(null)
    override val location: State<Location?>
        get() = _location

    override fun updateLocation(location: Location) {
        _location.value = location
    }
}