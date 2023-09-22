package com.koolya.weathertest.core.data.datasource.mapepr

import com.koolya.weathertest.core.data.database.entity.WeatherEntity
import com.koolya.weathertest.core.model.Weather

fun Weather.toEntity(): WeatherEntity = WeatherEntity(
    id = this.id,
    location = this.location,
    weather = this.weather,
    temp = this.temp
)

fun WeatherEntity.toModel(): Weather = Weather(
    id = this.id,
    location = this.location,
    weather = this.weather,
    temp = this.temp,
)

fun List<WeatherEntity>.toModel(): List<Weather> = this.map { it.toModel() }