package com.koolya.weathertest.feature.map.ui.mapper

import com.koolya.weathertest.core.model.Weather
import com.koolya.weathertest.feature.map.ui.contract.MapContract

fun Weather.toUi() = MapContract.State.WeatherBottomSheet.WeatherInfo(
    location = this.location,
    temp = this.temp,
    weather = this.weather
)

fun MapContract.State.WeatherBottomSheet.WeatherInfo.toModel() =
    MapContract.State.WeatherBottomSheet.WeatherInfo(
        location = this.location,
        temp = this.temp,
        weather = this.weather
    )