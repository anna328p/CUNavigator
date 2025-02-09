package dev.ap5.cunavigator.ui.composables

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.AdvancedMarker
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import dev.ap5.mtdapi.rest.models.Stop

@Composable
fun StopMap(stops : List<Stop>) {
    val cameraPositionState = rememberCameraPositionState {
        position =
            CameraPosition.fromLatLngZoom(LatLng(40.1121034, -88.2172446), 17f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,

        uiSettings = MapUiSettings(
            tiltGesturesEnabled = false,
        )
    ) {
        for (stop in stops) {
            for (point in stop.stopPoints) {
                AdvancedMarker(
                    state = MarkerState(position = LatLng(point.lat, point.lon)),
                    title = point.name,
                    snippet = point.id.toString(),
                )
            }
        }
    }
}