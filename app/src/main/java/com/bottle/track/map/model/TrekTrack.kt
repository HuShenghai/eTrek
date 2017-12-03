package com.bottle.track.map.model

/**
 * @ClassName TrekTrack
 * @Author half_bottle
 * @Date 2017/12/2
 * @Description Trek 中的一个完整的轨迹，记录总路程，总耗时，平均速度等
 */
class TrekTrack {

    val tracks: List<Track>       // 过程中的多段
    var distance: Double? = null  // 总里程，单位米
    var time: Int? = null         // 总耗时，单位秒
    var speed: Int? = null        // 平均速度，单位：m/s，显示的时候根据实际可以显示为km/s
    var beginTime: Long? = null   // 起始时间
    var endTime: Long? = null     // 结束时间

    constructor(tracks: List<Track>){
        this.tracks = tracks
    }
}