package com.koolya.weathertest.feature.map.data.api.response

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("coord") var coordinateResponse: CoordinateResponse? = null,
    @SerializedName("weather") var weatherStateResponses: List<WeatherStateResponse>? = null,
    @SerializedName("base") var base: String? = null,
    @SerializedName("main") var characteristicsResponse: CharacteristicsResponse? = null,
    @SerializedName("visibility") var visibility: Int? = null,
    @SerializedName("wind") var windResponse: WindResponse? = null,
    @SerializedName("clouds") var cloudsResponse: CloudsResponse? = null,
    @SerializedName("dt") var dt: Int? = null,
    @SerializedName("sys") var sysResponse: SysResponse? = null,
    @SerializedName("timezone") var timezone: Int? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("cod") var cod: Int? = null
)

data class CoordinateResponse(
    @SerializedName("lon") var lon: Double? = null,
    @SerializedName("lat") var lat: Double? = null
)

data class WeatherStateResponse(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("main") var main: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("icon") var icon: String? = null
)

data class CharacteristicsResponse(
    @SerializedName("temp") var temp: Double? = null,
    @SerializedName("feels_like") var feelsLike: Double? = null,
    @SerializedName("temp_min") var tempMin: Double? = null,
    @SerializedName("temp_max") var tempMax: Double? = null,
    @SerializedName("pressure") var pressure: Int? = null,
    @SerializedName("humidity") var humidity: Int? = null,
    @SerializedName("sea_level") var seaLevel: Int? = null,
    @SerializedName("grnd_level") var grndLevel: Int? = null
)

data class WindResponse(
    @SerializedName("speed") var speed: Double? = null,
    @SerializedName("deg") var deg: Int? = null,
    @SerializedName("gust") var gust: Double? = null
)

data class CloudsResponse(
    @SerializedName("all") var all: Int? = null
)

data class SysResponse(
    @SerializedName("type") var type: Int? = null,
    @SerializedName("id") var id: Int? = null,
    @SerializedName("country") var country: String? = null,
    @SerializedName("sunrise") var sunrise: Int? = null,
    @SerializedName("sunset") var sunset: Int? = null
)