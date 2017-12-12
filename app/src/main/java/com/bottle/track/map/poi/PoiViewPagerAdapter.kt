package com.bottle.track.map.poi

import android.app.Activity
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.amap.api.services.core.PoiItem
import com.bottle.track.R

/**
 * @ClassName PoiViewPagerAdapter
 * @Author half_bottle
 * @Date 2017/12/12
 * @Description
 */
class PoiViewPagerAdapter: PagerAdapter {

    private val pois: List<PoiItem>
    private val activity: Activity
    private val inflate: LayoutInflater

    constructor(activity: Activity, pois: ArrayList<PoiItem>){
        this.pois = pois
        this.activity = activity
        this.inflate = LayoutInflater.from(activity)
    }

    override fun isViewFromObject(view: View?, obj: Any?): Boolean {
        return view?.equals(obj) ?: false
    }

    override fun getCount(): Int {
        return pois.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): View {
        val view = inflate.inflate(R.layout.adapter_poi, null)
        val tvPoiDescription = view.findViewById<TextView>(R.id.tvPoiDescription)
        val poi = pois[position]
        tvPoiDescription.text = poi.businessArea + poi.title + poi.adName
        return view
    }
    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }
}