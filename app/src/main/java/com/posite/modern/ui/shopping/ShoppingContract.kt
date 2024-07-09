package com.posite.modern.ui.shopping

import com.posite.modern.data.remote.model.location.Location
import com.posite.modern.data.remote.model.shopping.GeocodingResult
import com.posite.modern.ui.base.UiEffect
import com.posite.modern.ui.base.UiEvent
import com.posite.modern.ui.base.UiState

class ShoppingContract {
    sealed class Event : UiEvent {
        data class FetchAddress(val latlng: String) : Event()
    }

    sealed class ShoppingListState {
        data class ShoppingLocation(val location: Location) : ShoppingListState()
        data class ChooseLocation(val location: List<GeocodingResult>) : ShoppingListState()
    }

    sealed class Effect : UiEffect {
        object FetchAddressError : Effect()
    }

    data class State(
        val shoppingLocation: ShoppingListState.ShoppingLocation? = null,
        val locationText: ShoppingListState.ChooseLocation
    ) : UiState
}