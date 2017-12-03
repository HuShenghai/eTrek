package com.bottle.track.map

import android.util.Log

import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode
import com.amap.api.location.AMapLocationListener
import com.bottle.track.BuildConfig
import com.bottle.track.MyApplication
import com.bottle.track.TrekEvent
import org.greenrobot.eventbus.EventBus

class TrekLocation(private val observer: IOnReceiveLocation) {

    private val TAG = this.javaClass.simpleName
    private var mLocationClient: AMapLocationClient? = null
    private var mLocationOption: AMapLocationClientOption? = null
    private val mLocationListener: AMapLocationListener

    init {
        mLocationListener = AMapLocationListener { amapLocation ->
            if (amapLocation == null) {
                return@AMapLocationListener
            }
            if (amapLocation.errorCode == 0) {
                if(BuildConfig.DEBUG) {
                    Log.d(TAG, amapLocation.toStr())
                }
                observer.onReceiveLocation(amapLocation)
                //                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                //                amapLocation.getLatitude();//获取纬度
                //                amapLocation.getLongitude();//获取经度
                //                amapLocation.getAccuracy();//获取精度信息
                //                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                //                Date date = new Date(amapLocation.getTime());
                //                df.format(date);//定位时间
                //                amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                //                amapLocation.getCountry();//国家信息
                //                amapLocation.getProvince();//省信息
                //                amapLocation.getCity();//城市信息
                //                amapLocation.getDistrict();//城区信息
                //                amapLocation.getStreet();//街道信息
                //                amapLocation.getStreetNum();//街道门牌号信息
                //                amapLocation.getCityCode();//城市编码
                //                amapLocation.getAdCode();//地区编码
            } else {
                Log.e(TAG, "location Error, ErrCode:"
                        + amapLocation.errorCode
                        + ", errInfo:" + amapLocation.errorInfo)
            }
        }
        setOption()
    }

    private fun setOption() {
        mLocationClient = AMapLocationClient(MyApplication.app)
        mLocationOption = AMapLocationClientOption()
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors 是仅设备模式
        mLocationOption?.locationMode = AMapLocationMode.Hight_Accuracy
        mLocationOption?.isNeedAddress = false   //设置是否返回地址信息（默认返回地址信息）
        mLocationOption?.isOnceLocation = false //设置是否只定位一次,默认为false
        mLocationOption?.isMockEnable = false   //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption?.interval = 5000        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationClient?.setLocationOption(mLocationOption)
        mLocationClient?.setLocationListener(mLocationListener)
    }

    fun start() {
        mLocationClient?.startLocation()
    }

    fun stop() {
        mLocationClient?.stopLocation()
    }

    interface IOnReceiveLocation {
        fun onReceiveLocation(arg0: com.amap.api.location.AMapLocation?)
    }

}






