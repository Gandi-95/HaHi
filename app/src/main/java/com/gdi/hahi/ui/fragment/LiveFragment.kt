package com.gdi.hahi.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gdi.hahi.R
import com.gdi.hahi.base.BaseFragment
import com.gdi.hahi.mvp.contract.HahiContract
import com.gdi.hahi.mvp.model.bean.LiveData
import com.gdi.hahi.mvp.presenter.LivePresenter
import com.gdi.hahi.ui.adapter.LiveAdapter
import java.util.ArrayList
import android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE
import com.gdi.lazylibrary.ui.SRefreshRecyclerView
import kotlin.properties.Delegates
//import kotlinx.android.synthetic.main.fragment_live.*

/**
 * Created by gandi on 2017/9/7 0007.
 */

class LiveFragment : BaseFragment(), HahiContract.View<List<LiveData>>, SRefreshRecyclerView.OnRefreshViewListener,SRefreshRecyclerView.OnScrollListener {

    internal var sr_recycler_live: SRefreshRecyclerView by Delegates.notNull()
    internal var adapter: LiveAdapter ?=null
    internal var presenter: LivePresenter by Delegates.notNull()
    internal var mList: MutableList<LiveData> = ArrayList()
    internal var lastTime: Long = -1

    internal var curItemPosition = 0
    internal var viewHolder: LiveAdapter.ViewHolder? = null
    internal var isHidden = true

    internal val HANDLER_START_FRIST = 1


    internal var mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                HANDLER_START_FRIST -> {
                    adapter?.let {  adapter!!.fristViewHolder?.let {
                        viewHolder = adapter!!.fristViewHolder
                        if (!isHidden)
                            viewHolder!!.gsy_player.start()
                    } }

                }
            }
        }
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_live
    }

    override fun lazyLoad() {
        presenter = LivePresenter(this, activity)

        sr_recycler_live = mView!!.findViewById(R.id.sr_recycler_view) as SRefreshRecyclerView
        adapter = LiveAdapter(activity, mList)
        sr_recycler_live.setRecyclerView(LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false),DividerItemDecoration(activity, DividerItemDecoration.VERTICAL),null,adapter)
        sr_recycler_live.setOnRefreshListener(this)
        sr_recycler_live.setOnScrollListener(this)
        sr_recycler_live.setRefreshing(true)
        requestData()
    }


    private fun requestData() {
        if (lastTime == -1L) {
            lastTime = System.currentTimeMillis()
        }
        presenter.requestData(lastTime)
        lastTime = System.currentTimeMillis()
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
        val layoutManager = recyclerView!!.layoutManager
        // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
        if (layoutManager is LinearLayoutManager) {
            val lastItem = layoutManager.findLastVisibleItemPosition()
            val fristItem = layoutManager.findFirstVisibleItemPosition()
            val fristcItem = layoutManager.findFirstCompletelyVisibleItemPosition()
            val lastcItem = layoutManager.findLastCompletelyVisibleItemPosition()
            Log.i(TAG, "onScrollStateChanged: lastItem:$lastItem  fristItem:$fristItem  fristcItem:$fristcItem  lastcItem:$lastcItem  ")

            if (curItemPosition != lastItem - 1 && newState == SCROLL_STATE_IDLE) {
                viewHolder?.let { viewHolder!!.gsy_player.pause() }

                val index = if (recyclerView.childCount == 2) 0 else 1
                val view = recyclerView.getChildAt(index)

                view?.let { viewHolder = recyclerView.getChildViewHolder(view) as LiveAdapter.ViewHolder }
                viewHolder!!.gsy_player.start()
                curItemPosition = lastItem - 1
            }
        }
    }

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {

    }

    override fun onRefresh() {
        requestData()
    }

    override fun onLoadMore() {
        requestData()
    }


    override fun smoothScrollToPosition(postion: Int) {
        sr_recycler_live.smoothScrollToPosition(postion)
    }

    override fun setData(data: List<LiveData>?) {
        if (sr_recycler_live.refresh) {
            if (viewHolder != null)
                viewHolder!!.gsy_player.pause()
            mList.clear()
            data?.let { mList.addAll(it) }
            adapter!!.notifyDataSetChanged()
            mHandler.sendEmptyMessageDelayed(HANDLER_START_FRIST, 1000)
        } else {
            val start = mList.size
            data?.let { mList.addAll(it) }
            adapter!!.notifyItemRangeInserted(start, mList.size - 1)
        }
        sr_recycler_live.onCloseRefresh()

    }


    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        isHidden = hidden

        if (viewHolder != null) {
            if (hidden) {
                viewHolder!!.gsy_player.pause()
            } else {
                viewHolder!!.gsy_player.start()
            }
        } else {
            mHandler.sendEmptyMessageDelayed(HANDLER_START_FRIST, 1000)
        }

    }

    companion object {

        private val TAG = "LiveFragment"

    }
}
