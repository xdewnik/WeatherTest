package com.koolya.weathertest.feature.map.data.datasource.di

import com.koolya.weathertest.feature.map.data.datasource.WeatherRemoteDataSource
import com.koolya.weathertest.feature.map.data.datasource.WeatherRemoteDataSourceImpl
import com.koolya.weathertest.core.di.Dispatcher
import org.koin.core.qualifier.named
import org.koin.dsl.module

val MapDataSourceModule = module {

    factory<WeatherRemoteDataSource> {
        WeatherRemoteDataSourceImpl(
            weatherApi = get(),
            ioDispatcher = get(named(Dispatcher.IO))
        )
    }

}