package com.bottle.track.base.view

import android.os.Bundle
import android.support.v4.app.Fragment
import android.widget.Toast

/**
 * @ClassName 
 * @Author half_bottle
 * @Date 
 * @Description 懒加载 Fragment
 */
abstract class LazyLoadFragment : BaseFragment(){

    protected val TAG: String = LazyLoadFragment::class.java.simpleName
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

    private fun prepareFetchData(): Boolean {
        return prepareFetchData(false)
    }

    private fun prepareFetchData(forceUpdate: Boolean): Boolean {
        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
            fetchData()
            isDataInitiated = true
            return true
        }
        return false
    }

    fun showToast(toast: String){
        Toast.makeText(context, toast, Toast.LENGTH_SHORT).show()
    }
}