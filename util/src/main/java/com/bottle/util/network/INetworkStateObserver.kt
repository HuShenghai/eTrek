package com.bottle.util.network

interface INetworkStateObserver {

    fun onConnected(type: NetworkType)

    fun onDisconnected()
}