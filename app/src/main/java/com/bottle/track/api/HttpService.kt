package com.bottle.track.api

import com.bottle.track.api.request.QueryPoi
import com.bottle.track.api.request.LoginUser
import com.bottle.track.api.request.UploadPoi
import com.bottle.track.api.result.LoginResult
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

/**
 * @ClassName HttpService
 * @Author half_bottle
 * @Date 2017/12/2
 * @Description Trek app接口
 */
interface HttpService {

    // 测试接口
    @GET("/api/api.php")
    fun queryTest(): Observable<BaseResponseBean<Map<String, String>>>

    // 上传POI
    @POST("/api/route/uploadpoints")
    fun uploadPoints(@Body requesParams: BaseRequestBean<UploadPoi>): Observable<BaseResponseBean<Any>>

    // 查询POI
    @POST("/api/route/points")
    fun points(@Body requesParams: BaseRequestBean<QueryPoi>): Observable<BaseResponseBean<UploadPoi>>

    // 登录初始化
    @POST("/auth/init")
    fun authInit(@Body requesParams: BaseRequestBean<Any?>): Observable<BaseResponseBean<Any>>

    // 登录
    @POST("/api/auth/login")
    fun login(@Body requesParams: BaseRequestBean<LoginUser>): Observable<BaseResponseBean<LoginResult>>

    // 注销登录  reqbody:{login_token:"dafejfkfjkrgrgr"}
    @POST("/auth/logout")
    fun logout(@Body requesParams: BaseRequestBean<Map<String, Any>>): Observable<BaseResponseBean<List<Any>>>
}



















