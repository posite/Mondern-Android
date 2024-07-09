package com.posite.modern.util

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.posite.modern.data.remote.model.location.Location
import com.posite.modern.ui.location.LocationViewModel
import com.posite.modern.ui.shopping.ShoppingContractViewModel
import com.posite.modern.ui.shopping.ShoppingViewModel
import java.util.Locale

class LocationUtil(context: Context) {
    private val _fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    @Suppress("MissingPermission")
    fun requestLocationUpdate(viewModel: LocationViewModel) {
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: com.google.android.gms.location.LocationResult) {
                super.onLocationResult(locationResult)
                locationResult.lastLocation?.let {
                    viewModel.updateLocation(Location(it.latitude, it.longitude))
                }
            }
        }

        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).build()

        _fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    @Suppress("MissingPermission")
    fun requestLocationUpdate(viewModel: ShoppingContractViewModel) {
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: com.google.android.gms.location.LocationResult) {
                super.onLocationResult(locationResult)
                locationResult.lastLocation?.let {
                    viewModel.updateLocation(Location(it.latitude, it.longitude))
                }
            }
        }

        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 1000).build()

        _fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    fun reverseGeocodeLocation(context: Context, location: Location): String {
        val geocoder = Geocoder(context, Locale.KOREAN)
        val coordinates = LatLng(location.latitude, location.longitude)
        val addresses: MutableList<Address>? =
            geocoder.getFromLocation(coordinates.latitude, coordinates.longitude, 1)

        return if (addresses?.isNotEmpty() == true) return addresses[0].getAddressLine(0) else "No Address Found"
    }
}