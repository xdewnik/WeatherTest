package com.koolya.weathertest.core.data.api.adapter.error

import java.io.IOException

class ApiError(
    override val message: String,
    val code: Int,
    cause: Throwable?,
): IOException(message, cause) {
}