package com.bottle.track.home

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.amap.api.maps.AMap
import com.amap.api.maps.MapView
import com.bottle.track.R
import com.bottle.track.api.Api
import com.bottle.util.toJsonString
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeFragment : Fragment(), View.OnClickListener {

    private val TAG: String? = "HomeFragment"
    private var mParam1: String? = null
    private var mParam2: String? = null

    companion object {

        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        fun newInstance(param1: String, param2: String): Fragment {
            val fragment = HomeFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
            mParam2 = arguments.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater!!.inflate(R.layout.fragment_home, container, false)
        return view
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {

        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    private fun apiTest(){
        Api.api.httpService.queryTest()
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .doOnNext({
                    // 这里可以进行耗时操作，如读写数据库等
                    Log.d(TAG, "doOnNext")
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    Toast.makeText(context, response.info, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, toJsonString(response as Object))

                }, { error ->
                    Log.d(TAG, toJsonString(error as Object))
                })
    }

}
