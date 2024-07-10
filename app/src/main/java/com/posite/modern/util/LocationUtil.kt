package com.posite.modern.util

import android.content.Context
import android.location.Geocoder
import android.os.Build
import android.os.Looper
import androidx.annotation.RequiresApi
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.posite.modern.data.remote.model.location.Location
import com.posite.modern.ui.location.LocationContractViewModel
import com.posite.modern.ui.shopping.ShoppingContractViewModel
import java.util.Locale

class LocationUtil(context: Context) {
    private val _fusedLocationClient: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    @Suppress("MissingPermission")
    fun requestLocationUpdate(viewModel: LocationContractViewModel) {
        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: com.google.android.gms.location.LocationResult) {
                super.onLocationResult(locationResult)
                locationResult.lastLocation?.let {
                    viewModel.updateLocation(Location(it.latitude, it.longitude))
                } ?: viewModel.updateLocation(null)
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

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun reverseGeocodeLocation(context: Context, location: Location, listener: (String) -> Unit) {
        val geocoder = Geocoder(context, Locale.KOREAN)
        val coordinates = LatLng(location.latitude, location.longitude)
        geocoder.getFromLocation(coordinates.latitude, coordinates.longitude, 1) {
            listener(it[0].getAddressLine(0))
        }
    }

}