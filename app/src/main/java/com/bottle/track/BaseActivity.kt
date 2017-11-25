package com.bottle.track

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.bottle.util.network.NetworkType
import com.orhanobut.logger.Logger

open class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        MyApplication.app.onActivityCreate(this)
    }

    override fun onResume(){
        super.onResume()
        MyApplication.app.onActivityResume(this)
    }

    override fun onPause(){
        super.onPause()
        MyApplication.app.onActivityPause(this)
    }

    override fun onDestroy(){
        super.onDestroy()
        MyApplication.app.onActivityDestroy(this)
    }

    open fun onNetworkConnected(networkType: NetworkType){
        Logger.d("onConnected")
    }

    open fun onNetworkDisconnected(){
        Logger.d("onDisconnected")
    }

    fun showTips(view: View, tips: String) {
        Snackbar.make(view, tips, Snackbar.LENGTH_SHORT).show()
    }

}