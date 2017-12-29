package com.bottle.track.home.collection.trackedit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.bottle.track.R
import com.bottle.track.base.view.BaseActivity
import com.bottle.track.core.db.schema.TrekTrack
import com.bottle.track.core.model.TrackType
import kotlinx.android.synthetic.main.activity_track_editor.*
import kotlinx.android.synthetic.main.title_layout.*

/**
 * @Date 2017/12/09 10:41
 * @Author halfbottle
 * @Version 1.0.0
 * @Description 轨迹编辑活动
 */
class TrackEditorActivity : BaseActivity(), View.OnClickListener, TrackEditorContract.View{

    companion object {
        fun start(activity: Activity, track: TrekTrack, requestCode: Int){
            val intent = Intent(activity, TrackEditorActivity::class.java)
            intent.putExtra("track", track)
            activity.startActivityForResult(intent, requestCode)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_track_editor)
        imgBack.setOnClickListener(this)
        tvSave.setOnClickListener(this)
        tvDelete.setOnClickListener(this)
        val trekTrack = intent.getSerializableExtra("track") as TrekTrack?
        trackEditorPresenter = TrackEditorPresenter(trekTrack!!, this)
        trackEditorPresenter?.start()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.imgBack -> { finish() }
            R.id.tvSave -> { saveTrekTrack() }
            R.id.tvDelete -> { deleteTrekTrack() }
        }
    }

    private fun saveTrekTrack(){
        trackEditorPresenter?.updateTrack(edtTitle.text.toString(), edtDescription.text.toString())
    }

    private fun deleteTrekTrack(){
        trackEditorPresenter?.deleteTrack()
    }

    private var trackEditorPresenter: TrackEditorContract.Presenter? = null

    override fun setPresenter(presenter: TrackEditorContract.Presenter) {
        this.trackEditorPresenter = presenter
    }

    override fun showTitle() {
        tvTitle.text = getString(R.string.edit)
    }

    override fun showTrack(trekTrack: TrekTrack) {
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

    override fun goback() {
        finish()
    }

}
