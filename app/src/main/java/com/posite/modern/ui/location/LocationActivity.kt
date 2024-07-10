package com.posite.modern.ui.location

import android.Manifest
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import com.posite.modern.ui.theme.ModernTheme
import com.posite.modern.util.LocationUtil
import com.posite.modern.util.PermissionUtil

class LocationActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: LocationContractViewModel by viewModels<LocationContractViewModel>()
            ModernTheme {
                LocationScreen(LocalContext.current, viewModel, LocationUtil(LocalContext.current))
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun LocationScreen(context: Context, viewModel: LocationContractViewModel, util: LocationUtil) {
    val uiState = viewModel.uiState.collectAsState()
    var location = uiState.value.locationState
    var address = ""

    LaunchedEffect(key1 = viewModel) {
        viewModel.effect.collect {
            when (it) {
                is LocationContract.Effect.ShowError -> {
                    Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
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
            if (location is LocationContract.LocationState.Success) {
                util.reverseGeocodeLocation(context, location.location) { reverseResult ->
                    address = reverseResult
                }
                Text("Latitude: ${location.location.latitude}")
                Text("Longitude: ${location.location.longitude}")
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