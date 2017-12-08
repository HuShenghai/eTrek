package com.bottle.util

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.provider.MediaStore.Images.ImageColumns
import android.text.TextUtils
import android.util.Log

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.Closeable
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.text.DecimalFormat
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

private val TAG = "FileHelper"

/**
 * 返回自定文件或文件夹的大小
 *
 * @param f
 * @return
 * @throws Exception
 */
@Throws(Exception::class)
fun getFileSizes(f: File): Long {// 取得文件大小
    var s: Long = 0
    if (f.exists()) {
        var fis: FileInputStream?
        fis = FileInputStream(f)
        s = fis.available().toLong()
        fis.close()
    } else {
        f.createNewFile()
        Log.e(TAG, "文件不存在")
    }
    return s
}

/**
 * 递归 取得文件夹大小
 * @param f
 * @return
 * @throws Exception
 */
@Throws(Exception::class)
fun getFileSize(f: File): Long {
    var size: Long = 0
    val flist = f.listFiles()
    for (i in flist.indices) {
        if (flist[i].isDirectory) {
            size = size + getFileSize(flist[i])
        } else {
            size = size + flist[i].length()
        }
    }
    return size
}

fun FormetFileSize(fileS: Long): String {// 转换文件大小
    val df = DecimalFormat("#0.00")
    var fileSizeString: String
    if (fileS < 1024) {
        fileSizeString = df.format(fileS.toDouble()) + "B"
    } else if (fileS < 1048576) {
        fileSizeString = df.format(fileS.toDouble() / 1024) + "K"
    } else if (fileS < 1073741824) {
        fileSizeString = df.format(fileS.toDouble() / 1048576) + "M"
    } else {
        fileSizeString = df.format(fileS.toDouble() / 1073741824) + "G"
    }
    return fileSizeString
}

/**
 * 递归求取目录文件个数
 * @param f
 * @return
 */
fun getlist(f: File): Long {
    var size: Long
    val flist = f.listFiles()
    size = flist.size.toLong()
    for (i in flist.indices) {
        if (flist[i].isDirectory) {
            size = size + getlist(flist[i])
            size--
        }
    }
    return size

}

/**
 * @param name
 * @param encode 传null，或者空，则使用默认UTF-8
 * @return String
 * @Title: readFileString
 * @Description: 读取指定路径的一个文本文件
 */
fun readFileString(name: String, code: String): String {
    var encode = code
    val file = File(name)
    var result = ""
    var bReader: BufferedReader? = null
    var fs: FileInputStream?
    var ir: InputStreamReader?
    if (file.exists() && !file.isDirectory) {
        if (TextUtils.isEmpty(encode)) {
            encode = "UTF-8"
        }
        try {
            fs = FileInputStream(file)
            ir = InputStreamReader(fs, encode)
            bReader = BufferedReader(ir)
            val sb = StringBuilder()
            var line: String = bReader.readLine()
            while (!TextUtils.isEmpty(line)) {
                sb.append(line)
                line = bReader.readLine()
            }
            result = sb.toString()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            Log.i("FileHelper", e.message)
        } catch (e: IOException) {
            e.printStackTrace()
            Log.i("FileHelper", e.message)
        } finally {
            close(bReader)
        }
    }
    return result
}

/**
 * @param path
 * @param content
 * @param encode  传null，或者空，则使用默认UTF-8
 * @Title: writeTxtFile
 * @Description: 向一个文件里面写String
 */
fun writeTxtFile(path: String, content: String, code: String): Boolean {
    var encode = code
    var writer: BufferedWriter? = null
    var write: OutputStreamWriter?
    var fs: FileOutputStream?
    var result = false
    try {
        if (TextUtils.isEmpty(encode)) {
            encode = "UTF-8"
        }
        val f = File(path)
        if (!f.parentFile.exists()) {
            f.parentFile.mkdirs()
        }
        if (!f.exists()) {
            f.createNewFile()
        }
        fs = FileOutputStream(f)
        write = OutputStreamWriter(fs, encode)
        writer = BufferedWriter(write)
        writer.write(content)
        writer.flush() // 写入存储卡
        result = true
    } catch (e: Exception) {
        e.printStackTrace()
        Log.i("FileHelper", e.message)
    } finally {
        close(writer)
    }
    return result
}

fun rename(origin: String, destination: String): Boolean {
    val originFile = File(origin)
    return if (!originFile.exists() || TextUtils.isEmpty(destination)) {
        false
    } else originFile.renameTo(File(destination))
}

/**
 * 解压assets的zip压缩文件到指定目录
 *
 * @param context         上下文对象
 * @param assetName       压缩文件名
 * @param outputDirectory 输出目录
 * @param isReWrite       是否覆盖
 * @throws IOException
 */
@Throws(IOException::class)
fun unAssertsZip(context: Context, assetName: String,
                 outputDirectory: String, isReWrite: Boolean) {
    val inputStream = context.assets.open(assetName)
    unZip(inputStream, outputDirectory, isReWrite)
}

@Throws(IOException::class)
fun unFileZip(fileName: String, outputDirectory: String, isReWrite: Boolean) {
    val file = File(fileName)
    if (!file.exists())
        return
    val inputStream = FileInputStream(file)
    unZip(inputStream, outputDirectory, isReWrite)
}


