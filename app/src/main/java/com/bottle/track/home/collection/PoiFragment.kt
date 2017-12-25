package com.bottle.track.home.collection

import android.content.Context
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bottle.track.BaseFragment
import com.bottle.track.MyApplication
import com.bottle.track.R
import com.bottle.track.db.schema.TrekPoi
import com.bottle.track.home.OnRecyclerViewItemClickListener
import com.bottle.track.map.poi.PoiEditorActivity
import com.bottle.track.map.poi.PoiPreviewActivity2
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import kotlinx.android.synthetic.main.fragment_track.*

/**
 * @ClassName PoiFragment
 * @Author half_bottle
 * @Date 2017/11/25
 * @Description
 */
class PoiFragment : BaseFragment(), OnRecyclerViewItemClickListener, OnPoiItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    override fun onRefresh() {
        pois.clear()
        initData()
        swipeRefreshLayout.isRefreshing = false
    }

    override fun onEditClick(view: View?, position: Int) {
        PoiEditorActivity.start(activity, pois[position], 100)
    }

    override fun onItemClick(view: View?, position: Int) {
        PoiPreviewActivity2.start(activity, pois[position], 101)
    }

    var pois = ArrayList<TrekPoi>()
    var adapter: PoiAdapter? = null

    private fun initView(){
        adapter = PoiAdapter(context, pois)
        adapter?.onItemClickListener = this
        adapter?.onPoiClickListener = this
        recyclerView.layoutManager = LinearLayoutManager(activity)
        var animaAdatper = ScaleInAnimationAdapter(adapter)
        animaAdatper.setDuration(700)
        animaAdatper.setFirstOnly(false)
        recyclerView.adapter = animaAdatper
        swipeRefreshLayout.setOnRefreshListener(this)
    }
    private fun initData(){
        var dbPois = MyApplication.app.daoSession?.trekPoiDao?.queryBuilder()?.list()
        if(dbPois != null && dbPois.size > 0){
            for(track in dbPois){
                pois.add(track)
            }
        }
        adapter?.notifyDataSetChanged()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
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

        fun newInstance(param1: String, param2: String): PoiFragment {
            val fragment = PoiFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}