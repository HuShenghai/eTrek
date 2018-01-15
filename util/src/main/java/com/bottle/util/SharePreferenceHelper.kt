package com.bottle.util

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor

/**
 * 简单类型数据缓存SharePreference
 * @author halfbottle
 */
class SharePreferenceHelper @JvmOverloads constructor(context: Context, private val setting: String = "setting") {

    private val sp: SharedPreferences

    init {
        sp = context.getSharedPreferences(setting, Context.MODE_MULTI_PROCESS)
    }
    fun put(key: String, value: Any) {
        val ed = sp.edit()
        if (value is Boolean) {
            ed.putBoolean(key, value)
        } else if (value is Float) {
            ed.putFloat(key, value)
        } else if (value is Int) {
            ed.putInt(key, value)
        } else if (value is Long) {
            ed.putLong(key, value)
        } else if (value is String) {
            ed.putString(key, value)
        }
        ed.commit()
    }

    fun getBoolean(key: String, defValue: Boolean?): Boolean {
        return sp.getBoolean(key, defValue!!)
    }

    fun getFloat(key: String, defValue: Float): Float {
        return sp.getFloat(key, defValue)
    }

    fun getInt(key: String, defValue: Int): Int {
        return sp.getInt(key, defValue)
    }

    fun getLong(key: String, defValue: Long?): Long {
        return sp.getLong(key, defValue!!)
    }

    fun getString(key: String, defValue: String): String? {
        return sp.getString(key, defValue)
    }
}
