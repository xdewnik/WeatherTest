package com.koolya.weathertest.core.data.api.adapter

import com.google.gson.Gson
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ErrorCallAdapterFactory(private val gson: Gson) : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, Call<*>>? {
        if (getRawType(returnType) != Call::class.java || returnType !is ParameterizedType || returnType.actualTypeArguments.size != 1) {
            return null
        }

        val delegate = retrofit.nextCallAdapter(this, returnType, annotations)
        @Suppress("UNCHECKED_CAST")
        return ErrorCallAdapter(
            delegateAdapter = delegate as CallAdapter<Any, Call<*>>,
            gson = gson
        )
    }

    inner class ErrorCallAdapter(
        private val delegateAdapter: CallAdapter<Any, Call<*>>,
        private val gson: Gson
    ) : CallAdapter<Any, Call<*>> by delegateAdapter {

        override fun adapt(call: Call<Any>): Call<*> {
            return delegateAdapter.adapt(CallWithHandle(call, gson))
        }
    }

}
