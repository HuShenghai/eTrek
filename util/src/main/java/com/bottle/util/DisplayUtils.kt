package com.bottle.util

import android.content.Context

/**
 * 将px值转换为dip或dp值，保证尺寸大小不变
 *
 * @param pxValue
 * @param scale
 * （DisplayMetrics类中属性density）
 * @return
 */
fun px2dip(pxValue: Float, scale: Float): Int {
    return (pxValue / scale + 0.5f).toInt()
}

fun px2dip(context: Context, pxValue: Float): Int {
    val scale = context.resources
            .displayMetrics.density
    return (pxValue / scale + 0.5f).toInt()
}

/**
 * 将dip或dp值转换为px值，保证尺寸大小不变
 *
 * @param dipValue
 * @param scale
 * （DisplayMetrics类中属性density）
 * @return
 */
fun dip2px(dipValue: Float, scale: Float): Int {
    return (dipValue * scale + 0.5f).toInt()
}

fun dip2px(context: Context, dpValue: Float): Int {
    val scale = context.resources.displayMetrics.density
    return (dpValue * scale + 0.5f).toInt()
}

/**
 * 将px值转换为sp值，保证文字大小不变
 *
 * @param pxValue
 * @param fontScale
 * （DisplayMetrics类中属性scaledDensity）
 * @return
 */
fun px2sp(pxValue: Float, fontScale: Float): Int {
    return (pxValue / fontScale + 0.5f).toInt()
}

fun px2sp(context: Context, pxValue: Float): Int {
    val fontScale = context.resources.displayMetrics.density
    return (pxValue / fontScale + 0.5f).toInt()
}

/**
 * 将sp值转换为px值，保证文字大小不变
 *
 * @param spValue
 * @param fontScale
 * （DisplayMetrics类中属性scaledDensity）
 * @return
 */
fun sp2px(spValue: Float, fontScale: Float): Int {
    return (spValue * fontScale + 0.5f).toInt()
}

fun sp2px(context: Context, spValue: Float): Int {
    val fontScale = context.resources.displayMetrics.density
    return (spValue * fontScale + 0.5f).toInt()
}

/**
 *
 * @Title: getScreenWidth
 * @Description: 获取屏幕的宽度，单位像素，注意出错时返回0
 * @param @param context
 * @param @return
 * @return int
 * @throws
 */
fun getScreenWidth(context: Context): Int {
    var dm = context.resources.displayMetrics
    return if (dm == null) 0 else dm.widthPixels
}

/**
 *
 * @Title: getScreenHeight
 * @Description: 获取屏幕高度，单位像素
 * @param @param context
 * @param @return
 * @return int
 * @throws
 */
fun getScreenHeight(context: Context): Int {
    var dm = context.resources.displayMetrics
    return if (dm == null) 0 else dm.heightPixels
}










