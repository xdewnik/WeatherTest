package com.koolya.weathertest.feature.map.data.api

import com.koolya.weathertest.BuildConfig
import com.koolya.weathertest.feature.map.data.api.request.ExcludeRequest
import com.koolya.weathertest.feature.map.data.api.response.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {


    //todo replace to 3.0/onecall? need to pay
    @GET("/data/2.5/weather")
    suspend fun getWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude: ExcludeRequest = ExcludeRequest.CURRENT,
        @Query("appid") appid: String = BuildConfig.WEATHER_API_KEY,
    ): WeatherResponse

}