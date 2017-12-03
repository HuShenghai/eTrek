package com.bottle.track.map.model

/**
 * @ClassName GeoPoint
 * @Author half_bottle
 * @Date 2017/12/2
 * @Description Trek几何模型中的点，球坐标表示(lng, lat, alt)
 */
open class GeoPoint {

    val longitude: Double  // 经度
    val latitude: Double   // 纬度
    val altitude: Double   // 海拔

    constructor(longitude: Double, latitude: Double, altitude: Double){
        this.longitude = longitude
        this.latitude = latitude
         this.altitude = altitude
    }
}