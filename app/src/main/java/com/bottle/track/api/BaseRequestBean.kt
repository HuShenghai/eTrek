package com.bottle.track.api

/**
 * @ClassName BaseRequestBean
 * @Author half_bottle
 * @Date 2017/12/1
 * @Description POST请求的body
 */
class BaseRequestBean<T>{

    val baseinfo: BaseInfoBean
    val reqbody: T

    constructor(requestBody: T){
        this.baseinfo = BaseInfoBean()
        this.reqbody = requestBody
    }

}