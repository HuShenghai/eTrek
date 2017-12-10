package com.bottle.track.home.collection

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bottle.track.BaseFragment
import com.bottle.track.MyApplication
import com.bottle.track.R
import com.bottle.track.db.gen.TrekTrackDao
import com.bottle.track.db.schema.TrekTrack
import com.bottle.track.map.business.TrackEditorActivity
import com.bottle.track.map.business.TrackPreviewActivity
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter
import kotlinx.android.synthetic.main.fragment_track.*

/**
 * @ClassName TrackFragment
 * @Author half_bottle
 * @Date 2017/11/25
 * @Description
 */
class TrackFragment: BaseFragment(), OnItemClickListener {

    override fun onEditClick(view: View?, position: Int) {
        TrackEditorActivity.start(activity, tracks!![position])
    }

    override fun onItemClick(view: View?, position: Int) {
         TrackPreviewActivity.start(activity, tracks!![position])
    }

    var tracks: List<TrekTrack>? = null
    var adapter: TrackAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val dao: TrekTrackDao? = MyApplication.app.daoSession?.trekTrackDao
        tracks = dao?.queryBuilder()?.list()
        adapter = TrackAdapter(context, tracks as List<TrekTrack>)
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

    override fun onDetach() {
        super.onDetach()
    }

    override fun fetchData() {

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