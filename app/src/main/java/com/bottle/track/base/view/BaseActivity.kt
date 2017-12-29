package com.bottle.track.base.view

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bottle.track.app.MyApplication
import com.bottle.util.network.NetworkType
import com.orhanobut.logger.Logger

open class BaseActivity: AppCompatActivity() {

    protected val TAG = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        MyApplication.app.onActivityCreate(this)
        Logger.d("onCreate")
    }

    override fun onResume(){
        super.onResume()
        MyApplication.app.onActivityResume(this)
        Logger.d("onResume")
    }

    override fun onPause(){
        super.onPause()
        MyApplication.app.onActivityPause(this)
        Logger.d("onPause")
    }

    override fun onDestroy(){
        super.onDestroy()
        MyApplication.app.onActivityDestroy(this)
        Logger.d("onDestroy")
    }

    open fun onNetworkConnected(networkType: NetworkType){
        Logger.d("onNetworkConnected")
    }

    open fun onNetworkDisconnected(){
        Logger.d("onNetworkDisconnected")
    }

    fun showTips(view: View, tips: String) {
        Snackbar.make(view, tips, Snackbar.LENGTH_SHORT).show()
    }


    fun showTips(tips: String) {
        Snackbar.make(findViewById(android.R.id.content), tips, Snackbar.LENGTH_SHORT).show()
    }

}