package com.koolya.weathertest.core.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.koolya.weathertest.core.data.database.entity.WeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(weather: WeatherEntity)

    @Delete
    fun remove(weather: WeatherEntity)

    @Query("SELECT * FROM WeatherEntity")
    fun getWeatherList(): Flow<List<WeatherEntity>>

}