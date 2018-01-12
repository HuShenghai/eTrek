package com.bottle.track.home.user

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bottle.track.base.view.BaseActivity
import com.bottle.track.base.view.LazyLoadFragment
import com.bottle.track.R
import com.bottle.track.base.ui.coordinatorlayout.CollapsingToolbarLayoutActivity
import com.bottle.track.base.view.createSimpleDialog
import com.bottle.track.home.user.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : LazyLoadFragment(), View.OnClickListener {

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

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        llSetting.setOnClickListener(this)
        llUserInfo.setOnClickListener(this)
        imgUserLogo.setOnClickListener(this)
        llMyLocation.setOnClickListener(this)

    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_home, container, false)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.llSetting -> {
                CollapsingToolbarLayoutActivity.start(activity)
            }
            R.id.llUserInfo -> {
                LoginActivity.start(context as BaseActivity, 100)
            }
            R.id.imgUserLogo -> {
                createSimpleDialog(activity, question = "这个对话框点击外部不可取消",
                        onOkClickListener = object : DialogInterface.OnClickListener {
                            override fun onClick(dialog: DialogInterface?, which: Int) {
                                showToast("Hello World!这是一般实现")
                            }
                        },
                        canceledOnTouchOutside = false).show()
            }
            R.id.llMyLocation -> {
                createSimpleDialog(activity, "点击显示: Hello World!",
                        DialogInterface.OnClickListener { dialog, which ->
                            showToast("Hello World!这是Lambda实现")
                        }, false).show()
            }
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun fetchData() {

    }

//    private fun apiTest(){
//        Api.api.httpService.queryTest()
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(Schedulers.io())
//                .doOnNext({
//                    // 这里可以进行耗时操作，如读写数据库等
//                    Log.d(TAG, "doOnNext")
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ response ->
//                    Toast.makeText(context, response.info, Toast.LENGTH_SHORT).show()
//                    Log.d(TAG, GsonHelper.gsonHelper.toJson(response.result))
//
//                }, { error ->
//                    Log.d(TAG, toJsonString(error as Object))
//                })
//    }

}
