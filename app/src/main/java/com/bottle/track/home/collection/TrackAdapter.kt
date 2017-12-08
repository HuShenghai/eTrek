package com.bottle.track.home.collection

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.bottle.track.db.schema.TrekTrack
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import com.bottle.track.R

/**
 * @Date 2017/12/8 16:45
 * @Author halfbottle
 * @Version 1.0.0
 * @Description TrekTrack 适配器
 */
class TrackAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private val context: Context
    private val tracks: List<TrekTrack>
    private val layoutInflater: LayoutInflater

    constructor(context: Context, tracks: List<TrekTrack>){
        this.context = context
        this.tracks = tracks
        layoutInflater = LayoutInflater.from(context)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if(holder is ItemViewHolder){
            val track = tracks[position]
            holder.initItem(track)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return ItemViewHolder(layoutInflater.inflate(R.layout.item_track, parent, false))
    }

    override fun getItemCount(): Int {
        return tracks.size
    }
}

class ItemViewHolder: RecyclerView.ViewHolder{

    val imgTrackType: ImageView
    val tvTrackDescription: TextView

    constructor(view: View): super(view){
        imgTrackType = view.findViewById(R.id.imgTrackType)
        tvTrackDescription = view.findViewById(R.id.tvTrackDescription)
    }

    fun initItem(track: TrekTrack){
        tvTrackDescription.text = track.description
    }
}








