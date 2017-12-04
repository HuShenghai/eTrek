package com.bottle.util

import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.net.URLEncoder
import java.util.regex.Pattern
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


fun isEmpty(s: String?): Boolean {
    if(s == null) {
        return true
    }
    return s.isEmpty()
}

/**
 *
 * @Title: containSpecialCharacter
 * @Description: 判断是否包含特殊字符，ascll码33-127之内的不是特殊字符
 * 用于密码检测，下面所有字符测试通过，不是特殊字符
 * ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789abcdef
 * ghijklmnopqrstuvwxyz<>\\+=_-)([]{}|*&^%$#@!~`';/.,
 * @param @param str
 * @param @return
 * @return boolean 返回true表示包含有特殊字符
 * @throws
 */
fun containSpecialCharacter(str: String): Boolean {
    val regEx = "((?=[^\\x21-\\x7e]+))"
    val p = Pattern.compile(regEx)
    val m = p.matcher(str)
    return m.find()
}

/**
 *
 * @Title: numberAndCharacterOnly
 * @Description: 只能包括字母、数字和下划线 ，其他为非法字符
 * @param @param str
 * @param @return
 * @return boolean
 * @throws
 */
fun numberAndCharacterOnly(str: String): Boolean {
    val reg = "^[0-9a-zA-Z_]{1,}$"
    val p = Pattern.compile(reg)
    val m = p.matcher(str)
    return m.matches()
}

/**
 * 验证邮箱
 * @param email
 * @return
 */
fun isEmail(email: String): Boolean {
    var flag = false
    try {
        val check = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}"
        val regex = Pattern.compile(check)
        val matcher = regex.matcher(email)
        flag = matcher.matches()
    } catch (e: Exception) {
        flag = false
    }

    return flag
}

/**
 *
 * @Title: isNumber
 * @Description: 判断一个字符串是否是数字(匹配手机号?)
 * @param @param str
 * @param @return
 * @return boolean
 * @throws
 */
fun isNumber(str: String): Boolean {
    var result = false
    try {
        val reg = "[0-9]{1,}"
        val p = Pattern.compile(reg)
        val matcher = p.matcher(str)
        result = matcher.matches()
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return result
}

/**
 * URL编码
 * @param encodeString 编码前的字符串
 * @return 返回一个URL编码后的字符串
 */
fun urlEncode(encodeString: String): String {
    var result: String
    try {
        result = URLEncoder.encode(encodeString, "UTF-8")
    } catch (e: UnsupportedEncodingException) {
        e.printStackTrace()
        result = encodeString
    }
    return result
}

/**
 * URL解码，UTF-8编码方式
 * @param encodeString 需要解码的字符串
 * @return 如果解码失败，返回输入的 encodeString
 */
fun urlDecode(encodeString: String): String {
    var result: String
    try {
        result = URLDecoder.decode(encodeString, "UTF-8")
    } catch (e: UnsupportedEncodingException) {
        e.printStackTrace()
        result = encodeString
    }
    return result
}


/**
 * 从格式化唯一id中获取时间戳
 * @param fileCreateTime {@link com.bottle.util#getFormatId()}
 * @return 格式不对返回0，否则返回时间戳
 */
fun formatFileCreateTime(fileCreateTime: String?): Long {
    if (fileCreateTime == null || !isNumber(fileCreateTime)) {
        return 0L
    }
    if (fileCreateTime.length >= 18) {
        val year = fileCreateTime.substring(0, 2)
        val month = fileCreateTime.substring(2, 4)
        val date = fileCreateTime.substring(4, 6)
        val hour = fileCreateTime.substring(6, 8)
        val minute = fileCreateTime.substring(8, 10)
        val second = fileCreateTime.substring(10, 12)
        val millisecond = fileCreateTime.substring(12, 15)
        val dateFormat = "20%s/%s/%s %s:%s:%s.%s"
        val realDateTime = String.format(dateFormat, year, month, date, hour, minute, second, millisecond)
        val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS")
        var result = 0L
        try {
            val dateTime = sdf.parse(realDateTime)
            result = dateTime.time
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return result
    }
    return java.lang.Long.parseLong(fileCreateTime)
}

/**
 * 从时间戳获取一个唯一id，格式为：yyMMddHHmmssSSSxyz 即年(只取后面两位)月日时分秒毫秒+三位随机数(18位)
 * @param currentTimeMillis
 * @return yyMMddHHmmssSSSxyz yy只取后面两位
 */
fun getFormatId(currentTimeMillis: Long): String {
    val date = Date(currentTimeMillis)
    val simpleDateFormat = SimpleDateFormat("yyyyMMddHHmmssSSS")
    val result = simpleDateFormat.format(date)
    val result2 = result.substring(result.length - 15, result.length)
    val random = Random()
    val first = random.nextInt(10)
    val second = random.nextInt(10)
    val third = random.nextInt(10)
    return result2 + first + second + third
}
