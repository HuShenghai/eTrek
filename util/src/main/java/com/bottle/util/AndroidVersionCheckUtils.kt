
package com.bottle.util

import android.os.Build
/**
 * 当前Android系统版本是否在（ Donut） Android 1.6或以上
 *
 * @return
 */
fun hasDonut(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.DONUT
}

/**
 * 当前Android系统版本是否在（ Eclair） Android 2.0或 以上
 *
 * @return
 */
fun hasEclair(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR
}

/**
 * 当前Android系统版本是否在（ Froyo） Android 2.2或 Android 2.2以上
 *
 * @return
 */
fun hasFroyo(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO
}

/**
 * 当前Android系统版本是否在（ Gingerbread） Android 2.3x或 Android 2.3x 以上
 *
 * @return
 */
fun hasGingerbread(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD
}

/**
 * 当前Android系统版本是否在（ Honeycomb） Android3.1或 Android3.1以上
 *
 * @return
 */
fun hasHoneycomb(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB
}

/**
 * 当前Android系统版本是否在（ HoneycombMR1） Android3.1.1或 Android3.1.1以上
 *
 * @return
 */
fun hasHoneycombMR1(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1
}

/**
 * 当前Android系统版本是否在（ IceCreamSandwich） Android4.0或 Android4.0以上
 *
 * @return
 */
fun hasIcecreamsandwich(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH
}

fun hasJellyBean(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN // api 16
}

fun hasJellyBeanMR1(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1 // api 17
}

fun hasKitKat(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT // api 19
}

fun hasLollipop():Boolean{
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP // 21
}

fun hasM(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M // api 23
}