package com.posite.modern.ui.shopping

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.posite.modern.data.model.location.Location

@Composable
fun FindAddressScreen(location: Location, onLocationSelected: (Location) -> Unit) {
    val selectedLocation = remember {
        mutableStateOf(LatLng(location.latitude, location.longitude))
    }

    val cameraPosition = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(selectedLocation.value, 10f)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFeef0ff))
    ) {
        GoogleMap(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            cameraPositionState = cameraPosition,
            onMapClick = {
                selectedLocation.value = it
                Log.d("Location", "Selected Location: $selectedLocation")
            }
        ) {
            Marker(state = MarkerState(position = selectedLocation.value))
        }
        var newLocation: Location
        Button(onClick = {
            newLocation =
                Location(selectedLocation.value.latitude, selectedLocation.value.longitude)
            onLocationSelected(newLocation)
        }, colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00b0f0))) {
            Text("완료")
        }
    }
}

@Preview
@Composable
fun FindAddressScreenPreview() {
    FindAddressScreen(
        location = Location(37.5665, 126.9780),
        onLocationSelected = {}
    )
}