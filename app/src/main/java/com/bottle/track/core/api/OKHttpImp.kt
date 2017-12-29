package com.bottle.track.core.api

import okhttp3.*
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * 只是熟悉一下OkHttp
 */
class OKHttpImp {

    private var JSON = MediaType.parse("application/json; charset=utf-8")
    private val okHttpClient:OkHttpClient

    constructor(){
        val builder = OkHttpClient.Builder()

        //设置缓存
        // builder.addInterceptor(netWorkCheckInterceptor)
        // builder.addInterceptor(addQueryParameterInterceptor)
        // builder.addNetworkInterceptor(cacheInterceptor)
        // builder.addInterceptor(cacheInterceptor)
        // builder.cache(Cache(cacheFile, (1024 * 1024 * 100).toLong()))

        //设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS)
        builder.readTimeout(20, TimeUnit.SECONDS)
        builder.writeTimeout(20, TimeUnit.SECONDS)
        //错误重连
        builder.retryOnConnectionFailure(true)
        okHttpClient = builder.build()
    }

    //////////// 同步方法 /////////
    @Throws(IOException::class)
    fun get(url: String): String {
        val request = Request.Builder().url(url).build()
        val response = okHttpClient.newCall(request).execute()
        return if (response.isSuccessful) {
            response.body()!!.string()
        } else {
            throw IOException("Unexpected code " + response)
        }
    }

    @Throws(IOException::class)
    fun post(url: String, json: String): String{
        val body: RequestBody  = RequestBody.create(JSON, json)
        val request: Request = Request.Builder()
                .url(url)
                .post(body)
                .build()
        val response: Response = okHttpClient.newCall(request).execute()
        if (response.isSuccessful) {
            return response.body()!!.string()
        } else {
            throw IOException("Unexpected code " + response)
        }
    }

    @Throws(IOException::class)
    fun post(url: String, map: Map<String, String>): String{
        val builder: FormBody.Builder = FormBody.Builder()
        for((k,v) in map){
            builder.add(k, v)
        }
        val formBody = builder.build()
        val request = Request.Builder()
                .url(url)
                .post(formBody)
                .build()
        val response = okHttpClient.newCall(request).execute()
        return if (response.isSuccessful) {
            response.body()!!.string()
        } else {
            throw IOException("Unexpected code " + response)
        }
    }

    //////////// 异步方法 //////////////////

    fun get(url: String, callback: Callback): Call{
        val request = Request.Builder().url(url).build()
        val call = okHttpClient.newCall(request)
        call.enqueue(callback)
        return call
    }

    fun post(url: String, json: String, callback: Callback): Call{
        val body: RequestBody  = RequestBody.create(JSON, json)
        val request: Request = Request.Builder()
                .url(url)
                .post(body)
                .build()
        val call = okHttpClient.newCall(request)
        call.enqueue(callback)
        return call
    }

    fun post(url: String, map: Map<String, String>, callback: Callback): Call{
        val builder: FormBody.Builder = FormBody.Builder()
        for((k,v) in map){
            builder.add(k, v)
        }
        val formBody = builder.build()
        val request = Request.Builder()
                .url(url)
                .post(formBody)
                .build()
        val call = okHttpClient.newCall(request)
        call.enqueue(callback)
        return call
    }


}

