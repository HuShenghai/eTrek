package com.bottle.track.model

/**
 * @ClassName TrekPoi
 * @Author half_bottle
 * @Date 2017/12/2
 * @Description Trek 中的POI，继承自GeoPoint，即包含坐标位置信息，自定义其它属性，如描述，地名等
 */
class TrekPoi(longitude: Double, latitude: Double, altitude: Double) : GeoPoint(longitude, latitude, altitude){
    var logtime: Long? = null         // 记录时间
    var province: String? = null      // 省
    var city: String? = null          // 市
    var district: String? = null      // 区/县
    var name: String? = null          // 地名
    var description: String? = null   // 描述，提供给用户，作游记等
}