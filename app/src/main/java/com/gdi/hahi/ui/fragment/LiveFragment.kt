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
import kotlin.properties.Delegates
//import kotlinx.android.synthetic.main.fragment_live.*

/**
 * Created by gandi on 2017/9/7 0007.
 */

class LiveFragment : BaseFragment(), HahiContract.View<List<LiveData>> {

    internal var mView: View? = null
    internal var rv_live: RecyclerView by Delegates.notNull()
    internal var sl_live:SwipeRefreshLayout by Delegates.notNull()
    internal var adapter: LiveAdapter by Delegates.notNull()
    internal var presenter: LivePresenter by Delegates.notNull()
    internal var mList: MutableList<LiveData> = ArrayList()
    internal var isRefresh = true
    internal var lastTime: Long = -1

    internal var curItemPosition = 0
    internal var viewHolder: LiveAdapter.ViewHolder? = null
    internal var isHidden = true

    internal val HANDLER_REFRESH_CLOSE = 1
    internal val HANDLER_START_FRIST = 2
    internal val HANDLER_CRAWLER_ERROR = 3


    internal var mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                HANDLER_REFRESH_CLOSE -> {
                    if (sl_live.isRefreshing)
                        sl_live.isRefreshing = false
                }
                HANDLER_START_FRIST -> {
                    adapter!!.fristViewHolder?.let {
                        viewHolder = adapter!!.fristViewHolder
                        if (!isHidden)
                            viewHolder!!.gsy_player.start()
                    }
                }
                HANDLER_CRAWLER_ERROR -> this.sendEmptyMessage(HANDLER_REFRESH_CLOSE)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mView == null) {
            mView = inflater!!.inflate(R.layout.fragment_live, null)
            init()
        }
        return mView
    }

    private fun init() {
        presenter = LivePresenter(this, activity)

        rv_live = mView!!.findViewById(R.id.rv_live) as RecyclerView
        sl_live = mView!!.findViewById(R.id.sl_live) as SwipeRefreshLayout
        //        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rv_live!!.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rv_live!!.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        adapter = LiveAdapter(activity, mList)
        rv_live!!.adapter = adapter
//        tb_title.text = resources.getString(R.string.title_live)

        initListener()
        requestData()
    }

    private fun initListener() {
        sl_live.setOnRefreshListener {
            isRefresh = true
            requestData()
            mHandler.sendEmptyMessageDelayed(HANDLER_REFRESH_CLOSE, 6000)
        }

        rv_live!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val layoutManager = recyclerView!!.layoutManager
                // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                if (layoutManager is LinearLayoutManager) {
                    val lastItem = layoutManager.findLastVisibleItemPosition()
                    val fristItem = layoutManager.findFirstVisibleItemPosition()
                    Log.i(TAG, "onScrollStateChanged: lastItem:$lastItem  curItemPosition:$curItemPosition")

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
                super.onScrolled(recyclerView, dx, dy)
                if (isSlideToBottom(recyclerView)) {
                    isRefresh = false
                    requestData()
                }
            }
        })

    }


    private fun requestData() {
        if (lastTime == -1L) {
            lastTime = System.currentTimeMillis()
        }
        presenter.requestData(lastTime)
        lastTime = System.currentTimeMillis()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mView?.let { (mView!!.parent as ViewGroup).removeView(mView) }
    }


    override fun smoothScrollToPosition(postion: Int) {
        rv_live.smoothScrollToPosition(postion)
    }

    override fun setData(data: List<LiveData>?) {
        mHandler.sendEmptyMessage(HANDLER_REFRESH_CLOSE)
        if (isRefresh) {
            if (viewHolder != null)
                viewHolder!!.gsy_player.pause()
            mList.clear()
            data?.let { mList.addAll(it) }
            adapter.notifyDataSetChanged()
            mHandler.sendEmptyMessageDelayed(HANDLER_START_FRIST, 1000)
        } else {
            val start = mList.size
            data?.let { mList.addAll(it) }
            adapter.notifyItemRangeInserted(start, mList.size - 1)
        }

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

        /**
         * 是否滑动到底部

         * @param recyclerView
         * *
         * @return
         */
        fun isSlideToBottom(recyclerView: RecyclerView?): Boolean {
            if (recyclerView == null) return false
            if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange() - 200)
                return true
            return false
        }
    }
}
