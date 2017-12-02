package com.bottle.track.home

import android.os.Bundle
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.*
import android.widget.Toast
import com.amap.api.maps.AMap
import com.amap.api.maps.MapView

import com.bottle.track.R
import org.greenrobot.eventbus.EventBus
import com.amap.api.location.AMapLocation
import com.bottle.track.BaseFragment
import com.bottle.track.TrekEvent
import kotlinx.android.synthetic.main.fragment_map.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.CameraUpdateFactory
import com.bottle.track.api.Api
import com.bottle.track.api.BaseRequestBean
import com.bottle.track.api.request.UploadPoi

import com.bottle.track.lbs.MyOverlay
import com.bottle.track.model.TrekPoi
import com.bottle.util.toJsonString
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MapFragment : BaseFragment(), SearchView.OnCloseListener, View.OnClickListener, SearchView.OnQueryTextListener {

    private var mParam1: String? = null
    private var mParam2: String? = null

    private var mapView: MapView? = null
    private var amap: AMap? = null

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
        searchView.setOnCloseListener(this)
        searchView.setOnSearchClickListener(this)
        searchView.setOnQueryTextListener(this)
        imgMyLocation.setOnClickListener(this)
        imgLayers.setOnClickListener(this)
        imgTrack.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
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
        // amap?.uiSettings?.isZoomControlsEnabled = false
        overlay = MyOverlay(amap!!)
        return view
    }

    override fun fetchData() {

    }

    ////////////// SearchView //////////////////////
    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.imgMyLocation ->{
                testUploadPoints()
            }
        }
    }

    override fun onClose(): Boolean {
        return false
    }

    private var isFirstLocation: Boolean = true // 第一次定位
    private var isFollow: Boolean = false       // 地图显示位置是否跟随定位，默认 false
    private var overlay: MyOverlay? = null

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onReceiveLocation(event: TrekEvent<Any>) {
        if (event.event is AMapLocation) {
            val location: AMapLocation = event.event as AMapLocation
            val position = LatLng(location.latitude, location.longitude)
            overlay?.latLngs?.add(position)
            if(overlay?.latLngs!!.size > 1){
                overlay?.updateOverlay()
            }
            if (isFirstLocation) {
                isFirstLocation = false// 只在第一次定位时显示到定位中心
                amap?.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 19f))
            } else if(isFollow){
                amap?.moveCamera(CameraUpdateFactory.changeLatLng(position))
            }
        }
    }

    private fun testUploadPoints(){
        val logtime = System.currentTimeMillis()
        val p1 = TrekPoi(112.1, 23.0, 500.0)
        val p2 = TrekPoi(112.1, 23.0, 500.0)
        val p3 = TrekPoi(112.1, 23.0, 500.0)
        p1.logtime = logtime
        p2.logtime = logtime
        p3.logtime = logtime
        val points = listOf(p1, p2, p3)
        val uploadPoints = UploadPoi(points)
        val requestBean = BaseRequestBean(uploadPoints)
        Api.api.httpService.uploadPoints(requestBean).subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .doOnNext({
                    // 这里可以进行耗时操作，如读写数据库等
                    Log.d(TAG, "doOnNext")
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    Toast.makeText(context, response.info, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, toJsonString(response as Object))

                }, { error ->
                    Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, toJsonString(error as Object))
                })
    }
}
