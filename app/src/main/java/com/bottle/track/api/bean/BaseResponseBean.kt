package com.bottle.track.api.bean

/**
 * @Date 2017/11/21 11:02
 * @Author halfbottle
 * @Version 1.0.0
 * @Description {"status":true,"info":"success","response":{"keya":"Android","keyb":"iOS"}}
 */
class BaseResponseBean<T>{

    var status: Boolean? = false
    private set

    var info: String? = null
    private set

    var response: T? = null
    private set

}