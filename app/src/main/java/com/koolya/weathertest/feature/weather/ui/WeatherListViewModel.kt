package com.koolya.weathertest.feature.weather.ui

import androidx.lifecycle.viewModelScope
import com.koolya.weathertest.core.model.Weather
import com.koolya.weathertest.core.ui.viewmodel.BaseViewModel
import com.koolya.weathertest.feature.weather.domain.GetWeatherListUseCase
import com.koolya.weathertest.feature.weather.domain.RemoveWeatherUseCase
import com.koolya.weathertest.feature.weather.ui.contract.WeatherList
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class WeatherListViewModel(
    getWeatherListUseCase: GetWeatherListUseCase,
    private val removeWeatherUseCase: RemoveWeatherUseCase,
) :
    BaseViewModel<WeatherList.Event, WeatherList.State, WeatherList.SideEffect>() {

    override fun createInitialState(): WeatherList.State = WeatherList.State()

    init {
        getWeatherListUseCase.invoke()
            .onEach { setState { copy(weatherList = it, isEmpty = it.isEmpty()) } }
            .launchIn(viewModelScope)
    }

    override fun handleEvent(event: WeatherList.Event) = when (event) {
        WeatherList.Event.OnAddNewLocationClick -> setSideEffect { WeatherList.SideEffect.NavigateToMap }
        is WeatherList.Event.OnRemoveWeatherClicked -> removeWeather(weather = event.weather)
    }

    private fun removeWeather(weather: Weather) {
        launch {
            removeWeatherUseCase.invoke(weather = weather)
        }
    }

}