package com.bottle.track.home.collection.poilist

import com.bottle.track.base.presenter.BasePresenter
import com.bottle.track.base.view.BaseView
import com.bottle.track.core.db.schema.TrekPoi

/**
 * @Date 2018/1/5 17:19
 * @Author halfbottle
 * @Version 1.0.0
 * @Description
 */
interface PoiListContract {

    interface View: BaseView<Presenter>{

        fun showPoiList() // 显示兴趣点

    }

    interface Presenter: BasePresenter{

        fun getPoiList() // 获取兴趣点列表

    }
}