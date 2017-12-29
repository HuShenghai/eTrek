package com.bottle.track.home.collection.poiedit

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bottle.track.*
import com.bottle.track.app.MyApplication
import com.bottle.track.base.view.BaseActivity
import com.bottle.track.core.api.Api
import com.bottle.track.core.api.BaseRequestBean
import com.bottle.track.core.api.request.UploadPoi
import com.bottle.track.core.db.convertToModelClass
import com.bottle.track.core.db.schema.TrekPoi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_poi_editor.*
import kotlinx.android.synthetic.main.title_layout.*

class PoiEditorActivity : BaseActivity(), View.OnClickListener {

    private var trekPoi: TrekPoi? = null

    companion object {
        fun start(activity: Activity, poi: TrekPoi, requestCode: Int){
            val intent = Intent(activity, PoiEditorActivity::class.java)
            intent.putExtra("poi", poi)
            activity.startActivityForResult(intent, requestCode)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_poi_editor)
        imgBack.setOnClickListener(this)
        tvSave.setOnClickListener(this)
        tvDelete.setOnClickListener(this)
        tvTitle.text = getString(R.string.edit)

        trekPoi = intent.getSerializableExtra("poi") as TrekPoi?
        edtTitle.setText(trekPoi?.title ?: "")
        edtDescription.setText(trekPoi?.description ?: "")
    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.imgBack -> { finish() }
            R.id.tvSave -> { saveTrekPoi(trekPoi!!) }
            R.id.tvDelete -> { deleteTrekPoi(trekPoi!!) }
        }
    }

    private fun saveTrekPoi(trekPoi: TrekPoi){
        trekPoi?.title = edtTitle.text.toString()
        trekPoi?.description = edtDescription.text.toString()
        MyApplication.app.daoSession?.trekPoiDao?.update(trekPoi!!)
        if(BuildConfig.DEBUG){
            finish()
        } else {
            testUploadPoints(convertToModelClass(trekPoi))
        }
    }

    private fun deleteTrekPoi(trekPoi: TrekPoi) {
        MyApplication.app.daoSession?.trekPoiDao?.delete(trekPoi)
        finish()
    }

    private fun testUploadPoints(poi: com.bottle.track.core.model.TrekPoi) {
        val points = listOf(poi)
        val uploadPoints = UploadPoi(points)
        val requestBean = BaseRequestBean(uploadPoints)
        Api.api.httpService.uploadPoints(requestBean)
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .doOnNext({
                    // 这里可以进行耗时操作，如读写数据库等
                    // Log.d(TAG, "doOnNext")
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    Toast.makeText(this, response.info, Toast.LENGTH_SHORT).show()
                    // Log.d(TAG, toJsonString(response as Object))
                    finish()

                }, { error ->
                    Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                    // Log.d(TAG, toJsonString(error as Object))
                })
    }

}
