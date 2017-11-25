package com.bottle.util

import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import android.os.Build
import android.util.Log
import java.io.*
import java.lang.Thread.UncaughtExceptionHandler
import java.text.SimpleDateFormat
import java.util.*

class CrashExceptionHandler private constructor(context: Context) : UncaughtExceptionHandler {

    private var context: Context? = null
    private var defaultHandler: UncaughtExceptionHandler? = null
    private val info = HashMap<String, String>()

    init {
        init(context)
    }

    private fun init(context: Context) {
        this.context = context
        defaultHandler = Thread.getDefaultUncaughtExceptionHandler()
    }

    override fun uncaughtException(thread: Thread, ex: Throwable) {
        collectDeviceInfo(context)
        writeCrashInfoIntoFile(ex)
        defaultHandler!!.uncaughtException(thread, ex)
    }

    private fun writeCrashInfoIntoFile(ex: Throwable?) {
        if (ex == null) {
            return
        }
        // 设备信息
        val sb = StringBuilder()
        var value: String
        for (key in info.keys) {
            value = info[key]!!
            sb.append(key).append("=").append(value).append("\n")
        }
        // 错误信息
        val writer = StringWriter()
        val printWriter = PrintWriter(writer)
        ex.printStackTrace(printWriter)
        var cause: Throwable? = ex.cause
        while (cause != null) {
            cause.printStackTrace(printWriter)
            cause = cause.cause
        }
        printWriter.close()
        val result = writer.toString()
        sb.append(result)
        // 保存到文件
        var fos: FileOutputStream? = null
        val formatter = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss")
        val timestamp = System.currentTimeMillis()
        val time = formatter.format(Date())
        val fileName = "$time-$timestamp.txt"
        try {
            val file = ExternalStorageUtils.getDiskCacheDir(context!!, "crash")
            if (!file.exists()) {
                file.mkdirs()
            }
            val newFile = File(file.absolutePath + File.separator + fileName)
            fos = FileOutputStream(newFile)
            fos.write(sb.toString().toByteArray())
        } catch (fne: FileNotFoundException) {
            Log.e(TAG, fne.message)
        } catch (e: Exception) {
            Log.e(TAG, e.message)
        } finally {
            if (fos != null) {
                try {
                    fos.close()
                } catch (e: IOException) {
                    Log.e(TAG, e.message)
                }

            }
        }
    }

    private fun collectDeviceInfo(context: Context?) {
        try {
            val pm = context!!.packageManager
            val pi = pm.getPackageInfo(context.packageName, PackageManager.GET_ACTIVITIES)
            if (pi != null) {
                val versionName = if (pi.versionName == null) "null" else pi.versionName
                val versionCode = pi.versionCode.toString() + ""
                info.put("versionName", versionName)
                info.put("versionCode", versionCode)
            }
        } catch (e: NameNotFoundException) {
            e.printStackTrace()
        }

        val fields = Build::class.java.declaredFields
        try {
            for (field in fields) {
                field.isAccessible = true
                info.put(field.name, field.get(null).toString())
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    companion object {

        val TAG = "CrashExceptionHandler"
        private var instance: CrashExceptionHandler? = null

        fun getInstance(context: Context): CrashExceptionHandler {
            if (instance == null) {
                instance = CrashExceptionHandler(context)
            }
            return instance!!
        }
    }

}