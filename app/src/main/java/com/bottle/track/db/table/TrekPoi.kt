package com.bottle.track.db.table

/**
 * @ClassName TrekPoi
 * @Author half_bottle
 * @Date 2017/12/3
 * @Description eTrek 兴趣点的本地数据表
 */
class TrekPoi {

    val longitude: Double             // 经度
    val latitude: Double              // 纬度
    val altitude: Double              // 海拔
    var logtime: Long? = null         // 记录时间
    var province: String? = null      // 省
    var city: String? = null          // 市
    var district: String? = null      // 区/县
    var name: String? = null          // 地名
    var description: String? = null   // 描述，提供给用户，作游记等

    constructor(longitude: Double, latitude: Double, altitude: Double){
        this.longitude = longitude
        this.latitude = latitude
        this.altitude = altitude
    }
}