package com.koolya.weathertest.ui.feature.map.di

import com.koolya.weathertest.ui.feature.map.ui.MapViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val MapModule = module {
    viewModel { MapViewModel() }
}