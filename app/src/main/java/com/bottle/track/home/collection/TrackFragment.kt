package com.bottle.track.home.collection

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bottle.track.*
import com.bottle.track.db.gen.TrekTrackDao
import com.bottle.track.db.schema.TrekTrack
import com.bottle.track.map.business.TrackEditorActivity
import com.bottle.track.map.business.TrackPreviewActivity
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import kotlinx.android.synthetic.main.fragment_track.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @ClassName TrackFragment
 * @Author half_bottle
 * @Date 2017/11/25
 * @Description
 */
class TrackFragment: BaseFragment(), OnItemClickListener {

    private final val REQUEST_EDIT = 0x13

    override fun onEditClick(view: View?, position: Int) {
        TrackEditorActivity.start(activity, tracks!![position], REQUEST_EDIT)
    }

    override fun onItemClick(view: View?, position: Int) {
         TrackPreviewActivity.start(activity, tracks!![position])
    }

    var tracks = ArrayList<TrekTrack>()
    var adapter: TrackAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    private fun initData(){
        var dbTracks = MyApplication.app.daoSession?.trekTrackDao?.queryBuilder()?.list()
        if(dbTracks != null && dbTracks.size > 0){
            for(track in dbTracks){
                tracks.add(track)
            }
        }
        adapter?.notifyDataSetChanged()
    }

    private fun initView(){
        adapter = TrackAdapter(context, tracks)
        adapter?.setOnItemClickListener(this)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        var animaAdatper = ScaleInAnimationAdapter(adapter)
        animaAdatper.setDuration(700)
        animaAdatper.setFirstOnly(false)
        recyclerView.adapter = animaAdatper
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_track, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun fetchData() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onReceiveEvent(event: TrekEvent<Any>) {
        when (event.type) {
            TrekEvent.TYPE_UPDATE_TRACK -> {
                tracks.clear()
                initData()
            }
        }
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
        fun newInstance(param1: String, param2: String): TrackFragment {
            val fragment = TrackFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}