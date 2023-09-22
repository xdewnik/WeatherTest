package com.koolya.weathertest

import android.app.Application
import com.koolya.weathertest.core.data.database.di.DatabaseModule
import com.koolya.weathertest.core.data.datasource.di.CoreLocalDataSourceModule
import com.koolya.weathertest.core.di.DispatchersModule
import com.koolya.weathertest.feature.map.data.api.di.ApiModule
import com.koolya.weathertest.feature.map.data.datasource.di.MapDataSourceModule
import com.koolya.weathertest.feature.map.domain.di.MapUseCaseModule
import com.koolya.weathertest.feature.map.ui.di.MapUiModule
import com.koolya.weathertest.feature.weather.domain.di.WeatherListDomainModule
import com.koolya.weathertest.feature.weather.ui.di.WeatherListModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class WeatherApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {

            androidLogger()

            androidContext(this@WeatherApp)
            // Load modules
            modules(
                listOf(
                    DispatchersModule,
                    ApiModule,
                    MapUiModule,
                    MapDataSourceModule,
                    MapUseCaseModule,
                    DatabaseModule,
                    WeatherListModule,
                    WeatherListDomainModule,
                    CoreLocalDataSourceModule,
                )
            )
        }
    }

}