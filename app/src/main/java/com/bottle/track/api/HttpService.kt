package com.bottle.track.api

import com.bottle.track.api.bean.BaseResponseBean
import io.reactivex.Observable
import retrofit2.http.GET

interface HttpService {

    @GET("/api/api.php")
    fun queryTest(): Observable<BaseResponseBean<Map<String, String>>>
}