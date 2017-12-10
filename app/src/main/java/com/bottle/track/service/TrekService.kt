package com.bottle.track.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.util.Log
import com.bottle.track.MyApplication

/**
 * @ClassName TrekService
 * @Author half_bottle
 * @Date 2017/12/3
 * @Description eTrek 应用的唯一一个后台服务，目前用于后台定位
 */
class TrekService : Service() {

    private val TAG = TrekService::class.java.simpleName
    private var trackManager: TrackManager = TrackManager()

    inner class MyBinder : Binder() {

        val service: TrekService
            get() = this@TrekService
    }

    override fun onCreate() {
        super.onCreate()

    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand")
        if(intent == null) return super.onStartCommand(intent, flags, startId)
        var command: Command<Any> = intent.getSerializableExtra(COMMAND) as Command<Any>
        if(command != null){
            when(command.command){
                Command.START_LOCATION -> {trackManager.startLocation()}
                Command.STOP_LOCATION -> {trackManager.stopLocation()}
                Command.START_TRACKING ->{trackManager.startTracking(command)}
                Command.STOP_TRACKING ->{trackManager.stopTracking()}
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        trackManager.stopLocation()
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

    companion object {

        val COMMAND = "command"

        fun start(command: Command<Any>){
            val locationIntent = Intent(MyApplication.app, TrekService::class.java)
            locationIntent.putExtra(COMMAND, command)
            MyApplication.app.startService(locationIntent)
        }
    }

}
