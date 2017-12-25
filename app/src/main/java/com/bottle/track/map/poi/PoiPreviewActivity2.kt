package com.bottle.track.map.poi

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.bottle.track.R
import com.bottle.track.db.schema.TrekPoi

class PoiPreviewActivity2 : AppCompatActivity() {

    companion object {
        fun start(activity: Activity, poi: TrekPoi, requestCode: Int){
            val intent = Intent(activity, PoiPreviewActivity2::class.java)
            intent.putExtra("poi", poi)
            activity.startActivityForResult(intent, requestCode)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poi_preview2)
    }
}
