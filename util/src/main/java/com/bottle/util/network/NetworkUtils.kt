package com.bottle.util.network

import android.content.Context
import android.net.ConnectivityManager

class NetworkUtils {

    companion object {

        fun isNetworkConnected(context: Context): Boolean{
            val service = context.getSystemService(Context.CONNECTIVITY_SERVICE)
            service?: return false
            if(service !is ConnectivityManager){
                return false
            }
            val mNetworkInfo = service.activeNetworkInfo
            return mNetworkInfo != null && mNetworkInfo.isConnected
        }

        fun getNetworkType(context: Context): NetworkType{
            val service = context.getSystemService(Context.CONNECTIVITY_SERVICE)
            service?:return NetworkType.NONE_NET
            if(service !is ConnectivityManager){
                return NetworkType.NONE_NET
            }
            val networkInfo = service.activeNetworkInfo
            networkInfo?:return NetworkType.NONE_NET
            val nType = networkInfo.type
            return when(nType){
                ConnectivityManager.TYPE_MOBILE -> NetworkType.MOBILE
                ConnectivityManager.TYPE_WIFI -> NetworkType.WIFI
                -1 -> NetworkType.NONE_NET
                else -> NetworkType.OTHER
            }
        }

    }

}