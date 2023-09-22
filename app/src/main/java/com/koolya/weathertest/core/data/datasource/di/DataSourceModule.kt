package com.koolya.weathertest.core.data.datasource.di

import com.koolya.weathertest.core.data.datasource.WeatherLocalDataSource
import com.koolya.weathertest.core.data.datasource.WeatherLocalDataSourceImpl
import org.koin.dsl.module

val CoreLocalDataSourceModule = module {

    factory<WeatherLocalDataSource> {
        WeatherLocalDataSourceImpl(
            weatherDao = get(),
            ioDispatcher = get()
        )
    }

}