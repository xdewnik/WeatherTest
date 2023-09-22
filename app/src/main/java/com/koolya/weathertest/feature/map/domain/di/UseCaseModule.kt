package com.koolya.weathertest.feature.map.domain.di

import com.koolya.weathertest.feature.map.domain.GetWeatherInfoUseCase
import org.koin.dsl.module

val MapUseCaseModule = module {
    factory { GetWeatherInfoUseCase(get()) }
}