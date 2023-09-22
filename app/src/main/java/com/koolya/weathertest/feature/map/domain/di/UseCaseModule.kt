package com.koolya.weathertest.feature.map.domain.di

import com.koolya.weathertest.feature.map.domain.GetWeatherInfoUseCase
import com.koolya.weathertest.feature.map.domain.SaveWeatherInfoUseCase
import org.koin.dsl.module

val MapUseCaseModule = module {

    factory { GetWeatherInfoUseCase(get()) }

    factory { SaveWeatherInfoUseCase(get()) }

}