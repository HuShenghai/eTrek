package com.bottle.track.api.request

import com.bottle.track.map.model.Track
import java.util.ArrayList

/**
 * @Date 2017/12/6 20:42
 * @Author halfbottle
 * @Version 1.0.0
 * @Description
 */
class UploadTrack{

    constructor(login_token: String,
                tracks: ArrayList<Track>,
                distance: Double,
                time: Long,
                speed: Int,
                beginTime: Long,
                endTime: Long,
                description: String){
        this.login_token = login_token
        this.tracks = tracks
        this.distance = distance
        this.time = time
        this.speed = speed
        this.begin_time = beginTime
        this.end_time = endTime
        this.description = description
    }
    var login_token: String? = null
    var tracks: ArrayList<Track>? = null       // 过程中的多段
    var distance: Double? = null          // 总里程，单位米
    var time: Long = 0                 // 总耗时，单位秒
    var speed: Int = 0                // 平均速度，单位：m/s，显示的时候根据实际可以显示为km/s
    var begin_time: Long = 0           // 起始时间
    var end_time: Long = 0             // 结束时间
    var description: String? = null        // 描述
}