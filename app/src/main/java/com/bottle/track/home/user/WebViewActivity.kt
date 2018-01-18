package com.bottle.track.home.user

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent

import com.bottle.track.R
import com.bottle.track.base.view.BaseActivity

/**
 * @Description
 */
class WebViewActivity : BaseActivity(), WebViewFragment.OnHelpFragmentInteractionListener {
    private var url: String? = null
    private var title: String? = null

    private var helpFragment: WebViewFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        if (savedInstanceState != null) {
            url = savedInstanceState.getString(URL)
            title = savedInstanceState.getString(TITLE)
        } else {
            url = intent?.getStringExtra(URL)
            title = intent?.getStringExtra(TITLE)
        }
        helpFragment = WebViewFragment.newInstance(url!!, title)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.frameLayout, helpFragment)
                .commit()
    }

    override fun onSaveInstanceState(bundle: Bundle?) {
        super.onSaveInstanceState(bundle)
        bundle!!.putString(URL, url)
        bundle.putString(TITLE, title)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (helpFragment!!.goBack()) {
                return true
            } else {
                finish()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onFinish() {
        finish()
    }

    companion object {

        private val URL = "url"       // 需要访问的url
        private val TITLE = "title"   // 如果要显示一个固定的标题，则传入标题

        /**
         *
         * @param activity
         * @param url       网址
         */
        fun start(activity: Activity, url: String) {
            val intent = Intent(activity, WebViewActivity::class.java)
            intent.putExtra(URL, url)
            activity.startActivity(intent)
        }

        /**
         *
         * @param activity
         * @param url       网址
         * @param title     显示的标题
         */
        fun start(activity: Activity, url: String, title: String) {
            val intent = Intent(activity, WebViewActivity::class.java)
            intent.putExtra(URL, url)
            intent.putExtra(TITLE, title)
            activity.startActivity(intent)
        }
    }
}
