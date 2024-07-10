package com.posite.modern.ui.location

import com.posite.modern.data.remote.model.location.Location
import com.posite.modern.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationContractViewModel @Inject constructor() :BaseViewModel<LocationContract.Event, LocationContract.State, LocationContract.Effect>() {
    override fun createInitialState(): LocationContract.State {
        return LocationContract.State(LocationContract.LocationState.Before)
    }

    override fun handleEvent(event: LocationContract.Event) {
        when (event) {
            is LocationContract.Event.UpdateLocation -> {
                setState { copy(locationState = LocationContract.LocationState.Success(event.location)) }
            }
        }
    }

    fun updateLocation(location: Location?) {
        location?.let {
            setEvent(LocationContract.Event.UpdateLocation(it))
        } ?: setEffect {
            LocationContract.Effect.ShowError
        }

    }
}