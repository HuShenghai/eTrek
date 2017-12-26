package com.bottle.track.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.amap.api.location.AMapLocation
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.MapView
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MyLocationStyle
import com.bottle.track.*
import com.bottle.track.api.Api
import com.bottle.track.api.BaseRequestBean
import com.bottle.track.api.request.UploadPoi
import com.bottle.track.db.gen.TrekPoiDao
import com.bottle.track.db.schema.TrekTrack
import com.bottle.track.map.poi.PoiSearchActivity
import com.bottle.track.map.MyOverlay
import com.bottle.track.map.track.TrackEditorActivity
import com.bottle.track.map.model.TrackType
import com.bottle.track.map.model.TrekPoi
import com.bottle.track.service.Command
import com.bottle.track.service.TrekService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_map.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MapFragment : BaseFragment(),  View.OnClickListener {

    private var mParam1: String? = null
    private var mParam2: String? = null

    private var mapView: MapView? = null
    private var amap: AMap? = null
    private var center: LatLng? = null

    companion object {

        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        fun newInstance(param1: String, param2: String): MapFragment {
            val fragment = MapFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
        }
        EventBus.getDefault().register(this)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imgMyLocation.setOnClickListener(this)
        imgLayers.setOnClickListener(this)
        imgTrack.setOnClickListener(this)
        imgAddLocation.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
        foreground = true
    }

    override fun onPause() {
        foreground = false
        super.onPause()
        mapView?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_map, container, false)
        mapView = view.findViewById(R.id.mapView)
        mapView?.onCreate(savedInstanceState)
        amap = mapView?.map
        amap?.isTrafficEnabled = false
        amap?.isMyLocationEnabled = true
        val myLocationStyle = MyLocationStyle()
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER)
        myLocationStyle.interval(5000)
        amap?.myLocationStyle = myLocationStyle
        amap?.uiSettings?.isZoomControlsEnabled = false
        overlay = MyOverlay(MyApplication.app.cache.track)
        return view
    }

    override fun fetchData() {

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.imgAddLocation -> { PoiSearchActivity.start(activity, 100)}
            R.id.imgMyLocation -> {
                if (center != null)
                    amap?.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 19f))
            }
            R.id.imgLayers -> {
                // testPoiDatabase()
            }
            R.id.imgTrack -> {
                if (tracking) {
                    tracking = false
                    TrekService.start(Command(Command.STOP_TRACKING, "停止记录轨迹", ""))
                    showToast("停止记录轨迹")
                    overlay?.remove(amap!!)
                } else {
                    showToast("开始记录轨迹")
                    TrekService.start(Command(Command.START_TRACKING, "开始记录轨迹", TrackType.PEDESTRIANISM))
                    tracking = true
                }
            }
        }
    }

    private var isFirstLocation: Boolean = true // 第一次定位
    private var isFollow: Boolean = false       // 地图显示位置是否跟随定位，默认 false
    private var overlay: MyOverlay? = null      //
    private var tracking: Boolean = false       // 是否正在记录轨迹
    private var foreground: Boolean = true      // Fragment是否前台运行

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onReceiveEvent(event: TrekEvent<Any>) {
        when (event.type) {
            TrekEvent.TYPE_RECEIVE_LOCATION -> updateTrackOverlay(event)
            TrekEvent.TYPE_RECORD_TRACK -> {
                TrackEditorActivity.start(activity, event.event as TrekTrack, 0)
            }
        }
    }

    private fun updateTrackOverlay(event: TrekEvent<Any>) {
        if (event.event is AMapLocation) {
            val location: AMapLocation = event.event as AMapLocation
            val position = LatLng(location.latitude, location.longitude)
            center = position
            if (tracking) {
                if (overlay?.latLngs!!.size > 1) {
                    overlay?.updateOverlay(amap!!)
                }
            }
            if (!foreground) {
                return
            }
            if (isFirstLocation) {
                isFirstLocation = false // 只在第一次定位时显示到定位中心
                amap?.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 19f))
            } else if (isFollow) {
                amap?.moveCamera(CameraUpdateFactory.changeLatLng(position))
            }
        }
    }

}
