package com.bottle.track.api

import com.bottle.track.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Api {

    init {
        baseUrl=  when (BuildConfig.DEBUG) {
            true -> " http://fmeetu.com"
            false -> " http://fmeetu.com" // http://fmeetu.com/api/api.php
        }
    }

    private val gson: Gson
    private val baseUrl: String
    private val okHttpClient: OkHttpClient
    private val retrofit: Retrofit
    val httpService: HttpService

    constructor() {
        val builder = OkHttpClient.Builder()
        //设置缓存
        // builder.addInterceptor(netWorkCheckInterceptor)
        // builder.addInterceptor(addQueryParameterInterceptor)
        // builder.addNetworkInterceptor(cacheInterceptor)
        // builder.addInterceptor(cacheInterceptor)
        // builder.cache(Cache(cacheFile, (1024 * 1024 * 100).toLong()))
        builder.connectTimeout(10, TimeUnit.SECONDS)
        builder.readTimeout(20, TimeUnit.SECONDS)
        builder.writeTimeout(20, TimeUnit.SECONDS)
        //错误重连
        builder.retryOnConnectionFailure(true)
        builder.addInterceptor(BaseParamInterceptor()) // 添加公共参数
        okHttpClient = builder.build()

        gson = GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").serializeNulls().create()
        retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build()
        httpService = retrofit.create(HttpService::class.java)
    }

    companion object {
        val api :Api by lazy { Api() }
    }
}