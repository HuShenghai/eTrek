package com.bottle.track.home.collection.poilist

import com.bottle.track.core.db.schema.TrekPoi

/**
 * @Date 2018/1/5 17:24
 * @Author halfbottle
 * @Version 1.0.0
 * @Description
 */
class PoiListPresenter: PoiListContract.Presenter {

    val view: PoiListContract.View

    constructor(view: PoiListContract.View){
        this.view = view
        this.view.setPresenter(this)
    }

    override fun start() {

    }

    override fun getPoiList(){

    }
}