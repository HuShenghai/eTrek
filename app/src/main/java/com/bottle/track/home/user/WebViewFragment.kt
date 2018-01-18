package com.bottle.track.home.user

import android.content.Context
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView

import com.bottle.track.BuildConfig
import com.bottle.track.R
import com.bottle.track.base.view.BaseFragment

import com.bottle.util.hasJellyBean
import com.bottle.util.hasJellyBeanMR1

/**
 * 连接异常，逻辑编程帮助页面，打开一个连接，并显示标题，支持网页返回，仅此而已，没有更多功能
 */
class WebViewFragment : BaseFragment(), View.OnClickListener {

    private var url: String? = null
    private var title: String? = null

    protected var imgBack: ImageView? = null

    protected var tvTitle: TextView? = null

    protected var pgLoading: ProgressBar? = null

    protected var webView: WebView? = null

    private var mListener: OnHelpFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            url = arguments.getString(URL)
            title = arguments.getString(TITLE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val contentView = inflater!!.inflate(R.layout.fragment_webview, container, false)
        initWebView(contentView)
        return contentView
    }

    private fun initWebView(view: View) {
        this.imgBack = view.findViewById(R.id.imgBack)
        this.imgBack?.setOnClickListener(this)
        this.tvTitle = view.findViewById(R.id.tvTitle)
        this.pgLoading = view.findViewById(R.id.pgLoading)
        this.webView = view.findViewById(R.id.webView)

        if (BuildConfig.DEBUG && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true)
        }
        webView?.settings?.displayZoomControls = false
        webView?.settings?.setSupportZoom(false)
        webView?.settings?.useWideViewPort = false
        webView?.settings?.javaScriptEnabled = true
        webView?.settings?.loadWithOverviewMode = true
        if (hasJellyBean()) {
            webView?.settings?.allowUniversalAccessFromFileURLs = true
        }
        if (hasJellyBeanMR1()) {
            webView?.settings?.mediaPlaybackRequiresUserGesture = false
        }
        webView?.settings?.allowContentAccess = true
        webView?.settings?.allowFileAccess = true
        webView?.settings?.databaseEnabled = true
        webView?.settings?.domStorageEnabled = true
        webView?.settings?.setAppCacheEnabled(false)
        webView?.settings?.cacheMode = WebSettings.LOAD_NO_CACHE
        webView?.settings?.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        webView?.isScrollContainer = false
        webView?.webViewClient = MyWebViewClient()
        webView?.webChromeClient = MyWebChromeClient()
        webView?.loadUrl(url)
        tvTitle?.text = url
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnHelpFragmentInteractionListener) {
            mListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    /**
     *
     * @return true webview 网页返回上一页，false 没有更多页面，自行处理即可
     */
    fun goBack(): Boolean {
        if (webView!!.canGoBack()) {
            webView?.goBack()
            return true
        } else {
            return false
        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    override fun onClick(v: View) {
        if (v.id == R.id.imgBack) {
            if (webView!!.canGoBack()) {
                webView?.goBack()
            } else {
                if (mListener != null) {
                    mListener!!.onFinish()
                }
            }
        }
    }

    inner class MyWebChromeClient : WebChromeClient() {

        override fun onProgressChanged(view: WebView?, progress: Int) {
            pgLoading?.progress = progress
        }
    }

    inner class MyWebViewClient : WebViewClient() {

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            pgLoading?.visibility = View.VISIBLE
            pgLoading?.progress = 0
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            pgLoading?.progress = 100
            // 如果传进来的标题为空，则显示网页的title，否则优先显示传进来的标题
            tvTitle?.text = if (TextUtils.isEmpty(title)) view?.title else title
            pgLoading?.postDelayed({ pgLoading?.visibility = View.GONE }, 1500)
        }
    }

    interface OnHelpFragmentInteractionListener {
        fun onFinish()  // 返回键，webview没有页面可以返回时调用
    }

    companion object {

        private val URL = "url"       // 需要访问的url
        private val TITLE = "title"   // 如果要显示一个固定的标题，则传入标题

        /**
         * @param url   要打开的连接
         * @param title 要显示的标题，如果没有，那么会显示网页中的title标签
         * @return A new instance of fragment WebViewFragment.
         */
        fun newInstance(url: String, title: String?): WebViewFragment {
            val fragment = WebViewFragment()
            val args = Bundle()
            args.putString(URL, url)
            args.putString(TITLE, title)
            fragment.arguments = args
            return fragment
        }
    }
}
