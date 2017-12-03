package com.bottle.track.map.model

/**
 * @ClassName Polyline
 * @Author half_bottle
 * @Date 2017/12/2
 * @Description Trek几何模型中的线，一条线有有序的坐标点相连组成
 */
open class Polyline {

    var startPoint: GeoPoint? = null // 起点
    var endPoint: GeoPoint? = null   // 终点
    var distance: Double? = null     // 总长度

    val points: List<GeoPoint>

    constructor(points: List<GeoPoint>){
        this.points = points
    }

}