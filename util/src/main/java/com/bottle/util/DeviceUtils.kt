package com.bottle.util

import android.content.Context
import android.provider.Settings

/**
 * @ClassName DeviceUtils
 * @Author half_bottle
 * @Date 2017/12/1
 * @Description
 */
fun getAndroidId(context: Context): String {
    if(context == null){
        return ""
    }
    return Settings.System.getString(context.contentResolver, android.provider.Settings.Secure.ANDROID_ID)
}