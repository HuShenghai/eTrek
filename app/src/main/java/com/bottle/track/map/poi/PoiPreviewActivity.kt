package com.bottle.track.map.poi

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.amap.api.maps.AMap
import com.amap.api.services.core.PoiItem
import com.bottle.track.R
import kotlinx.android.synthetic.main.title_layout.*
import android.graphics.BitmapFactory
import android.support.v4.view.ViewPager
import android.util.Log
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.*
import com.bottle.track.BaseActivity
import com.bottle.track.MyApplication
import com.bottle.util.dip2px
import kotlinx.android.synthetic.main.activity_poi_preview.*

class PoiPreviewActivity : BaseActivity(), View.OnClickListener, ViewPager.OnPageChangeListener {

    override fun onPageScrollStateChanged(state: Int) {
        Log.d("tag", "on")
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        Log.d("tag", "on")
    }

    override fun onPageSelected(position: Int) {
        Log.d("tag", "on")
    }

    private var amap: AMap? = null
    private var adapter: PoiViewPagerAdapter? = null

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
        initViewPager()
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
        amap?.setOnMapLoadedListener {
            val poiItem = intent.getParcelableExtra<PoiItem>("poi")
            addMark(poiItem, amap)
            amap?.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    LatLng(poiItem.latLonPoint.latitude, poiItem.latLonPoint.longitude), 19f))
        }
    }

    private fun initView(){
        imgBack.setOnClickListener(this)
        tvTitle.text = getString(R.string.preview)
    }

    private fun addMark(poi: PoiItem, amap: AMap?): Marker?{
        val markerOption = MarkerOptions()
        markerOption.position(LatLng(poi.latLonPoint.latitude, poi.latLonPoint.longitude))
        markerOption.title(poi.title).snippet(poi.snippet)

        markerOption.draggable(true)//设置Marker可拖动
        markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                .decodeResource(resources, R.mipmap.ic_place_black_36dp)))
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        // markerOption.isFlat = true//设置marker平贴地图效果
        return amap?.addMarker(markerOption)
    }

    private fun initViewPager() {
        viewPager.setOnPageChangeListener(this)
        viewPager.offscreenPageLimit = 2
        viewPager.pageMargin = dip2px(this, 8f)
        adapter = PoiViewPagerAdapter(this,  MyApplication.app.cache.poiSearchResult)
        viewPager.adapter = adapter
        adapter?.notifyDataSetChanged()
    }
    companion object {

        fun start(activity: Activity, poiItem: PoiItem){
            val intent = Intent(activity, PoiPreviewActivity::class.java)
            intent.putExtra("poi", poiItem)
            activity.startActivity(intent)
        }

    }
}