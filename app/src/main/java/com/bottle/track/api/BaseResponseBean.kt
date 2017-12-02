package com.bottle.track.api

/**
 * @Date 2017/11/21 11:02
 * @Author halfbottle
 * @Version 1.0.0
 * @Description {"rescode":true,"info":"success","result":{"keya":"Android","keyb":"iOS"}}
 */
class BaseResponseBean<T>{

    var rescode: Int? = null
    private set

    var info: String? = null
    private set

    var result: T? = null
    private set

}