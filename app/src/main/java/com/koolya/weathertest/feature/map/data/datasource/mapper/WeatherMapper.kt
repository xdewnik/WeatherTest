package com.koolya.weathertest.feature.map.data.datasource.mapper

import com.koolya.weathertest.feature.map.data.api.response.WeatherResponse
import com.koolya.weathertest.core.model.Weather

fun WeatherResponse.toModel() = Weather(
    location = requireNotNull(this.name) { "location name is null" },
    temp = requireNotNull(this.characteristicsResponse?.temp?.toString()) { "temp is null" },
    weather = requireNotNull(this.weatherStateResponses?.get(0)?.main) { "weather is null" }
    )