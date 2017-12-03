package com.bottle.track

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.util.Log
import com.bottle.track.map.AMapLocation

/**
 * 一个应用只有一个Service
 */
class TrekService : Service() {

    private val TAG = TrekService::class.java.simpleName
    private var handler: Handler? = null
    private var aMapLocation: AMapLocation? = null

    inner class MyBinder : Binder() {

        val service: TrekService
            get() = this@TrekService
    }

    override fun onCreate() {
        super.onCreate()
        aMapLocation = AMapLocation(object : AMapLocation.IOnReceiveLocation {
            override fun onReceiveLocation(arg0: com.amap.api.location.AMapLocation?) {
                if (handler != null) {
                    val msg = handler!!.obtainMessage()
                    msg.what = MSG_RECEIVER_LOCATION
                    msg.obj = arg0
                    handler!!.sendMessage(msg)
                }
            }
        })
        aMapLocation?.start()
        Log.d(TAG, "onCreate")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand")
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        aMapLocation!!.stop()
        super.onDestroy()
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
    }

    override fun onBind(intent: Intent): IBinder? {
        return MyBinder()
    }

    override fun onUnbind(intent: Intent): Boolean {
        return super.onUnbind(intent)
    }

    fun setHandler(handler: Handler) {
        this.handler = handler
    }

    companion object {
        val MSG_RECEIVER_LOCATION = 0x1
    }
}
