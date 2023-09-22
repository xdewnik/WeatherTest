package com.koolya.weathertest.core.data.database.di

import androidx.room.Room
import com.koolya.weathertest.core.data.database.WeatherDatabase
import com.koolya.weathertest.core.data.database.dao.WeatherDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val DatabaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            WeatherDatabase::class.java, WeatherDatabase.scheme,
        ).build()
    }

    single<WeatherDao> {
        val db = get<WeatherDatabase>()
        db.weatherDao()
    }
}