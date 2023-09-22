package com.koolya.weathertest.feature.weather.ui.contract

import com.koolya.weathertest.core.model.Weather
import com.koolya.weathertest.core.ui.contract.UiEvent
import com.koolya.weathertest.core.ui.contract.UiSideEffect
import com.koolya.weathertest.core.ui.contract.UiState

object WeatherList {

    data class State(
        val weatherList: List<Weather> = emptyList(),
        val isEmpty: Boolean = true,
    ) : UiState

    sealed class Event : UiEvent {
        data object OnAddNewLocationClick : Event()

        data class OnRemoveWeatherClicked(val weather: Weather) : Event()

    }

    sealed class SideEffect : UiSideEffect {
        data object NavigateToMap : SideEffect()

    }

}