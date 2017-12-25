package com.bottle.track.home

import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bottle.track.BaseFragment

import com.bottle.track.R
import com.bottle.track.home.collection.PoiFragment
import com.bottle.track.home.collection.TrackFragment
import kotlinx.android.synthetic.main.fragment_collection.*

class CollectionFragment : BaseFragment(), ViewPager.OnPageChangeListener, TabLayout.OnTabSelectedListener {

    private var mParam1: String? = null
    private var mParam2: String? = null
    private val locationFragment: Fragment?
    private val trackFragment: Fragment?
    private val array: Array<Fragment>
    private var fragmentAdapter: FragmentAdapter? = null

    init {
        locationFragment = PoiFragment.newInstance("","")
        trackFragment = TrackFragment.newInstance("","")
        array = arrayOf(locationFragment, trackFragment)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentAdapter = FragmentAdapter(fragmentManager,array)
        viewPager.addOnPageChangeListener(this)
        viewPager.adapter = fragmentAdapter
        tabLayout.addTab(tabLayout.newTab().setText("位置"))
        tabLayout.addTab(tabLayout.newTab().setText("轨迹"))
        tabLayout.addOnTabSelectedListener(this)
        tabLayout.getTabAt(1)?.select()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_collection, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun fetchData() {

    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

    }

    override fun onPageSelected(position: Int) {
        Log.d(TAG, "onPageSelected")
        tabLayout.getTabAt(position)?.select()
    }

    override fun onTabReselected(tab: TabLayout.Tab?) {

    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {

    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        Log.d(TAG, "onTabSelected")
        viewPager.currentItem = tab!!.position
    }

    companion object {

        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CollectionFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): CollectionFragment {
            val fragment = CollectionFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}
