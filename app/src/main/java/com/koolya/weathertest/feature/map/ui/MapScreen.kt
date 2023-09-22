package com.koolya.weathertest.feature.map.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.koolya.weathertest.R
import com.koolya.weathertest.feature.map.ui.contract.MapContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(viewModel: MapViewModel = getViewModel()) {
    val state by viewModel.state.collectAsState()

    MapScreen(
        state = state,
        onEvent = viewModel::handleEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(
    state: MapContract.State,
    onEvent: (MapContract.Event) -> Unit,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    bottomSheetState: SheetState = rememberModalBottomSheetState(),
) {
    Box {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = CameraPositionState(
                CameraPosition.fromLatLngZoom(
                    state.marker,
                    0f
                )
            ),
            onMapClick = { latLng -> onEvent(MapContract.Event.OnMapClick(latLng)) }
        ) {
            Marker(
                state = MarkerState(state.marker),
            )
        }
        if (state.weatherBottomSheet.isVisible) {
            ModalBottomSheet(onDismissRequest = { onEvent(MapContract.Event.HideBottomSheet) }) {
                WeatherBottomSheet(
                    state = state.weatherBottomSheet,
                    onSaveClick = {
                        coroutineScope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                            if (!bottomSheetState.isVisible) {
                                onEvent(MapContract.Event.OnSaveWeatherClick)
                            }
                        }
                    },
                )
            }
        }
    }
}

@Composable
fun WeatherBottomSheet(
    state: MapContract.State.WeatherBottomSheet,
    onSaveClick: () -> Unit,
) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        when {
            state.isLoading -> CircularProgressIndicator(
                modifier = Modifier
                    .size(36.dp)
                    .align(Alignment.Center)
            )

            !state.error.isNullOrEmpty() -> Text(text = state.error, color = Color.Red)
            state.weatherInfo != null -> WeatherInfo(weatherInfo = state.weatherInfo, onSaveClick)

            else -> Text(
                text = stringResource(id = R.string.something_went_wrong),
                color = Color.Red
            )
        }
    }
}

@Composable
fun WeatherInfo(
    weatherInfo: MapContract.State.WeatherBottomSheet.WeatherInfo,
    onSaveClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
        ) {
            Text(text = weatherInfo.location)

            Spacer(modifier = Modifier.width(16.dp))

            Text(text = weatherInfo.weather)

            Spacer(modifier = Modifier.width(16.dp))

            Text(text = weatherInfo.temp)
        }
        Button(onClick = onSaveClick) {
            Text(text = stringResource(id = R.string.button_save_location))
        }
    }
}