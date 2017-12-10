package com.bottle.track

class TrekEvent<T>(type: Int, description: String, event: T) {

    var type: Int? = 0
    private set

    var info: String? = "event bus"
    private set

    var event: T? = null
    private set

    init{
        this.type = type
        this.info = description
        this.event = event
    }

    companion object {
        val TYPE_RECEIVE_LOCATION = 1    // 接收到定位信息
        val TYPE_RECORD_TRACK = 2        // 记录一条轨迹
        val TYPE_UPDATE_TRACK = 3        // 数据库中的一条轨迹被修改
    }
}