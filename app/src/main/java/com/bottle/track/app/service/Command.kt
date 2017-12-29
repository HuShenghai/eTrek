package com.bottle.track.app.service

import java.io.Serializable

/**
 * @ClassName Command
 * @Author half_bottle
 * @Date 2017/12/3
 * @Description Service 的参数
 */
class Command<T> constructor(val command: Int, val msg: String, val data: T): Serializable{

    companion object {
        val START_LOCATION = 1001    // 开始定位
        val STOP_LOCATION = 1002     // 停止定位
        val START_TRACKING = 2001    // 开始记录轨迹
        val STOP_TRACKING = 2002     // 停止记录轨迹
    }

}