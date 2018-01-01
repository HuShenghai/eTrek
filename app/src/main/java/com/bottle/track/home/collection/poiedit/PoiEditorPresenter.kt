package com.bottle.track.home.collection.poiedit

import com.bottle.track.core.db.schema.TrekPoi

/**
 * @ClassName PoiEditorPresenter
 * @Author half_bottle
 * @Date 2018/1/1
 * @Description
 */
class PoiEditorPresenter: PoiEditorContract.Presenter {

    val view: PoiEditorContract.View

    constructor(view: PoiEditorContract.View){
        this.view = view
    }

    override fun start() {

    }

    override fun updatePoi(title: String, description: String): Boolean {
        return true
    }

    override fun deletePoi(trekPoi: TrekPoi): Boolean {
        return true
    }

}