package com.koolya.weathertest.ui.feature.map.ui.contract

import com.google.android.gms.maps.model.LatLng
import com.koolya.weathertest.core.ui.contract.UiEvent
import com.koolya.weathertest.core.ui.contract.UiSideEffect
import com.koolya.weathertest.core.ui.contract.UiState

object MapContract {
    data class State(
        val marker: LatLng = LatLng(0.0, 0.0),
        val weatherBottomSheet: WeatherBottomSheet = WeatherBottomSheet()
    ) : UiState {
        data class WeatherBottomSheet(
            val isVisible: Boolean = false,
            val isLoading: Boolean = false,
            val error: String? = null,
            val weatherInfo: WeatherInfo? = null,
        ) {
            data class WeatherInfo(
                val location: String,
                val temp: String,
                val weather: String
            )
        }
    }

    sealed class Event : UiEvent {
        data class OnMapClick(val latLng: LatLng) : Event()
        data object OnSaveWeatherClick : Event()

        data object HideBottomSheet : Event()

    }

    sealed class SideEffect : UiSideEffect

}