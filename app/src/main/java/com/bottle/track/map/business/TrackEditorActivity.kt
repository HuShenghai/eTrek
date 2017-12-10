package com.bottle.track.map.business

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.bottle.track.BaseActivity
import com.bottle.track.R
import com.bottle.track.db.schema.TrekTrack
import kotlinx.android.synthetic.main.activity_track_editor.*
import kotlinx.android.synthetic.main.title_layout.*

/**
 * @Date 2017/12/09 10:41
 * @Author halfbottle
 * @Version 1.0.0
 * @Description 轨迹编辑活动
 */
class TrackEditorActivity : BaseActivity(), View.OnClickListener{

    companion object {
        fun start(activity: Activity, track: TrekTrack){
            val intent = Intent(activity, TrackEditorActivity::class.java)
            intent.putExtra("track", track)
            activity.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_editor)
        imgBack.setOnClickListener(this)
        tvSave.setOnClickListener(this)
        tvDelete.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.imgBack -> { finish() }
            R.id.tvSave -> {}
            R.id.tvDelete -> {}
        }
    }

}
