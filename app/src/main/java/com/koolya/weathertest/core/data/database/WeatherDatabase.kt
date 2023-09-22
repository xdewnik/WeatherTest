package com.koolya.weathertest.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.koolya.weathertest.core.data.database.dao.WeatherDao
import com.koolya.weathertest.core.data.database.entity.WeatherEntity

@Database(entities = [WeatherEntity::class], version = WeatherDatabase.version)
abstract class WeatherDatabase : RoomDatabase() {
    companion object {
        const val scheme: String = "weather_database"
        const val version: Int = 1
    }

    abstract fun weatherDao(): WeatherDao
}