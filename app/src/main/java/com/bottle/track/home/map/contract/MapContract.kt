package com.bottle.track.home.map.contract

import com.bottle.track.base.presenter.BasePresenter
import com.bottle.track.base.view.BaseView

/**
 * @ClassName MapContract
 * @Author half_bottle
 * @Date 2017/12/29
 * @Description
 */
interface MapContract {

    interface View: BaseView<Presenter>{
        fun updateTrack()
    }

    interface Presenter: BasePresenter{

    }
}