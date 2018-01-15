package com.bottle.track.home.search.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.text.TextUtils
import android.view.View
import com.amap.api.services.core.PoiItem
import com.amap.api.services.poisearch.PoiResult
import com.bottle.track.R
import kotlinx.android.synthetic.main.activity_poi_search.*
import com.amap.api.services.poisearch.PoiSearch
import com.bottle.track.base.view.BaseActivity
import com.bottle.track.app.MyApplication
import com.bottle.track.base.view.OnRecyclerViewItemClickListener
import com.bottle.track.home.search.preview.PoiPreviewActivity
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import java.util.ArrayList


class PoiSearchActivity : BaseActivity(),
        View.OnClickListener,
        SearchView.OnQueryTextListener,
        PoiSearch.OnPoiSearchListener,
        OnRecyclerViewItemClickListener {

    companion object {
        fun start(activity: Activity, requestCode: Int){
            val intent = Intent(activity, PoiSearchActivity::class.java)
            activity.startActivityForResult(intent, requestCode)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poi_search)
        initView()
    }

    var adapter: PoiAdapter? = null
    var pois: ArrayList<PoiItem> = ArrayList()

    private fun initView(){
        imgBack.setOnClickListener(this)
        tvSearch.setOnClickListener(this)
        llMyLocation.setOnClickListener(this)
        llSelectFromMap.setOnClickListener(this)
        searchView.setOnQueryTextListener(this)
        searchView.isSubmitButtonEnabled = true
        searchView.requestFocusFromTouch()
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PoiAdapter(this, pois)
        adapter?.onItemClickListener = this
        var animaAdatper = ScaleInAnimationAdapter(adapter)
        animaAdatper.setDuration(700)
        animaAdatper.setFirstOnly(false)
        recyclerView.adapter = animaAdatper
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.imgBack -> { finish() }
            R.id.tvSearch -> {

            }
            R.id.llMyLocation -> {
                showTips("我的位置，需要反地理编码")
            }
            R.id.llSelectFromMap -> {
                showTips("地图选点，需要定位当前位置，然后移动地图选择")
            }
        }
    }

    override fun onItemClick(view: View?, position: Int) {
        PoiPreviewActivity.start(this, pois[position], position)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(TextUtils.isEmpty(query)){
            pois.clear()
            adapter?.notifyDataSetChanged()
            return false
        }
        searchPOIAsyn(MyApplication.app.cache?.cityCode ?: "010", query!!, pageSize, 1)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(TextUtils.isEmpty(newText)){
            pois.clear()
            adapter?.notifyDataSetChanged()
            return false
        }
        searchPOIAsyn(MyApplication.app.cache?.cityCode ?: "010", newText!!, pageSize, 1)
        return false
    }

    private val pageSize = 35

    private fun searchPOIAsyn(cityCode: String, keyWord: String, pageSize: Int, pageNumer: Int){
        val query = PoiSearch.Query(keyWord, "", cityCode)
        query.pageSize = pageSize
        query.pageNum = pageNumer
        val poiSearch = PoiSearch(this, query)
        poiSearch.setOnPoiSearchListener(this)
        poiSearch.searchPOIAsyn()
    }

    override fun onPoiItemSearched(p0: PoiItem?, p1: Int) {

    }

    override fun onPoiSearched(p0: PoiResult?, p1: Int) {
        if(p0 == null || 1000 != p1){
            return
        }
        val pois = p0.pois
        this.pois.clear()
        this.pois.addAll(pois)
        adapter?.notifyDataSetChanged()
        MyApplication.app.cache?.poiSearchResult?.clear()
        MyApplication.app.cache?.poiSearchResult?.addAll(pois)
    }

}