@Throws(IOException::class)
fun unZip(inputStream: InputStream,
          outputDirectory: String, isReWrite: Boolean) {
    var file = File(outputDirectory)
    if (!file.exists()) {
        file.mkdirs()
    }
    val zipInputStream = ZipInputStream(inputStream)
    var zipEntry: ZipEntry?
    try {
        zipEntry = zipInputStream.nextEntry
        val buffer = ByteArray(1024 * 1024)
        var count: Int
        while (zipEntry != null) {
            if (zipEntry.isDirectory) {
                file = File(outputDirectory + File.separator + zipEntry.name)
                if (isReWrite || !file.exists()) {
                    file.mkdir()
                }
            } else {
                file = File(outputDirectory + File.separator + zipEntry.name)
                // 文件需要覆盖或者文件不存在，则解压文件
                if (isReWrite || !file.exists()) {
                    file.createNewFile()
                    val fileOutputStream = FileOutputStream(file)
                    count = zipInputStream.read(buffer)
                    while(count > 0){
                        fileOutputStream.write(buffer, 0, count)
                        count = zipInputStream.read(buffer)
                    }
                    fileOutputStream.close()
                }
            }
            // 定位到下一个文件入口
            zipEntry = zipInputStream.nextEntry
        }
    } catch (e: IOException) {
        e.printStackTrace()
        throw e
    } finally {
        try {
            zipInputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

}

/**
 * @param context
 * @param assetFileName
 * @param newFileName
 * @return 结果
 * @Title: copyAssetFile
 * @Description: 将assets目录的文件复制到指定目录
 */
fun copyAssetFile(context: Context, assetFileName: String, newFileName: String): Boolean {
    var fs: FileOutputStream? = null
    var inputStream: InputStream? = null
    var result = false
    try {
        val assetManager = context.assets
        inputStream = assetManager.open(assetFileName)
        val f = File(newFileName.substring(0, newFileName.lastIndexOf(File.separator)))
        if (!f.exists()) {
            f.mkdirs()
        }
        fs = FileOutputStream(newFileName)
        if (inputStream != null) {
            val buffer = ByteArray(1024)
            var byteread = inputStream.read(buffer)
            while (byteread != -1) {
                fs.write(buffer, 0, byteread)
                byteread = inputStream.read(buffer)
            }
        }
        result = true
    } catch (e: IOException) {
        e.printStackTrace()
        Log.e(TAG, "copyAssetFile exception" + e.message)
    } finally {
        close(fs)
        close(inputStream)
    }
    return result
}

/**
 * @param context  上下文
 * @param fileName 文件名称
 * @return String
 * @throws IOException
 * @Title: readAssetFile
 * @Description: 读取asset目录下的一个文本文件
 */
@Throws(IOException::class)
fun readAssetFile(context: Context, fileName: String): String {
    val sb = StringBuilder()
    var bf: BufferedReader? = null
    try {
        val assetManager = context.assets
        val `in` = assetManager.open(fileName)
        val inReader = InputStreamReader(`in`)
        bf = BufferedReader(inReader)
        var line: String? = bf.readLine()
        while (!TextUtils.isEmpty(line)) {
            sb.append(line)
            line = bf.readLine()
        }
    } catch (e: IOException) {
        throw e
    } finally {
        try {
            if (bf != null)
                bf.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
    return sb.toString()
}

/**
 * @param c
 * @param uri
 * @return String
 * @Title: getRealFilePath
 * @Description: 通过Uri获取本地资源的绝对路径
 */
fun getRealFilePath(c: Context, uri: Uri?): String? {
    if (uri == null) {
        return null
    }
    val scheme = uri.scheme
    var data: String? = null
    if (scheme == null) {
        data = uri.path
    } else if (ContentResolver.SCHEME_FILE == scheme) {
        data = uri.path
    } else if (ContentResolver.SCHEME_CONTENT == scheme) {
        val cursor = c.contentResolver.query(uri,
                arrayOf(ImageColumns.DATA), null, null, null)
        if (cursor != null && cursor.moveToFirst()) {
            val index = cursor.getColumnIndex(ImageColumns.DATA)
            if (index > -1) {
                data = cursor.getString(index)
            }
        }
    }
    return data
}

/**
 * @param oldPath
 * @param newPath
 * @Title: copy
 * @Description: 复制文件到指定路径
 */
fun copy(oldPath: String, newPath: String) {
    val oldFile = File(oldPath)
    copy(oldFile, newPath)
}

/**
 * @param oldfile
 * @param newPath
 * @return 结果
 * @Title: copy
 * @Description: 将文件复制到指定路径
 */
fun copy(oldfile: File?, newPath: String): Boolean {
    var result = false
    if (oldfile == null || TextUtils.isEmpty(newPath)) {
        return result
    }
    var inStream: InputStream? = null
    var fs: FileOutputStream? = null
    try {
        var byteread: Int
        inStream = FileInputStream(oldfile)
        fs = FileOutputStream(newPath)
        if (oldfile.exists()) {
            val buffer = ByteArray(1024)
            byteread = inStream.read(buffer)
            while (byteread != -1) {
                fs.write(buffer, 0, byteread)
                byteread = inStream.read(buffer)
            }
            result = true
        }
    } catch (e: Exception) {
        e.printStackTrace()
    } finally {
        close(inStream)
        close(fs)
    }
    return result
}

/**
 * @param file
 * @Title: delete
 * @Description: 删除文件，包括文件夹
 */
fun delete(file: File?) {
    if (file == null)
        return
    if (file.isFile) {
        file.delete()
        return
    }
    if (file.isDirectory) {
        val childFiles = file.listFiles()
        if (childFiles == null || childFiles.size == 0) {
            file.delete()
            return
        }
        for (i in childFiles.indices) {
            delete(childFiles[i])
        }
        file.delete()
    }
}

/**
 * @param c
 * @Title: close
 * @Description: 关闭一个流
 */
private fun close(c: Closeable?) {
    if (c != null) {
        try {
            c.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }
}


