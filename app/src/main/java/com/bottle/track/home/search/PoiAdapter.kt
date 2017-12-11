package com.bottle.track.home.search

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.amap.api.services.core.PoiItem
import com.bottle.track.R

/**
 * @ClassName PoiAdapter
 * @Author half_bottle
 * @Date 2017/12/11
 * @Description
 */
class PoiAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private val context: Context
    private val pois: List<PoiItem>
    private val layoutInflater: LayoutInflater

    constructor(context: Context, pois: ArrayList<PoiItem>){
        this.context = context
        this.pois = pois
        layoutInflater = LayoutInflater.from(context)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if(holder is ItemViewHolder){
            holder?.llContent?.tag = position
            holder?.imgMore?.tag = position
            val poi = pois[position]
            holder.initItem(poi)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        var view = layoutInflater.inflate(R.layout.item_poi, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pois.size
    }
}

class ItemViewHolder: RecyclerView.ViewHolder{

    val llContent: LinearLayout
    val imgMore: ImageView
    val imgPoiType: ImageView
    val tvPoiDescription: TextView

    constructor(view: View): super(view){
        llContent = view.findViewById(R.id.llContent)
        imgPoiType = view.findViewById(R.id.imgPoiType)
        tvPoiDescription = view.findViewById(R.id.tvPoiDescription)
        imgMore = view.findViewById(R.id.imgMore)
    }

    fun initItem(track: PoiItem){
        tvPoiDescription.text = track.adName + track.businessArea
    }
}