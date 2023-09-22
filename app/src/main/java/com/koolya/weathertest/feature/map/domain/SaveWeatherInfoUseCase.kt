package com.koolya.weathertest.feature.map.domain

import com.koolya.weathertest.core.data.datasource.WeatherLocalDataSource
import com.koolya.weathertest.core.model.Weather

class SaveWeatherInfoUseCase(private val weatherLocalDataSource: WeatherLocalDataSource) {

    suspend operator fun invoke(weather: Weather): Result<Unit> = runCatching {
        weatherLocalDataSource.insert(weather)
    }

}