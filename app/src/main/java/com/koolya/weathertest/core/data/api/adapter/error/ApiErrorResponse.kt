package com.koolya.weathertest.core.data.api.adapter.error

import com.google.gson.annotations.SerializedName

data class ApiErrorResponse(
    @SerializedName("cod")
    val code: Int,
    @SerializedName("message")
    val message: String,
)
