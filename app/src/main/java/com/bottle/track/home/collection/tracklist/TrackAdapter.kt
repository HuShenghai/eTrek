package com.bottle.track.home.collection.tracklist

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.bottle.track.core.db.schema.TrekTrack
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bottle.track.R
import com.bottle.track.base.view.OnRecyclerViewItemClickListener

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
    var onItemClickListener: OnRecyclerViewItemClickListener? = null
    var onTrackClickListener: OnTrackItemClickListener? = null

    constructor(context: Context, tracks: List<TrekTrack>){
        this.context = context
        this.tracks = tracks
        layoutInflater = LayoutInflater.from(context)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if(holder is ItemViewHolder){
            holder?.cvContent?.tag = position
            holder?.imgMore?.tag = position
            val track = tracks[position]
            holder.initItem(track)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        var view = layoutInflater.inflate(R.layout.item_track, parent, false)
        view.findViewById<CardView>(R.id.cvContent).setOnClickListener(this)
        view.findViewById<ImageView>(R.id.imgMore).setOnClickListener(this)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.cvContent -> {onItemClickListener?.onItemClick(v, v?.tag as Int)}
            R.id.imgMore -> {onTrackClickListener?.onEditClick(v, v?.tag as Int)}
        }

    }

}

interface OnTrackItemClickListener{
    fun onEditClick(view: View?, position: Int)
}

class ItemViewHolder: RecyclerView.ViewHolder{

    val cvContent: CardView
    val imgMore: ImageView
    val imgTrackType: ImageView
    val tvTrackDescription: TextView
    val tvTrackTitle: TextView

    constructor(view: View): super(view){
        cvContent = view.findViewById(R.id.cvContent)
        imgTrackType = view.findViewById(R.id.imgTrackType)
        tvTrackDescription = view.findViewById(R.id.tvTrackDescription)
        imgMore = view.findViewById(R.id.imgMore)
        this.tvTrackTitle = view.findViewById(R.id.tvTrackTitle)
    }

    fun initItem(track: TrekTrack){
        tvTrackDescription.text = track.description
        tvTrackTitle.text = track.title
    }
}








