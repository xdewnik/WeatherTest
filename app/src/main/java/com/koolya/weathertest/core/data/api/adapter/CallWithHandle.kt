package com.koolya.weathertest.core.data.api.adapter

import com.google.gson.Gson
import com.koolya.weathertest.core.data.api.adapter.error.ApiError
import com.koolya.weathertest.core.data.api.adapter.error.ApiErrorResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response


class CallWithHandle(
    private val delegate: Call<Any>,
    private val gson: Gson
) : Call<Any> by delegate {

    override fun enqueue(callback: Callback<Any>) {
        delegate.enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (response.isSuccessful) {
                    callback.onResponse(call, response)
                } else {
                    callback.onFailure(
                        call,
                        mapper(HttpException(response))
                    )
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                callback.onFailure(call, mapper(t))
            }
        })
    }

    override fun clone() = CallWithHandle(delegate.clone(), gson)

    private fun mapper(throwable: Throwable): Throwable {
        return when (throwable) {
            is HttpException -> {
                val responseBody = throwable.response()?.errorBody()?.string().orEmpty()
                val error: ApiErrorResponse? = try {
                    gson.fromJson(
                        responseBody,
                        ApiErrorResponse::class.java
                    )
                } catch (e: Exception) {
                    null
                }
                return error?.let {
                    ApiError(
                        message = error.message,
                        code = error.code,
                        cause = throwable
                    )
                } ?: throwable
            }

            else -> {
                throwable
            }
        }
    }
}
