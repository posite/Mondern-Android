package com.posite.modern.ui.shopping

import androidx.lifecycle.viewModelScope
import com.posite.modern.ModernApplication.Companion.getString
import com.posite.modern.R
import com.posite.modern.data.remote.model.location.Location
import com.posite.modern.data.repository.shopping.ShoppingRepository
import com.posite.modern.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingContractViewModel @Inject constructor(private val repository: ShoppingRepository) :
    BaseViewModel<ShoppingContract.Event, ShoppingContract.State, ShoppingContract.Effect>() {
    override fun createInitialState(): ShoppingContract.State {
        return ShoppingContract.State(
            shoppingLocation = null,
            locationText = ShoppingContract.ShoppingListState.ChooseLocation(emptyList())
        )
    }

    override fun handleEvent(event: ShoppingContract.Event) {
        when (event) {
            is ShoppingContract.Event.FetchAddress -> {
                viewModelScope.launch {
                    try {
                        val result = repository.getAddress(event.latlng, getString(R.string.map_key))
                        setState {
                            copy(locationText = ShoppingContract.ShoppingListState.ChooseLocation(result.results))
                        }
                    } catch (e: Exception) {
                        setEffect {
                            ShoppingContract.Effect.FetchAddressError
                        }
                    }
                }
            }

        }
    }

    fun fetchAddress(latlng: String) {
        setEvent(ShoppingContract.Event.FetchAddress(latlng))
    }

    fun updateLocation(location: Location) {
        setState {
            copy(shoppingLocation = ShoppingContract.ShoppingListState.ShoppingLocation(location))
        }
    }
}