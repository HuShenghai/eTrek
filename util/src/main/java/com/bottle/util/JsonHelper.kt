package com.bottle.util

import com.alibaba.fastjson.JSON

fun toJsonString(obj: Object): String{
    return JSON.toJSONString(obj)
}

fun <T> toObject(jsonString: String, cls: Class<T>): T{
    return JSON.parseObject(jsonString, cls)
}