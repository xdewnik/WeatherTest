package com.koolya.weathertest.feature.map.data.datasource

import com.koolya.weathertest.feature.map.data.api.WeatherApi
import com.koolya.weathertest.core.model.Weather
import com.koolya.weathertest.feature.map.data.datasource.mapper.toModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

interface WeatherRemoteDataSource {

    suspend fun getWeatherInfo(lat: Double, lng: Double): Weather

}

class WeatherRemoteDataSourceImpl(
    private val weatherApi: WeatherApi,
    private val ioDispatcher: CoroutineDispatcher
) : WeatherRemoteDataSource {
    override suspend fun getWeatherInfo(lat: Double, lng: Double): Weather =
        withContext(ioDispatcher) {
            weatherApi.getWeatherData(lat, lng).toModel()
        }

}