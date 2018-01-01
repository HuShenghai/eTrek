package com.bottle.track.app

import com.amap.api.maps.model.LatLng
import com.amap.api.services.core.PoiItem
import com.bottle.track.core.db.schema.TrekUser

/**
 * @Date 2017/12/6 14:08
 * @Author halfbottle
 * @Version 1.0.0
 * @Description
 */
class Cache {

    val track: ArrayList<LatLng> = arrayListOf()
    var trekUser: TrekUser? = null
    val poiSearchResult = java.util.ArrayList<PoiItem>()
    var cityCode: String? = null
    var cityName: String? = null


}