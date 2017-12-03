package com.bottle.track.api.request

import com.bottle.track.map.model.GeoPoint
import com.bottle.track.map.model.TrekPoi

/**
 * @ClassName UploadPoi
 * @Author half_bottle
 * @Date 2017/12/2
 * @Description 上传POI的请求参数
 */
class UploadPoi {

    val points: List<TrekPoi> // 要上传的点集合

    constructor(pois: List<TrekPoi>){
        this.points = pois
    }
}