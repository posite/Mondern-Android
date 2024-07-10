package com.posite.modern.ui.location

import com.posite.modern.data.remote.model.location.Location
import com.posite.modern.ui.base.UiEffect
import com.posite.modern.ui.base.UiEvent
import com.posite.modern.ui.base.UiState

class LocationContract {
    sealed class Event : UiEvent {
        data class UpdateLocation(val location: Location) : Event()
    }

    sealed class LocationState {
        object Before : LocationState()
        data class Success(val location: Location) : LocationState()
        data class Error(val message: String) : LocationState()
    }

    sealed class Effect : UiEffect {
        object ShowError : Effect()
    }

    data class State(val locationState: LocationState) : UiState
}