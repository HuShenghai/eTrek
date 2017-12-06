package com.bottle.track.service

import android.util.Log
import android.widget.Toast
import com.amap.api.location.AMapLocation
import com.amap.api.maps.model.LatLng
import com.bottle.track.BuildConfig
import com.bottle.track.MyApplication
import com.bottle.track.ThreadExecutor
import com.bottle.track.TrekEvent
import com.bottle.track.api.Api
import com.bottle.track.api.BaseRequestBean
import com.bottle.track.api.convertToRequestBean
import com.bottle.track.api.request.UploadTrack
import com.bottle.track.db.convertToDbClass
import com.bottle.track.map.TrekLocation
import com.bottle.track.map.model.GeoPoint
import com.bottle.track.map.model.Track
import com.bottle.track.map.model.TrekTrack
import io.reactivex.schedulers.Schedulers
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
    private var geoPoints = ArrayList<GeoPoint>()

    fun startLocation() {
        trekLocation = TrekLocation(object : TrekLocation.IOnReceiveLocation {
            override fun onReceiveLocation(location: AMapLocation) {
                if(!BuildConfig.DEBUG) {
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
                EventBus.getDefault().post(TrekEvent(1, "定位", location))
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
        tracking = false
        val endTrackingTime = System.currentTimeMillis()
        Log.d(TAG, "停止轨迹记录，然后需要写入数据库等")
        val track = Track(geoPoints, beginTrackingTime, endTrackingTime)
        val tracks = ArrayList<Track>()
        tracks.add(track)
        val trekTrack = TrekTrack(tracks, beginTrackingTime, endTrackingTime, "这是一条轨迹")
        val trekTrackDao = MyApplication.app.daoSession?.trekTrackDao
        trekTrackDao?.insert(convertToDbClass(trekTrack))
        ThreadExecutor.defaultInstance().doTask(Runnable {
            Api.api.httpService.uploadTrekTrack(
                    BaseRequestBean(convertToRequestBean(MyApplication.app.androidId!!, trekTrack)))
                    .observeOn(Schedulers.io())
                    .doOnNext({
                        // 这里可以进行耗时操作，如读写数据库等
                        Log.d(TAG, "doOnNext")
                    })
                    .subscribe({ response ->
                        Log.d(TAG, "上传轨迹成功")

                    }, { error ->
                        Log.d(TAG, "上传轨迹失败：" + error.message)
                    })
        })
    }

    fun recordLocation(location: AMapLocation) {
        // 收到定位信息，在这里判断这个点是否需要加入到轨迹中 1.精度要求；2.与上一个点的时间与距离等
        Log.d(TAG, "收到定位信息")
        val position = LatLng(location.latitude, location.longitude)
        MyApplication.app.cache.track.add(position)
        geoPoints.add(GeoPoint(location.longitude, location.latitude, location.altitude))
    }

}