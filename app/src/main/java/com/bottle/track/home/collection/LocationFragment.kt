package com.bottle.track.home.collection

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bottle.track.BaseFragment
import com.bottle.track.R

/**
 * @ClassName LocationFragment
 * @Author half_bottle
 * @Date 2017/11/25
 * @Description
 */
class LocationFragment: BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.fragment_location, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun fetchData() {

    }

    companion object {

        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        fun newInstance(param1: String, param2: String): LocationFragment {
            val fragment = LocationFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}