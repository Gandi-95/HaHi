package com.gdi.hahi.ui.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gdi.hahi.R
import com.gdi.hahi.base.BaseFragment
import com.gdi.hahi.mvp.contract.HahiContract
import com.gdi.hahi.mvp.model.bean.NeiHanData
import com.gdi.hahi.mvp.presenter.NeihanPresenter
import com.gdi.hahi.ui.adapter.NeihanAdapter
import java.util.ArrayList
//import kotlinx.android.synthetic.main.fragment_neihan.*
import kotlin.properties.Delegates

/**
 * Created by gandi on 2017/9/7 0007.
 */

@SuppressLint("ValidFragment")
class NeiHanFragment(internal var context_type: String) : BaseFragment(), HahiContract.View<List<NeiHanData>> {

    internal var mView: View? = null
    internal var rv_neihan: RecyclerView by Delegates.notNull()
    internal var sl_neihan:SwipeRefreshLayout by Delegates.notNull()
    // 标志位，标志已经初始化完成。
    private var isPrepared: Boolean = false
    internal var isVisible: Boolean = false
    internal var presenter: NeihanPresenter by Delegates.notNull()
    internal var adapter: NeihanAdapter by Delegates.notNull()
    internal var mList: MutableList<NeiHanData> = ArrayList()

    internal var isRefresh = true
    internal var lastTime: Long = -1

    internal val HANDLER_REFRESH_CLOSE = 1
    internal val HANDLER_CRAWLER_SUCCESS = 2
    internal val HANDLER_CRAWLER_ERROR = 3


    internal var mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                HANDLER_REFRESH_CLOSE -> if (sl_neihan.isRefreshing)
                    sl_neihan.isRefreshing = false
                HANDLER_CRAWLER_SUCCESS -> this.sendEmptyMessage(HANDLER_REFRESH_CLOSE)
                HANDLER_CRAWLER_ERROR -> this.sendEmptyMessage(HANDLER_REFRESH_CLOSE)
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mView == null) {
            mView = inflater!!.inflate(R.layout.fragment_neihan, null)
            isPrepared = true
            init()
        }
        return mView
    }


    private fun init() {
        if (!isPrepared || !isVisible) {
            return
        }
        isPrepared = false

        presenter = NeihanPresenter(this, activity)
        presenter.setContentType(context_type)

        rv_neihan = mView!!.findViewById(R.id.rv_neihan) as RecyclerView
        sl_neihan = mView!!.findViewById(R.id.sl_neihan) as SwipeRefreshLayout

        //        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rv_neihan.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rv_neihan.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
        adapter = NeihanAdapter(activity, mList)
        rv_neihan.adapter = adapter
        //        photoAdapter.setOnItemClickLitener(this);

        sl_neihan.setOnRefreshListener {
            isRefresh = true
            requestData()
            mHandler.sendEmptyMessageDelayed(HANDLER_REFRESH_CLOSE, 6000)
        }

        rv_neihan.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (isSlideToBottom(recyclerView)) {
                    isRefresh = false
                    requestData()
                }
            }
        })
        requestData()
    }

    /**
     * 在这里实现Fragment数据的缓加载.

     * @param isVisibleToUser
     */
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint) {
            isVisible = true
            init()
        } else {
            isVisible = false
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        mView?.let { (mView!!.parent as ViewGroup).removeView(mView) }
    }


    override fun smoothScrollToPosition(postion: Int) {
        rv_neihan.smoothScrollToPosition(postion)
    }

    private fun requestData() {
        if (lastTime == -1L) {
            lastTime = System.currentTimeMillis()
        }
        presenter.requestData(lastTime)
        lastTime = System.currentTimeMillis()
    }

    override fun setData(data: List<NeiHanData>?) {
        mHandler.sendEmptyMessage(HANDLER_REFRESH_CLOSE)
        if (isRefresh) {
            mList.clear()
            data?.let { mList.addAll(it) }
            adapter.notifyDataSetChanged()
        } else {
            val start = mList.size
            data?.let { mList.addAll(it) }
            adapter.notifyItemRangeInserted(start, mList.size - 1)
        }


    }

    companion object {

        private val TAG = "NeiHanFragment"


        /**
         * 是否滑动到底部

         * @param recyclerView
         * *
         * @return
         */
        fun isSlideToBottom(recyclerView: RecyclerView?): Boolean {
            if (recyclerView == null) return false
            //        Log.i(TAG, "Extent: "+recyclerView.computeVerticalScrollExtent()+"Offset: "+recyclerView.computeVerticalScrollOffset()+"Range: "+recyclerView.computeVerticalScrollRange());
            if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange() - 200)
                return true
            return false
        }
    }

    //    private void closeRefresh(){
    //        if (swipeRefreshLayout.isRefreshing())
    //            swipeRefreshLayout.setRefreshing(false);
    //    }
}
