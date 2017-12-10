package com.bottle.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.lang.reflect.Type
import java.util.*

/**
 * @Date 2017/11/21 15:59
 * @Author halfbottle
 * @Version 1.0.0
 * @Description
 */
class GsonHelper private constructor(){

    val gson: Gson

    init{
        gson = GsonBuilder().setDateFormat("yyyy-MM-dd hh:mm:ss").serializeNulls().create()
    }

    fun toJson(obj: Any): String{
        return gson.toJson(obj)
    }

    fun <T> toAny(jsonString: String, cls: Class<T>): T{
        return gson.fromJson(jsonString, cls)
    }

    fun toObject(json: String, type: Type): Object{
        return gson.fromJson(json, type)
    }

    companion object {
        val gsonHelper: GsonHelper = GsonHelper()
    }

}