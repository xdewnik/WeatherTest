package com.koolya.weathertest.feature.map.ui.di

import com.koolya.weathertest.feature.map.ui.MapViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val MapUiModule = module {
    viewModel {
        MapViewModel(
            getWeatherInfoUseCase = get()
        )
    }
}