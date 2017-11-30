package com.bottle.track.home

import android.os.Bundle
import android.support.v7.widget.SearchView
import android.view.*
import com.amap.api.maps.AMap
import com.amap.api.maps.MapView

import com.bottle.track.R
import org.greenrobot.eventbus.EventBus
import com.amap.api.location.AMapLocation
import com.bottle.track.BaseFragment
import com.bottle.track.TrackEvent
import kotlinx.android.synthetic.main.fragment_map.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.CameraUpdateFactory
import com.bottle.track.lbs.MyOverlay


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
        amap?.isTrafficEnabled = true
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

    }

    override fun onClose(): Boolean {
        return false
    }

    private var isFirstLocation: Boolean = true // 第一次定位
    private var isFollow: Boolean = false       // 地图显示位置是否跟随定位，默认false
    private var overlay: MyOverlay? = null

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onReceiveLocation(event: TrackEvent<Any>) {
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
}
