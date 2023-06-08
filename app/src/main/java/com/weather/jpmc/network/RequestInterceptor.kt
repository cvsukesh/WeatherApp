package com.weather.jpmc.network

import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.http.GET

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = when (request.method()) {
            GET_METHOD -> {
                val url = request.url()
                request.newBuilder()
                    .url(
                        url.newBuilder()
                            .addQueryParameter(APP_ID, API_KEY)
                            .addQueryParameter(UNITS, IMPERIAL)
                            .build()
                    )
                    .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                    .build()
            }
            else -> request
        }
        return chain.proceed(request)
    }

    companion object {
        const val API_KEY = "d52b0b4bbcb5e3e7dc4b78aec8b814d5"
        const val APP_ID = "appid"
        const val UNITS = "units"
        const val IMPERIAL = "imperial"

        const val GET_METHOD = "GET"
        const val CONTENT_TYPE = "Content-Type"
        const val APPLICATION_JSON = "application/json"
    }
}