package com.bottle.track

import android.os.Bundle
import android.support.v4.app.Fragment

/**
 * @ClassName 
 * @Author half_bottle
 * @Date 
 * @Description
 */
abstract class BaseFragment : Fragment(){

    protected val TAG: String = BaseFragment::class.java.simpleName
    protected var isViewInitiated: Boolean = false
    protected var isVisibleToUser: Boolean = false
    protected var isDataInitiated: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        isViewInitiated = true
        prepareFetchData()
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        this.isVisibleToUser = isVisibleToUser
        prepareFetchData()
    }

    abstract fun fetchData()

    fun prepareFetchData(): Boolean {
        return prepareFetchData(false)
    }

    fun prepareFetchData(forceUpdate: Boolean): Boolean {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            fetchData()
            isDataInitiated = true
            return true
        }
        return false
    }
}