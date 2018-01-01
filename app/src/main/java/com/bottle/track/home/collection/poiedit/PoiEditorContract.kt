package com.bottle.track.home.collection.poiedit

import com.bottle.track.base.presenter.BasePresenter
import com.bottle.track.base.view.BaseView
import com.bottle.track.core.db.schema.TrekPoi

/**
 * @ClassName PoiEditorContract
 * @Author half_bottle
 * @Date 2018/1/1
 * @Description
 */
interface PoiEditorContract {

    interface View: BaseView<Presenter>{
        fun showTitle(title: String)
        fun showPoiInfo(trekPoi: TrekPoi)
    }

    interface Presenter: BasePresenter{

        fun updatePoi(title: String, description: String): Boolean
        fun deletePoi(trekPoi: TrekPoi): Boolean

    }
}