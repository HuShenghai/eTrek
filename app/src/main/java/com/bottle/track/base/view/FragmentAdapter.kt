package com.bottle.track.base.view

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class FragmentAdapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

    private var mFragments: Array<Fragment>? = null

    constructor(fm: FragmentManager, mFragments: Array<Fragment>): this(fm){
        this.mFragments = mFragments
    }

    override fun getItem(position: Int): Fragment {
        return mFragments!![position]
    }

    override fun getCount(): Int {
        return mFragments!!.size
    }

}