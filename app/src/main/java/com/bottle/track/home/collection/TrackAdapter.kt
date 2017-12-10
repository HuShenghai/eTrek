package com.bottle.track.home.collection

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.bottle.track.db.schema.TrekTrack
import android.view.LayoutInflater
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.LinearLayout
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
        if(holder is ItemViewHolder){
            holder?.llContent?.tag = position
            holder?.imgMore?.tag = position
            val track = tracks[position]
            holder.initItem(track)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        var view = layoutInflater.inflate(R.layout.item_track, parent, false)
        view.findViewById<LinearLayout>(R.id.llContent).setOnClickListener(this)
        view.findViewById<ImageView>(R.id.imgMore).setOnClickListener(this)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.llContent -> {onItemClickListener?.onItemClick(v, v?.tag as Int)}
            R.id.imgMore -> {onItemClickListener?.onEditClick(v, v?.tag as Int)}
        }

    }

    fun setOnItemClickListener(listener: OnItemClickListener){
        this.onItemClickListener = listener
    }
}

interface OnItemClickListener{
    fun onItemClick(view: View?, position: Int)
    fun onEditClick(view: View?, position: Int)
}

class ItemViewHolder: RecyclerView.ViewHolder{

    val llContent: LinearLayout
    val imgMore: ImageView
    val imgTrackType: ImageView
    val tvTrackDescription: TextView

    constructor(view: View): super(view){
        llContent = view.findViewById(R.id.llContent)
        imgTrackType = view.findViewById(R.id.imgTrackType)
        tvTrackDescription = view.findViewById(R.id.tvTrackDescription)
        imgMore = view.findViewById(R.id.imgMore)
    }

    fun initItem(track: TrekTrack){
        tvTrackDescription.text = track.description
    }
}








