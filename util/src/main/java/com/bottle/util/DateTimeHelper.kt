package com.bottle.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


val currDay: String
    @Throws(Exception::class)
    get() {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.format(Date())
    }

fun convertStrToDate(dateStr: String): Date? {
    var d: Date? = null
    val sdf = SimpleDateFormat(
            "yyyy-MM-dd hh:mm:ss", Locale.getDefault())
    try {
        d = sdf.parse(dateStr)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return d
}

fun dateFormat(d: Date): String {
    val format1 = SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return format1.format(d)
}

/**
 * @param @param  dateStr 时间戳
 * @param @return
 * @return String
 * @throws
 * @Title: dateFormat
 * @Description: 格式化时间输出yyyy-MM-dd hh:mm:ss
 */
fun dateFormat(time: String): String {
    val t = java.lang.Long.parseLong(time)
    val d = Date(t)
    return dateFormat(d)
}

fun stringForTime(timeMs: Int): String {
    val totalSeconds = timeMs / 1000
    val seconds = totalSeconds % 60
    val minutes = totalSeconds / 60 % 60
    val hours = totalSeconds / 3600

    return if (hours > 0) {
        String.format("%d:%02d:%02d", hours, minutes, seconds)
    } else {
        String.format("%02d:%02d", minutes, seconds)
    }
}
