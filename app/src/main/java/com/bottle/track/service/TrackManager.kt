package com.bottle.track.service

import android.util.Log
import com.amap.api.location.AMapLocation
import com.amap.api.maps.model.LatLng
import com.bottle.track.MyApplication
import com.bottle.track.TrekEvent
import com.bottle.track.map.TrekLocation
import org.greenrobot.eventbus.EventBus

/**
 * @ClassName TrackManager
 * @Author half_bottle
 * @Date 2017/12/3
 * @Description 轨迹记录帮助类
 */
class TrackManager {

    private val TAG = TrackManager::class.java.simpleName
    private var trekLocation: TrekLocation? = null
    private var tracking: Boolean = false       // 是否正在记录轨迹

    fun startLocation(){
        trekLocation = TrekLocation(object : TrekLocation.IOnReceiveLocation {
            override fun onReceiveLocation(location: AMapLocation) {
                var accuracy = location?.accuracy
                if(accuracy == null){
                    accuracy = Float.MAX_VALUE
                }
//                if(20.0f < accuracy) {
//                    return
//                }
                if(tracking){
                    recordLocation(location) // 记录轨迹
                }
                EventBus.getDefault().post(TrekEvent(1, "定位", location))
            }
        })
        trekLocation?.start()
    }

    fun stopLocation(){
        trekLocation?.stop()
        Log.d(TAG, "停止定位服务")
    }

    fun startTracking(){
        tracking = true
        MyApplication.app.cache.track.clear()
        Log.d(TAG, "开始记录轨迹")
    }

    fun stopTracking(){
        tracking = false
        Log.d(TAG, "停止轨迹记录，然后需要写入数据库等")
    }

    fun recordLocation(location: AMapLocation){
        // 收到定位信息，在这里判断这个点是否需要加入到轨迹中 1.精度要求；2.与上一个点的时间与距离等
        Log.d(TAG, "收到定位信息")
        val position = LatLng(location.latitude, location.longitude)
        MyApplication.app.cache.track.add(position)
    }

}