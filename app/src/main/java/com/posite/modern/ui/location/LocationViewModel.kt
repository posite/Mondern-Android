package com.posite.modern.ui.location

import androidx.compose.runtime.State
import com.posite.modern.data.remote.model.location.Location

interface LocationViewModel {
    val location: State<Location?>

    fun updateLocation(location: Location?)
}