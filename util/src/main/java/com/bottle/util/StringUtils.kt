package com.bottle.util

import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.net.URLEncoder
import java.util.regex.Pattern

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