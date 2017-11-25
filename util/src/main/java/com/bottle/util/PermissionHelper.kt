package com.bottle.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.text.TextUtils

private val TAG = "PermissionHelper"

fun hasPermission(activity: Context, permission: String): Boolean {
    return ContextCompat.checkSelfPermission(activity,
            permission) == PackageManager.PERMISSION_GRANTED
}

fun requestPermission(activity: Activity, permission: String, requestCode: Int) {
    if (!hasM() || TextUtils.isEmpty(permission)) {
        return
    }
    if (!hasPermission(activity, permission)) {
        val permissions = arrayOf(permission)
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
            // 官方建议向用户解析为什么要使用这个权限，然后再请求权限
            ActivityCompat.requestPermissions(activity, permissions, requestCode)
        } else {
            ActivityCompat.requestPermissions(activity, permissions, requestCode)
        }
    }
}

fun requestPermissions(activity: Activity, permission: Array<String>, requestCode: Int) {
    if (!hasM() || permission.size < 1) {
        return
    }
    ActivityCompat.requestPermissions(activity, permission, requestCode)
}

fun requestDrawOverlaysPermission(activity: Activity, requestCode: Int) {
    val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:" + activity.packageName))
    activity.startActivityForResult(intent, requestCode)
}



















