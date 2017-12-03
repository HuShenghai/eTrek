package com.bottle.track.map.model

/**
 * @ClassName Track
 * @Author half_bottle
 * @Date 2017/12/2
 * @Description Trek 中的一段轨迹，一个完整的轨迹由多段轨迹组合而成，每一段轨迹会记录距离，用时，速度等属性
 */
class Track(points: List<GeoPoint>): Polyline(points) {

    var beginTime: Long? = null   // 起始时间
    var endTime: Long? = null     // 结束时间
    var speed: Double? = null     // 速度，单位：m/s
    var time: Int? = null         // 耗时，单位：秒
    var description: String? = null

}