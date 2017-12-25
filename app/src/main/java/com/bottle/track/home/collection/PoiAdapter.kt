package com.bottle.track.home.collection

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.bottle.track.R
import com.bottle.track.db.schema.TrekPoi
import com.bottle.track.home.OnRecyclerViewItemClickListener

/**
 * @ClassName PoiAdapter
 * @Author half_bottle
 * @Date 2017/12/25
 * @Description
 */
class PoiAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>, View.OnClickListener {

    private val context: Context
    private val pois: List<TrekPoi>
    private val layoutInflater: LayoutInflater
    var onItemClickListener: OnRecyclerViewItemClickListener? = null
    var onPoiClickListener: OnPoiItemClickListener? = null

    constructor(context: Context, tracks: List<TrekPoi>){
        this.context = context
        this.pois = tracks
        layoutInflater = LayoutInflater.from(context)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if(holder is PoiViewHolder){
            holder?.llContent?.tag = position
            holder?.imgMore?.tag = position
            val poi = pois[position]
            holder.initItem(poi)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        var view = layoutInflater.inflate(R.layout.item_poi_2, parent, false)
        view.findViewById<LinearLayout>(R.id.llContent).setOnClickListener(this)
        view.findViewById<ImageView>(R.id.imgMore).setOnClickListener(this)
        return PoiViewHolder(view)
    }

    override fun getItemCount(): Int {
        return pois.size
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.llContent -> { onItemClickListener?.onItemClick(v, v?.tag as Int) }
            R.id.imgMore -> { onPoiClickListener?.onEditClick(v, v?.tag as Int) }
        }
    }
}

interface OnPoiItemClickListener{
    fun onEditClick(view: View?, position: Int)
}

class PoiViewHolder: RecyclerView.ViewHolder{

    val llContent: LinearLayout
    val imgMore: ImageView
    val poiDescription: TextView

    constructor(view: View): super(view){
        llContent = view.findViewById(R.id.llContent)
        poiDescription = view.findViewById(R.id.tvPoiDescription)
        imgMore = view.findViewById(R.id.imgMore)
    }

    fun initItem(poi: TrekPoi){
        poiDescription.text = poi.description
    }
}