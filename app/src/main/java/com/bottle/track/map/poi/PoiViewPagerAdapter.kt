package com.bottle.track.map.poi

import android.app.Activity
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.amap.api.services.core.PoiItem
import com.bottle.track.R

/**
 * @ClassName PoiViewPagerAdapter
 * @Author half_bottle
 * @Date 2017/12/12
 * @Description
 */
class PoiViewPagerAdapter: PagerAdapter, View.OnClickListener {

    private val pois: List<PoiItem>
    private val activity: Activity
    private val inflate: LayoutInflater
    var poiActionListener: PoiActionListener? = null

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
        val imgCollect = view.findViewById<ImageView>(R.id.imgCollect)
        imgCollect.tag = position
        imgCollect.setOnClickListener(this)
        val poi = pois[position]
        tvPoiDescription.text = poi.title + "\n" +  poi.snippet // poi.businessArea + poi.title
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as View)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.imgCollect -> {
                poiActionListener?.onCollection(pois[v.tag as Int])
            }
        }
    }

}

/**
 *
 */
interface PoiActionListener{
    fun onCollection(poi: PoiItem)  // 添加收藏
}
