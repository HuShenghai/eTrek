package com.bottle.track.api.request

import com.bottle.track.model.Poi

/**
 * @ClassName UploadPoi
 * @Author half_bottle
 * @Date 2017/12/2
 * @Description 上传POI的请求参数
 */
class UploadPoi {

    val points: List<Poi> // 要上传的点集合

    constructor(pois: List<Poi>){
        this.points = pois
    }
}