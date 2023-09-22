package com.koolya.weathertest.feature.weather.ui.di

import com.koolya.weathertest.feature.weather.ui.WeatherListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val WeatherListModule = module {
    viewModel {
        WeatherListViewModel(
            getWeatherListUseCase = get(),
            removeWeatherUseCase = get(),
        )
    }
}