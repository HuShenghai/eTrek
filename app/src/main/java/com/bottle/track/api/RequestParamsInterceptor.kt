package com.bottle.track.api

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class RequestParamsInterceptor : Interceptor {

//    private val VERSION: String = "version"
//    private val PLATFORM: String = "platform"
    private val TAG: String = "ParamsInterceptor"

    override fun intercept(chain: Interceptor.Chain): Response {
        val oldRequest = chain.request()
        val method = oldRequest.method()
        val header = oldRequest.headers().toString()
        val body = oldRequest.body().toString()
        Log.d(TAG, "url   :" + oldRequest.url().toString())
        Log.d(TAG, "method:" + method)
        Log.d(TAG, "header: " + header)
        Log.d(TAG, "body  : " + body)
//        val authorizedUrlBuilder = oldRequest.url()
//                .newBuilder()
//                .scheme(oldRequest.url().scheme())
//                .host(oldRequest.url().host())
//                 .addQueryParameter(VERSION, "1.0.0")
//                 .addQueryParameter(PLATFORM, "android")
//        val newRequest = oldRequest.newBuilder()
//                .method(oldRequest.method(), oldRequest.body())
//                .url(authorizedUrlBuilder.build())
//                .build()
//        Log.d("webapi", newRequest.toString())
        return chain.proceed(oldRequest)
    }
}