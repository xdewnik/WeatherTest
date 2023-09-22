package com.koolya.weathertest.feature.weather.domain.di

import com.koolya.weathertest.feature.weather.domain.GetWeatherListUseCase
import com.koolya.weathertest.feature.weather.domain.RemoveWeatherUseCase
import org.koin.dsl.module

val WeatherListDomainModule = module {

    factory { RemoveWeatherUseCase(get()) }

    factory { GetWeatherListUseCase(get()) }

}