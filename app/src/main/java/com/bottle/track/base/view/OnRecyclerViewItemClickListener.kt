package com.bottle.track.base.view

import android.view.View

/**
 * @Date 2017/12/12 17:33
 * @Author halfbottle
 * @Version 1.0.0
 * @Description RecyclerView 的OnItemClickListener
 */
interface OnRecyclerViewItemClickListener {
    fun onItemClick(view: View?, position: Int)
}