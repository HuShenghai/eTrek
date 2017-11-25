package com.bottle.util.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.util.Log
import java.lang.Exception

class NetworkStateReceiver private constructor(): BroadcastReceiver(){

    private val TAG: String = this.javaClass.simpleName
    private val observers: ArrayList<INetworkStateObserver> = arrayListOf()
    var networkType: NetworkType? = null
        private set
    var isNetworkConnected: Boolean = true
        private set

    override fun onReceive(p0: Context?, p1: Intent?) {
        p0?:return
        val action: String = p1?.action?:return
        if (action == ConnectivityManager.CONNECTIVITY_ACTION) {
            if (!NetworkUtils.isNetworkConnected(p0)) {
                isNetworkConnected = false
                networkType = NetworkType.NONE_NET
            } else {
                networkType = NetworkUtils.getNetworkType(p0)
                isNetworkConnected = true
            }
            notifyObserver()
        }
    }

    private fun notifyObserver() {
        var obs: INetworkStateObserver?
        for (i in this.observers.indices) {
            obs = this.observers[i]
            if (this.isNetworkConnected) {
                obs.onConnected(networkType!!)
            } else {
                obs.onDisconnected()
            }
        }
    }

    fun registerObserver(observer: INetworkStateObserver){
        observers.add(observer)
    }

    fun removeObserver(observer: INetworkStateObserver){
        observers.remove(observer)
    }

    fun registerNetworkStateReceiver(context: Context){
        val filter = IntentFilter()
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        context.applicationContext.registerReceiver(getInstance(), filter)
    }

    fun unRegisterNetworkStateReceiver(context: Context){
        try{
            context.applicationContext.unregisterReceiver(receiver)
        }catch (e: Exception){
            e.printStackTrace()
            Log.e(TAG, e.message)
        }
    }

    companion object {

        var receiver: NetworkStateReceiver? = null
        private set

        fun getInstance(): NetworkStateReceiver{
            if(receiver == null){
                receiver = NetworkStateReceiver()
            }
            return receiver!!
        }
    }
}