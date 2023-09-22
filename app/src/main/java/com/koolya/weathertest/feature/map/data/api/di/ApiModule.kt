package com.koolya.weathertest.feature.map.data.api.di

import com.google.gson.GsonBuilder
import com.koolya.weathertest.BuildConfig
import com.koolya.weathertest.feature.map.data.api.WeatherApi
import com.koolya.weathertest.core.data.api.adapter.ErrorCallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val ApiModule = module {

    single {
        GsonBuilder()
            .serializeNulls()
            .create()
    }

    factory { GsonConverterFactory.create(get()) }

    single {
        HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    single {
        OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(get<GsonConverterFactory>())
            .addCallAdapterFactory(ErrorCallAdapterFactory(get()))
            .client(get())
            .build()
    }

    single { get<Retrofit>().create(WeatherApi::class.java) }

}