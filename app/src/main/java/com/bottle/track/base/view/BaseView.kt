package com.bottle.track.base.view

/**
 * @Date 2017/12/28 18:47
 * @Author halfbottle
 * @Version 1.0.0
 * @Description
 */
interface BaseView<T> {
    fun setPresenter(presenter: T)
}