package com.bottle.track.model

/**
 * @ClassName Poi
 * @Author half_bottle
 * @Date 2017/12/2
 * @Description
 */
class Poi {

    val longitude: Double
    val latitude: Double
    val altitude: Double
    val logtime: Long

    constructor(longitude: Double, latitude: Double, altitude: Double, logtime: Long){
        this.longitude = longitude
        this.latitude = latitude
         this.altitude = altitude
        this.logtime = logtime
    }
}