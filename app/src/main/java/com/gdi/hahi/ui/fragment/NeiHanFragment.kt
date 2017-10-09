package com.gdi.hahi.ui.fragment

import android.annotation.SuppressLint
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.gdi.hahi.R
import com.gdi.hahi.base.BaseFragment
import com.gdi.hahi.mvp.contract.HahiContract
import com.gdi.hahi.mvp.model.bean.NeiHanData
import com.gdi.hahi.mvp.presenter.NeihanPresenter
import com.gdi.hahi.ui.adapter.NeihanAdapter
import com.gdi.lazylibrary.ui.SRefreshRecyclerView
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.video.base.GSYVideoView
import java.util.ArrayList
import kotlin.properties.Delegates

/**
 * Created by gandi on 2017/9/7 0007.
 */

@SuppressLint("ValidFragment")
class NeiHanFragment(internal var context_type: String) : BaseFragment(), HahiContract.View<List<NeiHanData>>, SRefreshRecyclerView.OnRefreshViewListener, SRefreshRecyclerView.OnScrollListener {

    var sr_recycler_neihan: SRefreshRecyclerView by Delegates.notNull()
    var presenter: NeihanPresenter by Delegates.notNull()
    var adapter: NeihanAdapter by Delegates.notNull()
    var mList: MutableList<NeiHanData> = ArrayList()

    var isRefresh = true
    var lastTime: Long = -1
    var firstVisibleItem = 0;


    override fun getLayoutId(): Int {
        return R.layout.fragment_neihan;
    }

    override fun lazyLoad() {
        presenter = NeihanPresenter(this, activity)
        presenter.setContentType(context_type)

        sr_recycler_neihan = mView!!.findViewById(R.id.sr_recycler_view) as SRefreshRecyclerView

        adapter = NeihanAdapter(activity, mList)
        sr_recycler_neihan.setRecyclerView(LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false), DividerItemDecoration(activity, DividerItemDecoration.VERTICAL), null, adapter)
        sr_recycler_neihan.setOnRefreshListener(this)
        sr_recycler_neihan.setOnScrollListener(this)
        sr_recycler_neihan.setRefreshing(true)
        requestData()
    }


    private fun requestData() {
        if (lastTime == -1L) {
            lastTime = System.currentTimeMillis()
        }
        presenter.requestData(lastTime)
        lastTime = System.currentTimeMillis()
    }

    override fun onRefresh() {
        isRefresh = true
        requestData()
    }

    override fun onLoadMore() {
        isRefresh = false
        requestData()
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
        val layoutManager = recyclerView!!.layoutManager
        if (layoutManager is LinearLayoutManager) {
            val FirstVisibleItem = layoutManager.findFirstVisibleItemPosition()
            if (firstVisibleItem != FirstVisibleItem) {
                firstVisibleItem = FirstVisibleItem
                val viewHolder = adapter.getViewHolder(firstVisibleItem-1)
                viewHolder?.let {
                    if (GSYVideoView.CURRENT_STATE_PLAYING == viewHolder.gsy_player.currentState) {
                        viewHolder.gsy_player.onVideoReset()
                    }
                }
            }
        }
    }

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
    }

    override fun smoothScrollToPosition(postion: Int) {
        sr_recycler_neihan.smoothScrollToPosition(postion)
    }


    override fun setData(data: List<NeiHanData>?) {
        if (sr_recycler_neihan.refresh) {
            mList.clear()
            data?.let { mList.addAll(it) }
            adapter.notifyDataSetChanged()
        } else {
            val start = mList.size
            data?.let { mList.addAll(it) }
            adapter.notifyItemRangeInserted(start, mList.size - 1)
        }
        sr_recycler_neihan.onCloseRefresh()

    }

    companion object {
        private val TAG = "NeiHanFragment"

    }

}