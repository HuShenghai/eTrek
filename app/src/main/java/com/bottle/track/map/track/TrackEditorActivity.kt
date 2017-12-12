package com.bottle.track.map.track

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bottle.track.*
import com.bottle.track.api.Api
import com.bottle.track.api.BaseRequestBean
import com.bottle.track.api.convertToRequestBean
import com.bottle.track.db.schema.TrekTrack
import com.bottle.track.map.model.TrackType
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_track_editor.*
import kotlinx.android.synthetic.main.title_layout.*
import org.greenrobot.eventbus.EventBus

/**
 * @Date 2017/12/09 10:41
 * @Author halfbottle
 * @Version 1.0.0
 * @Description 轨迹编辑活动
 */
class TrackEditorActivity : BaseActivity(), View.OnClickListener{

    private val TAG = TrackEditorActivity::class.java.simpleName

    companion object {
        fun start(activity: Activity, track: TrekTrack, requestCode: Int){
            val intent = Intent(activity, TrackEditorActivity::class.java)
            intent.putExtra("track", track)
            activity.startActivityForResult(intent, requestCode)
        }
    }

    var trekTrack: TrekTrack? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_editor)
        imgBack.setOnClickListener(this)
        tvSave.setOnClickListener(this)
        tvDelete.setOnClickListener(this)
        tvTitle.text = getString(R.string.edit)
        trekTrack = intent.getSerializableExtra("track") as TrekTrack?
        edtTitle.setText(trekTrack?.title ?: "")
        edtDescription.setText(trekTrack?.description ?: "")
        var typeString: Int = R.string.free_stroke
        for(type in TrackType.values()){
            if(type.type == trekTrack?.type){
                typeString = type.getName()
                break
            }
        }
        tvTrackType.text = getString(typeString)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.imgBack -> { finish() }
            R.id.tvSave -> { saveTrekTrack() }
            R.id.tvDelete -> { deleteTrekTrack() }
        }
    }

    private fun saveTrekTrack(){
        if(trekTrack == null) return
        trekTrack?.title = edtTitle.text.toString()
        trekTrack?.description = edtDescription.text.toString()
        MyApplication.app.daoSession?.trekTrackDao?.update(trekTrack)
        if(BuildConfig.DEBUG){
            EventBus.getDefault().post(TrekEvent(
                    TrekEvent.TYPE_UPDATE_TRACK, "修改一条轨迹", trekTrack))
            finish()
        } else {
            recordTrack(trekTrack!!) // release 版本才上传轨迹
        }
    }

    private fun deleteTrekTrack(){
        MyApplication.app.daoSession?.trekTrackDao?.delete(trekTrack)
        EventBus.getDefault().post(TrekEvent(
                TrekEvent.TYPE_UPDATE_TRACK, "删除一条轨迹", trekTrack))
        finish()
    }

    private fun recordTrack(track: TrekTrack) {
        Api.api.httpService.uploadTrekTrack(
                BaseRequestBean(convertToRequestBean(MyApplication.app.androidId!!, track)))
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .doOnNext({
                    // 这里可以进行耗时操作，如读写数据库等
                    Log.d(TAG, "doOnNext")
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    EventBus.getDefault().post(TrekEvent(
                            TrekEvent.TYPE_UPDATE_TRACK, "修改一条轨迹", trekTrack))
                    finish()
                    Log.d(TAG, "上传轨迹成功")
                }, { error ->
                    EventBus.getDefault().post(TrekEvent(
                            TrekEvent.TYPE_UPDATE_TRACK, "修改一条轨迹", trekTrack))
                    finish()
                    Log.d(TAG, "上传轨迹失败：" + error.message)
                })
    }

}
