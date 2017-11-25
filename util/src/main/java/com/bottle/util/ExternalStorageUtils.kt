/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bottle.util

import android.app.ActivityManager
import android.content.Context
import android.os.Environment
import android.os.StatFs
import android.util.Log

import java.io.File
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * @Title ExternalStorageUtils
 * @Description 缓存的工具类,Android 2.2以上版本使用
 * @version
 */
class ExternalStorageUtils {

    companion object {

        private val TAG = ExternalStorageUtils::class.java.simpleName

        /**
         * 判断是否存在外部存储设备
         * @return 如果不存在返回false
         */
        fun hasExternalStorage(): Boolean {
            return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
        }

        /**
         * 获取目录使用的空间大小
         * @param path  检查的路径路径
         * @return 在字节的可用空间
         */
        fun getUsableSpace(path: File): Long {
            if (hasGingerbread()) {
                return path.usableSpace
            }
            val stats = StatFs(path.path)
            return stats.blockSize.toLong() * stats.availableBlocks.toLong()
        }

        /**
         * 获得外部应用程序缓存目录 :/Android/data/com.package.name/cache/
         *
         * @param context
         * 上下文信息
         * @return 外部缓存目录
         */
        fun getExternalCacheDir(context: Context): File? {
            if (hasFroyo()) {
                return context.externalCacheDir
            }
            val cacheDir = "/Android/data/" + context.packageName + "/cache/"
            return File(Environment.getExternalStorageDirectory().path + cacheDir)
        }

        /**
         * 检查如果外部存储器是内置的或是可移动的。
         *
         * @return 如果外部存储是可移动的(就像一个SD卡)返回为 true,否则false。
         */
        val isExternalStorageRemovable: Boolean
            get() = if (hasGingerbread()) {
                Environment.isExternalStorageRemovable()
            } else true

        /**
         * 一个散列方法,改变一个字符串(如URL)到一个散列适合使用作为一个磁盘文件名。
         */
        fun hashKeyForDisk(key: String): String {
            var cacheKey: String
            try {
                val mDigest = MessageDigest.getInstance("MD5")
                mDigest.update(key.toByteArray())
                cacheKey = bytesToHexString(mDigest.digest())
            } catch (e: NoSuchAlgorithmException) {
                cacheKey = key.hashCode().toString()
            }

            return cacheKey
        }

        private fun bytesToHexString(bytes: ByteArray): String {
            val sb = StringBuilder()
            for (i in bytes.indices) {
                val hex = Integer.toHexString(0xFF and bytes[i].toInt())
                if (hex.length == 1) {
                    sb.append('0')
                }
                sb.append(hex)
            }
            return sb.toString()
        }

        /**
         * 得到一个可用的缓存目录(如果外部可用使用外部,否则内部)。
         *
         * @param context 上下文信息
         * @param uniqueName 目录名字
         * @return 返回目录名字：/Android/data/包名/cache/uniqueName
         */
        fun getDiskCacheDir(context: Context, uniqueName: String): File {
            var cachePath = context.cacheDir.path
            try {
                if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() || !isExternalStorageRemovable) {
                    cachePath = getExternalCacheDir(context)!!.path
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e(TAG, e.message)
            }

            return File(cachePath + File.separator + uniqueName)
        }

        /**
         * 得到一个可用的缓存目录(如果外部可用使用外部,否则内部)。
         * @param context
         * @return 返回目录名字
         */
        fun getSystemDiskCacheDir(context: Context): File {
            var cachePath = context.cacheDir.path
            try {
                if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() || !isExternalStorageRemovable) {
                    cachePath = getExternalCacheDir(context)!!.path
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e(TAG, e.message)
            }

            return File(cachePath)
        }

        fun getMemoryClass(context: Context): Int {
            return (context
                    .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager).memoryClass
        }

    }

}
