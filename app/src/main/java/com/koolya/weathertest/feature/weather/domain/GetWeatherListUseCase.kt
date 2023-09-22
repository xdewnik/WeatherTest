package com.koolya.weathertest.feature.weather.domain

import com.koolya.weathertest.core.data.datasource.WeatherLocalDataSource
import com.koolya.weathertest.core.model.Weather
import kotlinx.coroutines.flow.Flow

class GetWeatherListUseCase(private val dataSource: WeatherLocalDataSource) {

    operator fun invoke(): Flow<List<Weather>> = dataSource.getWeatherList()

}