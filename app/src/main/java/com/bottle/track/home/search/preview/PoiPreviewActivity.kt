package com.bottle.track.home.search.preview

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
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.*
import com.bottle.track.base.view.BaseActivity
import com.bottle.track.app.MyApplication
import com.bottle.track.core.db.schema.TrekPoi
import com.bottle.util.dip2px
import kotlinx.android.synthetic.main.activity_poi_preview.*

class PoiPreviewActivity : BaseActivity(),
        View.OnClickListener,
        ViewPager.OnPageChangeListener,
        PoiActionListener,
        AMap.OnMarkerClickListener {

    override fun onCollection(poi: PoiItem) {
        val trekPoi = TrekPoi()
        trekPoi.longitude = poi.latLonPoint.longitude
        trekPoi.latitude = poi.latLonPoint.latitude
        trekPoi.altitude = 0.0
        trekPoi.province = poi.provinceName
        trekPoi.city = poi.cityName
        trekPoi.district = poi.businessArea
        trekPoi.name = poi.adName
        trekPoi.description = poi.snippet
        trekPoi.logtime = System.currentTimeMillis()
        trekPoi.tags = ""
        trekPoi.title = poi.title
        MyApplication.app.daoSession?.trekPoiDao?.insert(trekPoi)
        showTips(getString(R.string.add_poi))
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        val poiItem = MyApplication.app.cache.poiSearchResult[position]
        amap?.moveCamera(CameraUpdateFactory.newLatLngZoom(
                LatLng(poiItem.latLonPoint.latitude, poiItem.latLonPoint.longitude),
                amap?.cameraPosition?.zoom ?: 19.0f))
        currentMarker?.hideInfoWindow()
        for(marker in marks){
            if(poiItem.latLonPoint.latitude == marker?.position?.latitude
                    && poiItem.latLonPoint.longitude == marker?.position?.longitude){
                currentMarker = marker
                currentMarker?.showInfoWindow()
                break
            }
        }
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
        amap?.uiSettings?.isZoomControlsEnabled = false
        amap?.setOnMapLoadedListener {
            val poiItem = intent.getParcelableExtra<PoiItem>("poi")
            amap?.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    LatLng(poiItem.latLonPoint.latitude, poiItem.latLonPoint.longitude), 19f))
            for(poi in MyApplication.app.cache.poiSearchResult){
                addMark(poi, amap)
            }
            initViewPager()
        }
        amap?.setOnMarkerClickListener(this)
    }

    override fun onMarkerClick(p0: Marker?): Boolean {
        currentMarker = p0
        var i = 0
        for(poi in MyApplication.app.cache.poiSearchResult){
            if(poi.latLonPoint.latitude == p0?.position?.latitude
                    && poi.latLonPoint.longitude == p0?.position?.longitude){
                viewPager.currentItem = i
                break
            }
            i++
        }
        p0?.showInfoWindow()
        return false
    }

    private fun initView(){
        imgBack.setOnClickListener(this)
        tvTitle.text = getString(R.string.preview)
    }

    private val marks: java.util.ArrayList<Marker> = ArrayList()
    private var currentMarker: Marker? = null

    private fun addMark(poi: PoiItem, amap: AMap?): Marker?{
        val markerOption = MarkerOptions()
        markerOption.position(LatLng(poi.latLonPoint.latitude, poi.latLonPoint.longitude))
                .title(String.format(getString(R.string.format_coordinate), poi.latLonPoint.latitude, poi.latLonPoint.longitude))
                .snippet(poi.title + "\n" + poi.snippet)
                .draggable(false)//设置Marker可拖动
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(resources, R.mipmap.ic_place_black_36dp)))
        var marker = amap?.addMarker(markerOption)
        if(marker != null) marks.add(marker)
        return marker
    }

    private fun initViewPager() {
        viewPager.setOnPageChangeListener(this)
        viewPager.offscreenPageLimit = 2
        viewPager.pageMargin = dip2px(this, 16f)
        adapter = PoiViewPagerAdapter(this, MyApplication.app.cache.poiSearchResult)
        adapter?.poiActionListener = this
        viewPager.adapter = adapter
        viewPager.currentItem = intent.getIntExtra("position", 0)
        adapter?.notifyDataSetChanged()
    }
    companion object {

        fun start(activity: Activity, poiItem: PoiItem, position: Int){
            val intent = Intent(activity, PoiPreviewActivity::class.java)
            intent.putExtra("poi", poiItem)
            intent.putExtra("position", position)
            activity.startActivity(intent)
        }

    }
}
