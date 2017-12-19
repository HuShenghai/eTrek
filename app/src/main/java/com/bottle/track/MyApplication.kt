package com.bottle.track

import android.app.Application
import android.os.StrictMode
import com.bottle.track.db.GreenDaoImp
import com.bottle.track.db.gen.DaoSession
import com.bottle.util.CrashExceptionHandler
import com.bottle.util.getAndroidId
import com.bottle.util.network.INetworkStateObserver
import com.bottle.util.network.NetworkStateReceiver
import com.bottle.util.network.NetworkType
import com.orhanobut.logger.Logger

import com.tencent.bugly.crashreport.CrashReport
import timber.log.Timber
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.DiskLogAdapter
import com.orhanobut.logger.CsvFormatStrategy
import com.squareup.leakcanary.LeakCanary

class MyApplication : Application(), INetworkStateObserver {

    var currentActivity: BaseActivity? = null
        private set
    var networkType: NetworkType? = NetworkType.NONE_NET
        private set
    var isNetworkConnected: Boolean = true
        private set
    var isTablet: Boolean = false     // pad(sw600->7英寸): true, phone: false
        private set
    var isForeground: Boolean = true  // APP是否正在前台运行
        private set
    var androidId: String? = null

    var daoSession: DaoSession? = null

    val cache: Cache = Cache()

    companion object {
        lateinit var app: MyApplication private set
    }

    override fun onCreate() {
        super.onCreate()
        initApp()
        initLog()
    }

    override fun onTerminate() {
        super.onTerminate()
        NetworkStateReceiver.getInstance().unRegisterNetworkStateReceiver(this)
    }

    private fun initApp() {
        app = this
        isTablet = resources.getBoolean(R.bool.isTablet)
        Thread.setDefaultUncaughtExceptionHandler(CrashExceptionHandler.getInstance(this))
        NetworkStateReceiver.getInstance().registerNetworkStateReceiver(this)
        NetworkStateReceiver.getInstance().registerObserver(this)
        if (BuildConfig.DEBUG) {
            StrictMode.enableDefaults()
        }
        LeakCanary.install(this)
        androidId = getAndroidId(this)
        daoSession = GreenDaoImp.getInstance(this, androidId ?: "local").daoSession
    }

    override fun onConnected(type: NetworkType) {
        networkType = type
        isNetworkConnected = true
        currentActivity?.onNetworkConnected(type)
    }

    override fun onDisconnected() {
        isNetworkConnected = false
        networkType = NetworkType.NONE_NET
        currentActivity?.onNetworkDisconnected()
    }

    private fun initLog() {
        // 初始化Bugly
        CrashReport.initCrashReport(applicationContext, getString(R.string.bugly_app_key), true)
        // Logger
        val formatStrategy = CsvFormatStrategy.newBuilder().tag("LoggerTag").build()
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return true // BuildConfig.DEBUG
            }
        })
        Logger.addLogAdapter(DiskLogAdapter(formatStrategy))
        // Timber日志
        Timber.plant(object : Timber.DebugTree() {
            override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
                Logger.log(priority, tag, message, t)
            }
        })
    }

    fun onActivityCreate(baseActivity: BaseActivity) {
        currentActivity = baseActivity
    }

    fun onActivityResume(baseActivity: BaseActivity) {
        currentActivity = baseActivity
        isForeground = true
    }

    fun onActivityPause(baseActivity: BaseActivity) {
        currentActivity = baseActivity
        isForeground = false
    }

    fun onActivityDestroy(baseActivity: BaseActivity) {
        // currentActivity = null
    }

}
