package com.bottle.track.map.poi

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.amap.api.maps.AMap
import com.amap.api.maps.model.MyLocationStyle
import com.amap.api.services.core.PoiItem
import com.bottle.track.R
import com.bottle.track.db.schema.TrekTrack
import kotlinx.android.synthetic.main.activity_track_preview.*
import kotlinx.android.synthetic.main.title_layout.*

class PoiPreviewActivity : AppCompatActivity(), View.OnClickListener {

    private var amap: AMap? = null

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.imgBack -> { finish() }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poi_preview)
        initView()
        initMap(savedInstanceState)
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
    private fun initMap(savedInstanceState: Bundle?) {
        mapView?.onCreate(savedInstanceState)
        amap = mapView?.map
        amap?.isTrafficEnabled = false
        amap?.isMyLocationEnabled = true
        val myLocationStyle = MyLocationStyle()
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER)
        myLocationStyle.interval(5000)
        amap?.myLocationStyle = myLocationStyle
        amap?.uiSettings?.isZoomControlsEnabled = true
    }

    private fun initView(){
        imgBack.setOnClickListener(this)
        tvTitle.text = getString(R.string.preview)
    }

    companion object {

        fun start(activity: Activity, poiItem: PoiItem){
            val intent = Intent(activity, PoiPreviewActivity::class.java)
            intent.putExtra("track", poiItem)
            activity.startActivity(intent)
        }

    }
}
