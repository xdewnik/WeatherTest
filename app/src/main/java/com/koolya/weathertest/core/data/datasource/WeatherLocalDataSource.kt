package com.koolya.weathertest.core.data.datasource

import com.koolya.weathertest.core.data.database.dao.WeatherDao
import com.koolya.weathertest.core.data.datasource.mapepr.toEntity
import com.koolya.weathertest.core.data.datasource.mapepr.toModel
import com.koolya.weathertest.core.model.Weather
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

interface WeatherLocalDataSource {

    suspend fun insert(weather: Weather)

    suspend fun remove(weather: Weather)

    fun getWeatherList(): Flow<List<Weather>>

}

class WeatherLocalDataSourceImpl(
    private val weatherDao: WeatherDao,
    private val ioDispatcher: CoroutineDispatcher
) : WeatherLocalDataSource {

    override suspend fun insert(weather: Weather) = withContext(ioDispatcher) {
        weatherDao.insert(weather.toEntity())
    }

    override suspend fun remove(weather: Weather) = withContext(ioDispatcher) {
        weatherDao.remove(weather.toEntity())
    }

    override fun getWeatherList(): Flow<List<Weather>> =
        weatherDao.getWeatherList().map { it.toModel() }.flowOn(ioDispatcher)

}
