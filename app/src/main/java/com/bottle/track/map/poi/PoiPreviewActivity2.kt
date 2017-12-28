package com.bottle.track.map.poi

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.*
import com.amap.api.services.core.PoiItem
import com.bottle.track.R
import com.bottle.track.db.schema.TrekPoi
import kotlinx.android.synthetic.main.activity_track_preview.*
import kotlinx.android.synthetic.main.title_layout.*

class PoiPreviewActivity2 : AppCompatActivity(), View.OnClickListener {

    companion object {
        fun start(activity: Activity, poi: TrekPoi, requestCode: Int){
            val intent = Intent(activity, PoiPreviewActivity2::class.java)
            intent.putExtra("poi", poi)
            activity.startActivityForResult(intent, requestCode)
        }
    }

    private var amap: AMap? = null
    private var poi: TrekPoi? = null
    private var marker: Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poi_preview2)
        imgBack.setOnClickListener(this)
        tvTitle.text = getString(R.string.preview)
        mapView?.onCreate(savedInstanceState)
        amap = mapView?.map
        amap?.isTrafficEnabled = false
        amap?.isMyLocationEnabled = true
        val myLocationStyle = MyLocationStyle()
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER)
        myLocationStyle.interval(5000)
        amap?.myLocationStyle = myLocationStyle
        amap?.uiSettings?.isZoomControlsEnabled = true

        poi = intent.getSerializableExtra("poi") as TrekPoi?
        if(poi != null && amap != null){
            marker = addMark(poi!!, amap!!)
            amap?.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    LatLng(poi?.latitude!!, poi?.longitude!!), 19f))
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

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.imgBack -> {finish()}
        }
    }

    private fun addMark(poi: TrekPoi, amap: AMap): Marker?{
        val markerOption = MarkerOptions()
        markerOption.position(LatLng(poi.latitude, poi.longitude))
                .title(String.format(getString(R.string.format_coordinate), poi.latitude, poi.longitude))
                .snippet(poi.title )
                .draggable(false)//设置Marker可拖动
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(resources, R.mipmap.ic_place_black_36dp)))
        var marker = amap.addMarker(markerOption)
        return marker
    }
}
