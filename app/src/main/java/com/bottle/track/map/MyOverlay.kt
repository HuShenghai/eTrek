package com.bottle.track.map

import android.graphics.Color
import com.amap.api.maps.AMap
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.Poi
import com.amap.api.maps.model.Polyline
import com.amap.api.maps.model.PolylineOptions

/**
 * @Date 2017/11/27 10:41
 * @Author halfbottle
 * @Version 1.0.0
 * @Description
 */
class MyOverlay{

    val poi: Poi? = null
    private val amap: AMap
    val latLngs: ArrayList<LatLng>
    var polyline: Polyline? = null

    constructor(amap: AMap, latLngs: ArrayList<LatLng>){
        this.amap = amap
        this.latLngs = latLngs
    }

    fun updateOverlay(){
        polyline?.remove()
        drawPolyLine(amap, latLngs)
    }

    fun drawPolyLine(aMap: AMap, latLngs: ArrayList<LatLng>): Polyline? {
        polyline = aMap.addPolyline(PolylineOptions()
                .addAll(latLngs)
                .width(10f)
                .color(Color.argb(255, 1, 1, 1)))
        return polyline
    }
}