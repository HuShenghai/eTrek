package com.bottle.track.api

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class BaseParamInterceptor: Interceptor {

    private val VERSION: String = "version"
    private val PLATFORM: String = "platform"

    override fun intercept(chain: Interceptor.Chain?): Response {
        val oldRequest = chain!!.request()
        val authorizedUrlBuilder = oldRequest.url()
                .newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host())
                .addQueryParameter(VERSION, "1.0.0")
                .addQueryParameter(PLATFORM, "android")
        val newRequest = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(authorizedUrlBuilder.build())
                .build()
        Log.d("webapi", newRequest.toString())
        return chain.proceed(newRequest)
    }
}