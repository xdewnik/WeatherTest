package com.koolya.weathertest.feature.weather.domain

import com.koolya.weathertest.core.data.datasource.WeatherLocalDataSource
import com.koolya.weathertest.core.model.Weather

class RemoveWeatherUseCase(private val weatherLocalDataSource: WeatherLocalDataSource) {

    suspend operator fun invoke(weather: Weather): Result<Unit> = runCatching {
        weatherLocalDataSource.remove(weather)
    }

}