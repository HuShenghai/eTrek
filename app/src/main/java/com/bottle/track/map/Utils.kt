package com.bottle.track.map

import android.content.Context
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.geocoder.GeocodeSearch
import com.amap.api.services.geocoder.RegeocodeQuery
import com.amap.api.services.geocoder.GeocodeQuery





/**
 * @ClassName Utils
 * @Author half_bottle
 * @Date 2017/12/27
 * @Description
 */

/**
 * 地理编码
 * @param context
 * @param listener
 * @param name 地址
 * @param cityCode 查询城市，中文或者中文全拼，citycode、adcode
 */
fun geoCode(context: Context, listener: GeocodeSearch.OnGeocodeSearchListener,
            name: String, cityCode: String){
    val geocoderSearch = GeocodeSearch(context)
    geocoderSearch.setOnGeocodeSearchListener(listener)
    val query = GeocodeQuery(name, cityCode)
    geocoderSearch.getFromLocationNameAsyn(query)
}

/**
 * 地理反编码
 * @param context
 * @param listener
 * @param latLonPoint
 * @param type 坐标系 火系坐标系还是GPS原生坐标系 GeocodeSearch.AMAP or GeocodeSearch.GPS
 */
fun regeocode(context: Context, listener: GeocodeSearch.OnGeocodeSearchListener,
              radius: Float, latLonPoint: LatLonPoint, type: String){
    val geocoderSearch = GeocodeSearch(context)
    geocoderSearch.setOnGeocodeSearchListener(listener)
    val query = RegeocodeQuery(latLonPoint, radius, type)
    geocoderSearch.getFromLocationAsyn(query)
}