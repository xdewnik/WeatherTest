package com.koolya.weathertest

import android.app.Application
import com.koolya.weathertest.core.di.DispatchersModule
import com.koolya.weathertest.ui.feature.map.di.MapModule
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
                )
            )
        }
    }

}