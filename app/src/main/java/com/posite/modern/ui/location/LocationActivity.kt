package com.posite.modern.ui.location

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.posite.modern.ui.theme.ModernTheme
import com.posite.modern.util.LocationUtil
import com.posite.modern.util.PermissionUtil

class LocationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: LocationViewModel by viewModels<LocationViewModelImpl>()
            ModernTheme {
                LocationScreen(LocalContext.current, viewModel, LocationUtil(LocalContext.current))
            }
        }
    }
}

@Composable
fun LocationScreen(context: Context, viewModel: LocationViewModel, util: LocationUtil) {
    val location = viewModel.location.value
    val address = location?.let { util.reverseGeocodeLocation(context, it) }
    val requestPermission =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions(),
            onResult = { permissions ->
                if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true && permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
                    util.requestLocationUpdate(viewModel)
                } else {
                    requireLocationPermission(context)
                }
            })
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (PermissionUtil.hasLocationPermission(context)) {
            Text("Location Permission Granted")
            util.requestLocationUpdate(viewModel)
            if (location != null) {
                Text("Latitude: ${location.latitude}")
                Text("Longitude: ${location.longitude}")
                Text("Address: $address")
            }
        } else {
            Text(text = "Requesting Location Permission")
            SideEffect {
                requestPermission.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                )
            }
            Log.d("request", requestPermission.contract.toString())
        }

    }

}

fun requireLocationPermission(context: Context) {
    val requestPermission =
        ActivityCompat.shouldShowRequestPermissionRationale(
            context as LocationActivity,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) || ActivityCompat.shouldShowRequestPermissionRationale(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    if (requestPermission) {
        Toast.makeText(context, "Location Permission Required", Toast.LENGTH_SHORT).show()
    } else {

    }
}

@Composable
@Preview
fun LocationScreenPreview() {
    ModernTheme {

    }
}