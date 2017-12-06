package com.bottle.track.api

import com.bottle.track.MyApplication

/**
 * @ClassName BaseInfoBean
 * @Author half_bottle
 * @Date 2017/12/2
 * @Description 基础参数，如device_id，app_version，request_time等，目前只有device_id
 */
class BaseInfoBean {

    val device_id: String

    constructor(){
        this.device_id = MyApplication.app.androidId!!
    }
}