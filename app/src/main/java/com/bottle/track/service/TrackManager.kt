package com.bottle.track.service

import android.util.Log
import com.amap.api.location.AMapLocation
import com.amap.api.maps.model.LatLng
import com.bottle.track.BuildConfig
import com.bottle.track.MyApplication
import com.bottle.track.ThreadExecutor
import com.bottle.track.TrekEvent
import com.bottle.track.db.convertToDbClass
import com.bottle.track.map.TrekLocation
import com.bottle.track.map.model.GeoPoint
import com.bottle.track.map.model.Track
import com.bottle.track.map.model.TrekTrack
import com.bottle.util.dateFormat
import org.greenrobot.eventbus.EventBus
import java.util.*

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
    private var beginTrackingTime: Long = 0
    private var geoPoints = ArrayList<GeoPoint>()           // 每段的 Polyline
    private val tracks = ArrayList<Track>()                 // 多段 Track

    fun startLocation() {
        trekLocation = TrekLocation(object : TrekLocation.IOnReceiveLocation {
            override fun onReceiveLocation(location: AMapLocation) {
                if (!BuildConfig.DEBUG) {
                    var accuracy = location?.accuracy
                    if (accuracy == null) {
                        accuracy = Float.MAX_VALUE
                    }
                    if (20.0f < accuracy) {
                        return
                    }
                }
                if (tracking) {
                    recordLocation(location) // 记录轨迹
                }
                EventBus.getDefault().post(TrekEvent(
                        TrekEvent.TYPE_RECEIVE_LOCATION, "接收到定位", location))
            }
        })
        trekLocation?.start()
    }

    fun stopLocation() {
        trekLocation?.stop()
        Log.d(TAG, "停止定位服务")
    }

    fun startTracking() {
        beginTrackingTime = System.currentTimeMillis()
        tracking = true
        MyApplication.app.cache.track.clear()
        geoPoints.clear()
        Log.d(TAG, "开始记录轨迹")
    }

    fun stopTracking() {
        Log.d(TAG, "停止轨迹记录，然后需要写入数据库等")
        tracking = false
        val endTrackingTime = System.currentTimeMillis()
        val track = Track(geoPoints, beginTrackingTime, endTrackingTime)
        tracks.add(track)
        val description = dateFormat(Date()) + " 徒步"
        val trekTrack = TrekTrack(tracks, beginTrackingTime, endTrackingTime, description)
        val trekTrackDao = MyApplication.app.daoSession?.trekTrackDao
        ThreadExecutor.defaultInstance().doTask(
                Runnable {
                    val localTrack = convertToDbClass(trekTrack)
                    trekTrackDao?.insert(localTrack) // 插入数据库
                    EventBus.getDefault().post(TrekEvent( // 发送到界面，然后提示是否上传
                            TrekEvent.TYPE_RECORD_TRACK, "记录一条轨迹", localTrack))
                })
    }

    fun recordLocation(location: AMapLocation) {
        // 收到定位信息，在这里判断这个点是否需要加入到轨迹中 1.精度要求；2.与上一个点的时间与距离等
        Log.d(TAG, "收到定位信息")
        val position = LatLng(location.latitude, location.longitude)
        MyApplication.app.cache.track.add(position)
        geoPoints.add(GeoPoint(location.longitude, location.latitude, location.altitude))
        // 计算，如 1km 记一个Track，将轨迹分为多段，然后计算每段的属性
    }

}