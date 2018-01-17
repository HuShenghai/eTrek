package com.bottle.track.app.service

import android.util.Log
import com.amap.api.location.AMapLocation
import com.amap.api.maps.model.LatLng
import com.bottle.track.BuildConfig
import com.bottle.track.app.MyApplication
import com.bottle.track.base.utils.ThreadExecutor
import com.bottle.track.app.TrekEvent
import com.bottle.track.core.db.convertToDbClass
import com.bottle.track.core.db.convertToModelClass
import com.bottle.track.core.db.schema.TrackPoint
import com.bottle.track.map.TrekLocation
import com.bottle.track.core.model.GeoPoint
import com.bottle.track.core.model.Track
import com.bottle.track.core.model.TrackType
import com.bottle.track.core.model.TrekTrack
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
    private var trackType: TrackType? = TrackType.PEDESTRIANISM

    fun startLocation() {
        trekLocation = TrekLocation(object : TrekLocation.IOnReceiveLocation {
            override fun onReceiveLocation(location: AMapLocation) {
                EventBus.getDefault().post(TrekEvent(
                        TrekEvent.TYPE_RECEIVE_LOCATION, "接收到定位", location))
                if (tracking) {
                    recordLocation(location) // 记录轨迹
                }
            }
        })
        trekLocation?.start()
    }

    fun stopLocation() {
        trekLocation?.stop()
        Log.d(TAG, "停止定位服务")
    }

    fun startTracking(command: Command<Any>) {
        var type = command.data
        if (type is TrackType) {
            trackType = type
        } else {
            trackType = TrackType.FREE_STROKE // 默认自由行
        }
        beginTrackingTime = System.currentTimeMillis()
        tracking = true
        MyApplication.app.cache?.track?.clear()
        geoPoints.clear()
        val trackPoints = MyApplication.app.daoSession?.trackPointDao?.loadAll()
        if (trackPoints == null) {
            return
        }
        for(point in trackPoints){
            geoPoints.add(convertToModelClass(point))
        }
    }

    fun stopTracking() {
        tracking = false
        val trackPoints = MyApplication.app.daoSession?.trackPointDao?.loadAll()
        if (trackPoints == null) {
            return
        }
        var points = ArrayList<GeoPoint>()
        for(point in trackPoints){
            points.add(convertToModelClass(point))
        }
        if (points.size < 2) {
            return // 两个点才能算一条线，直接返回即可
        }
        val endTrackingTime = System.currentTimeMillis()
        val track = Track(points, beginTrackingTime, endTrackingTime)
        tracks.add(track)
        val title = dateFormat(Date()) + "-" + MyApplication.app.getString(trackType!!.getName())
        val trekTrack = TrekTrack(trackType!!.type, tracks, beginTrackingTime, endTrackingTime, title)
        ThreadExecutor.defaultInstance().doTask(
                Runnable {
                    val localTrack = convertToDbClass(trekTrack)
                    MyApplication.app.daoSession?.trackPointDao?.deleteAll()
                    MyApplication.app.daoSession?.trekTrackDao?.insert(localTrack)
                    EventBus.getDefault().post(TrekEvent(
                            TrekEvent.TYPE_RECORD_TRACK, "记录一条轨迹", localTrack))
                })
    }

    /**
     * 收到定位信息，在这里判断这个点是否需要加入到轨迹中 1.精度要求；2.与上一个点的时间与距离等
     */
    fun recordLocation(location: AMapLocation) {
        var accuracy = location?.accuracy
        if (accuracy == null) {
            accuracy = Float.MAX_VALUE
        }
        if(!BuildConfig.DEBUG) { // 如果是发布版本，则只使用GPS定位，并且精度要求50米以内
            if(location?.locationType != 1) return
            if (50.0f < accuracy)  return
        }
        val position = LatLng(location.latitude, location.longitude)
        MyApplication.app.cache?.track?.add(position)
        geoPoints.add(GeoPoint(location.longitude, location.latitude, location.altitude))
        // 计算，如 1km 记一个Track，将轨迹分为多段，然后计算每段的属性
        val trackPoint = TrackPoint(location.longitude, location.latitude, location.altitude, System.currentTimeMillis())
        MyApplication.app.daoSession?.trackPointDao?.insert(trackPoint)
    }

}