package com.gdi.hahi.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gdi.hahi.R;
import com.gdi.hahi.base.BaseFragment;
import com.gdi.hahi.mvp.contract.HahiContract;
import com.gdi.hahi.mvp.model.bean.HahiBean;
import com.gdi.hahi.mvp.presenter.NeihanPresenter;
import com.gdi.hahi.ui.adapter.NeihanAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gandi on 2017/9/7 0007.
 */

@SuppressLint("ValidFragment")
public class ShowFragment extends BaseFragment implements HahiContract.View<List<HahiBean>> {

    private static final String TAG = "NeiHanFragment";

    View mView;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    // 标志位，标志已经初始化完成。
    private boolean isPrepared;
    protected boolean isVisible;
    NeihanPresenter presenter;
    NeihanAdapter adapter;
    String context_type = "-301" ;
    List<HahiBean> mList = new ArrayList<>();

    boolean isRefresh = true;
    long lastTime = -1;

    final int HANDLER_REFRESH_CLOSE = 1;
    final int HANDLER_CRAWLER_SUCCESS = 2;
    final int HANDLER_CRAWLER_ERROR = 3;


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLER_REFRESH_CLOSE:
                    if (swipeRefreshLayout.isRefreshing())
                        swipeRefreshLayout.setRefreshing(false);
                    break;
                case HANDLER_CRAWLER_SUCCESS:
                    mHandler.sendEmptyMessage(HANDLER_REFRESH_CLOSE);
                    break;
                case HANDLER_CRAWLER_ERROR:
                    mHandler.sendEmptyMessage(HANDLER_REFRESH_CLOSE);
                    break;
            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_show, null);
            isPrepared = true;
            init();
        }
        return mView;
    }


    private void init() {

        presenter = new NeihanPresenter(this, getActivity());
        presenter.setContentType(context_type);

        swipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.sl_neihan);
        recyclerView = (RecyclerView) mView.findViewById(R.id.rv_neihan);
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        adapter = new NeihanAdapter(getActivity(), mList);
        recyclerView.setAdapter(adapter);
//        photoAdapter.setOnItemClickLitener(this);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                requestData();
                mHandler.sendEmptyMessageDelayed(HANDLER_REFRESH_CLOSE, 6000);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isSlideToBottom(recyclerView)) {
                    isRefresh = false;
                    requestData();
                }
            }
        });
        requestData();
    }


    /**
     * 是否滑动到底部
     *
     * @param recyclerView
     * @return
     */
    public static boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
//        Log.i(TAG, "Extent: "+recyclerView.computeVerticalScrollExtent()+"Offset: "+recyclerView.computeVerticalScrollOffset()+"Range: "+recyclerView.computeVerticalScrollRange());
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange() - 200)
            return true;
        return false;
    }

    /**
     * 在这里实现Fragment数据的缓加载.
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            init();
        } else {
            isVisible = false;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != mView) {
            ((ViewGroup) mView.getParent()).removeView(mView);
        }
    }


    @Override
    public void smoothScrollToPosition(int postion) {

    }

    private void requestData(){
        if (lastTime==-1){
            lastTime = System.currentTimeMillis();
        }
        presenter.requestData(lastTime);
        lastTime = System.currentTimeMillis();
    }

    @Override
    public void setData(List<HahiBean> data) {
        mHandler.sendEmptyMessage(HANDLER_REFRESH_CLOSE);
        if (isRefresh) {
            mList.clear();
            mList.addAll(data);
            adapter.notifyDataSetChanged();
        } else {
            int start = mList.size();
            mList.addAll(data);
            adapter.notifyItemRangeInserted(start, mList.size() - 1);
        }


    }

//    private void closeRefresh(){
//        if (swipeRefreshLayout.isRefreshing())
//            swipeRefreshLayout.setRefreshing(false);
//    }
}
