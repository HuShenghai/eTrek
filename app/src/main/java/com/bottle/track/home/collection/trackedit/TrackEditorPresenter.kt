package com.bottle.track.home.collection.trackedit

import android.util.Log
import com.bottle.track.BuildConfig
import com.bottle.track.app.MyApplication
import com.bottle.track.app.TrekEvent
import com.bottle.track.core.api.Api
import com.bottle.track.core.api.BaseRequestBean
import com.bottle.track.core.api.convertToRequestBean
import com.bottle.track.core.db.schema.TrekTrack
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus

/**
 * @Date 2017/12/29 17:44
 * @Author halfbottle
 * @Version 1.0.0
 * @Description
 */
class TrackEditorPresenter: TrackEditorContract.Presenter {

    val TAG = "TrackEditorPresenter"

    val view: TrackEditorContract.View
    val trekTrack: TrekTrack

    constructor(trekTrack: TrekTrack, view: TrackEditorContract.View){
        this.trekTrack = trekTrack
        this.view = view
        this.view.setPresenter(this)
    }

    override fun start() {
        view.showTitle()
        view.showTrack(trekTrack)
    }

    override fun updateTrack(title: String, description: String): Boolean {
        this.trekTrack.title = title
        this.trekTrack.description = description
        MyApplication.app.daoSession?.trekTrackDao?.update(trekTrack)
        if(BuildConfig.DEBUG){
            EventBus.getDefault().post(TrekEvent(
                    TrekEvent.TYPE_UPDATE_TRACK, "修改一条轨迹", trekTrack))
            view.goback()
        } else {
            recordTrack(trekTrack!!) // release 版本才上传轨迹
        }
        return true
    }

    override fun deleteTrack(): Boolean {
        MyApplication.app.daoSession?.trekTrackDao?.delete(trekTrack)
        EventBus.getDefault().post(TrekEvent(
                TrekEvent.TYPE_UPDATE_TRACK, "删除一条轨迹", trekTrack))
        view.goback()
        return true
    }

    private fun recordTrack(trekTrack: TrekTrack) {
        Api.api.httpService.uploadTrekTrack(
                BaseRequestBean(convertToRequestBean(MyApplication.app.androidId!!, trekTrack)))
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
                    Log.d(TAG, "上传轨迹成功")
                    view.goback()
                }, { error ->
                    EventBus.getDefault().post(TrekEvent(
                            TrekEvent.TYPE_UPDATE_TRACK, "修改一条轨迹", trekTrack))
                    Log.d(TAG, "上传轨迹失败：" + error.message)
                    view.goback()
                })
    }

}