package com.bottle.track.home.collection

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.bottle.track.db.schema.TrekTrack
import android.view.LayoutInflater
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import com.bottle.track.R

/**
 * @Date 2017/12/8 16:45
 * @Author halfbottle
 * @Version 1.0.0
 * @Description TrekTrack 适配器
 */
class TrackAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>, View.OnClickListener{

    private val context: Context
    private val tracks: List<TrekTrack>
    private val layoutInflater: LayoutInflater
    private var onItemClickListener: OnItemClickListener? = null

    constructor(context: Context, tracks: List<TrekTrack>){
        this.context = context
        this.tracks = tracks
        layoutInflater = LayoutInflater.from(context)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        holder?.itemView?.tag = position
        if(holder is ItemViewHolder){
            val track = tracks[position]
            holder.initItem(track)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        var view = layoutInflater.inflate(R.layout.item_track, parent, false)
        view.setOnClickListener(this)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    override fun onClick(v: View?) {
        onItemClickListener?.onItemClick(v, v?.tag as Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.onItemClickListener = listener
    }
}

interface OnItemClickListener{
    fun onItemClick(view: View?, position: Int)
}

class ItemViewHolder: RecyclerView.ViewHolder{

    private val imgTrackType: ImageView
    private val tvTrackDescription: TextView

    constructor(view: View): super(view){
        imgTrackType = view.findViewById(R.id.imgTrackType)
        tvTrackDescription = view.findViewById(R.id.tvTrackDescription)
    }

    fun initItem(track: TrekTrack){
        tvTrackDescription.text = track.description
    }
}








