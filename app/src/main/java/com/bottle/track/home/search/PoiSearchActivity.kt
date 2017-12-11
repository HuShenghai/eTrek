package com.bottle.track.home.search

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
import com.bottle.track.BaseActivity
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import java.util.ArrayList


class PoiSearchActivity : BaseActivity(), View.OnClickListener, SearchView.OnQueryTextListener, PoiSearch.OnPoiSearchListener {

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
        searchView.setOnQueryTextListener(this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = PoiAdapter(this, pois)
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
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if(TextUtils.isEmpty(newText)){
            return false
        }
        searchPOIAsyn("010", newText!!, pageSize, 1)
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
        showTips(recyclerView,"size:" + pois.size)
    }

}
