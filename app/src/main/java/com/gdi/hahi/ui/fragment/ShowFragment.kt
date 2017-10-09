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
import com.shuyu.gsyvideoplayer.video.base.GSYVideoView
import java.util.ArrayList
//import kotlinx.android.synthetic.main.fragment_show.*
import kotlin.properties.Delegates

/**
 * Created by gandi on 2017/9/7 0007.
 */

@SuppressLint("ValidFragment")
class ShowFragment : BaseFragment(), HahiContract.View<List<NeiHanData>>, SRefreshRecyclerView.OnRefreshViewListener,SRefreshRecyclerView.OnScrollListener{


    internal var sr_recycler_show: SRefreshRecyclerView by Delegates.notNull()
    internal var presenter: NeihanPresenter by Delegates.notNull()
    internal var adapter: NeihanAdapter by Delegates.notNull()
    internal var context_type = "-301"
    internal var mList: MutableList<NeiHanData> = ArrayList()

    internal var lastTime: Long = -1
    var firstVisibleItem = 0;

    override fun getLayoutId(): Int {
        return R.layout.fragment_show
    }

    override fun lazyLoad() {
        presenter = NeihanPresenter(this, activity)
        presenter.setContentType(context_type)

        sr_recycler_show = mView!!.findViewById(R.id.sr_recycler_view) as SRefreshRecyclerView
        adapter = NeihanAdapter(activity, mList)
        sr_recycler_show.setRecyclerView(LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false),DividerItemDecoration(activity, DividerItemDecoration.VERTICAL),null,adapter)
        sr_recycler_show.setOnRefreshListener(this)
        sr_recycler_show.setRefreshing(true)
        requestData()
    }

    override fun onRefresh() {
        requestData()
    }

    override fun onLoadMore() {
        requestData()
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
        val layoutManager = recyclerView!!.layoutManager
        if (layoutManager is LinearLayoutManager) {
            val FirstVisibleItem = layoutManager.findFirstVisibleItemPosition()
            if (firstVisibleItem != FirstVisibleItem) {
                firstVisibleItem = FirstVisibleItem
                //获取刚刚滑出可视界面的item
                val viewHolder = adapter.getViewHolder(firstVisibleItem-1)
                viewHolder?.let {
                    //viewHolder 的视频播放状态 为正在播放
                    if (GSYVideoView.CURRENT_STATE_PLAYING == viewHolder.gsy_player.currentState) {
                        //重置视频
                        viewHolder.gsy_player.onVideoReset()
                    }
                }
            }
        }
    }

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {

    }

    override fun smoothScrollToPosition(postion: Int) {
        sr_recycler_show.smoothScrollToPosition(postion)
    }

    private fun requestData() {
        if (lastTime == -1L) {
            lastTime = System.currentTimeMillis()
        }
        presenter.requestData(lastTime)
        lastTime = System.currentTimeMillis()
    }

    override fun setData(data: List<NeiHanData>?) {
        if (sr_recycler_show.refresh) {
            mList.clear()
            data?.let { mList.addAll(it) }
            adapter.notifyDataSetChanged()
        } else {
            val start = mList.size
            data?.let { mList.addAll(it) }
            adapter.notifyItemRangeInserted(start, mList.size - 1)
        }
        sr_recycler_show.onCloseRefresh()

    }

    companion object {
        private val TAG = "ShowFragment"

    }

}
