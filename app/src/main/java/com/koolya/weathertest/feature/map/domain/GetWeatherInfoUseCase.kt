package com.koolya.weathertest.feature.map.domain

import com.koolya.weathertest.feature.map.data.datasource.WeatherRemoteDataSource
import com.koolya.weathertest.core.model.Weather

class GetWeatherInfoUseCase(private val weatherRemoteDataSource: WeatherRemoteDataSource) {

    suspend operator fun invoke(lat: Double, lng: Double): Result<Weather> = runCatching {
        weatherRemoteDataSource.getWeatherInfo(lat, lng)
    }

}