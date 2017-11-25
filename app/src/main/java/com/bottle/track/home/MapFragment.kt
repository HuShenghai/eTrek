package com.bottle.track.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.amap.api.maps.AMap
import com.amap.api.maps.MapView

import com.bottle.track.R
import org.greenrobot.eventbus.EventBus
import android.widget.Toast
import com.amap.api.location.AMapLocation
import com.bottle.track.TrackEvent
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MapFragment : Fragment() {

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
        val view: View = inflater!!.inflate(R.layout.fragment_dashboard, container, false)
        mapView = view.findViewById(R.id.mapView)
        mapView?.onCreate(savedInstanceState)
        amap = mapView?.map
        return view
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onReceiveLocation(event: TrackEvent<Any>) {
        if(event.event is AMapLocation) {
            val location: AMapLocation = event.event as AMapLocation
            Toast.makeText(context, location.toStr(), Toast.LENGTH_LONG).show()
        }
    }
}
