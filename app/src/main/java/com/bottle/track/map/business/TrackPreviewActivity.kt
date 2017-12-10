package com.bottle.track.map.business

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MyLocationStyle
import com.bottle.track.BaseActivity
import com.bottle.track.R
import com.bottle.track.db.schema.TrekTrack
import com.bottle.track.map.MyOverlay
import kotlinx.android.synthetic.main.activity_track_preview.*
import kotlinx.android.synthetic.main.title_layout.*

class TrackPreviewActivity : BaseActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.imgBack -> { finish() }
        }
    }

    companion object {
        fun start(activity: Activity, track: TrekTrack){
            val intent = Intent(activity, TrackPreviewActivity::class.java)
             intent.putExtra("track", track)
            activity.startActivity(intent)
        }
    }

    var track: TrekTrack? = null
    private var amap: AMap? = null
    private var overlay: MyOverlay? = null      //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_preview)
        imgBack.setOnClickListener(this)
        tvTitle.text = getString(R.string.preview)
        track = intent.getSerializableExtra("track") as TrekTrack?
        mapView?.onCreate(savedInstanceState)
        amap = mapView?.map
        amap?.isTrafficEnabled = false
        amap?.isMyLocationEnabled = true
        val myLocationStyle = MyLocationStyle()
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER)
        myLocationStyle.interval(5000)
        amap?.myLocationStyle = myLocationStyle
        amap?.uiSettings?.isZoomControlsEnabled = true

        val latLngs = ArrayList<LatLng>()
        for(k in track!!.tracks.indices){
            val segment = track!!.tracks[k]
            for(i in segment.points.indices){
                latLngs.add(LatLng(segment.points[i].latitude, segment.points[i].longitude))
            }
        }
        amap?.setOnMapLoadedListener {
            overlay = MyOverlay(latLngs)
            overlay?.updateOverlay(amap!!)
            amap?.moveCamera(CameraUpdateFactory.newLatLngZoom(overlay!!.latLngs[0], 16f))
        }

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
        mapView?.onDestroy()
        super.onDestroy()
    }

}
