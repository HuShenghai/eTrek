package com.bottle.track.home.collection.trackedit

import com.bottle.track.base.presenter.BasePresenter
import com.bottle.track.base.view.BaseView
import com.bottle.track.core.db.schema.TrekTrack

/**
 * @Date 2017/12/29 17:37
 * @Author halfbottle
 * @Version 1.0.0
 * @Description
 */
interface TrackEditorContract {

    interface View: BaseView<Presenter>{
        fun showTitle()
        fun showTrack(track: TrekTrack)
        fun goback()
    }

    interface Presenter: BasePresenter{
        fun updateTrack(title: String, description: String): Boolean
        fun deleteTrack(): Boolean
    }
}