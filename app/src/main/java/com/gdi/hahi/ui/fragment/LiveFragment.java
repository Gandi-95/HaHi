package com.gdi.hahi.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gdi.hahi.R;
import com.gdi.hahi.base.BaseFragment;
import com.gdi.hahi.mvp.contract.HahiContract;
import com.gdi.hahi.mvp.model.bean.HahiBean;
import com.gdi.hahi.mvp.model.bean.SimpleLiveBean;
import com.gdi.hahi.mvp.presenter.LivePresenter;
import com.gdi.hahi.ui.adapter.LiveAdapter;
import com.gdi.hahi.ui.adapter.NeihanAdapter;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static android.view.View.generateViewId;

/**
 * Created by gandi on 2017/9/7 0007.
 */

public class LiveFragment extends BaseFragment implements HahiContract.View<List<SimpleLiveBean>> {

    private static final String TAG = "LiveFragment";

    View mView;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    TextView tb_title;
    LiveAdapter adapter;
    LivePresenter presenter;
    List<SimpleLiveBean> mList = new ArrayList<>();
    boolean isRefresh = true;
    long lastTime = -1;

    int curItemPosition = 0;
    LiveAdapter.ViewHolder viewHolder;
    boolean isHidden = true;

    final int HANDLER_REFRESH_CLOSE = 1;
    final int HANDLER_START_FRIST = 2;
    final int HANDLER_CRAWLER_ERROR = 3;


    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HANDLER_REFRESH_CLOSE:
                    if (swipeRefreshLayout.isRefreshing())
                        swipeRefreshLayout.setRefreshing(false);
                    break;
                case HANDLER_START_FRIST:
                    if (adapter.getFristViewHolder() != null) {
                        viewHolder = adapter.getFristViewHolder();
                        if (!isHidden)
                            viewHolder.gsy_player.start();
                    }
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
            mView = inflater.inflate(R.layout.fragment_live, null);
            init();
        }
        return mView;
    }

    private void init() {
        presenter = new LivePresenter(this, getActivity());

        swipeRefreshLayout = (SwipeRefreshLayout) mView.findViewById(R.id.sl_neihan);
        recyclerView = (RecyclerView) mView.findViewById(R.id.rv_neihan);
        tb_title = (TextView) mView.findViewById(R.id.tb_title);

//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        adapter = new LiveAdapter(getActivity(), mList);
        recyclerView.setAdapter(adapter);
        tb_title.setText(getResources().getString(R.string.title_live));

        initListener();
        requestData();
    }

    private void initListener() {
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
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                // 只有LinearLayoutManager才有查找第一个和最后一个可见view位置的方法
                if (layoutManager instanceof LinearLayoutManager) {
                    int lastItem = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    int fristItem = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                    Log.i(TAG, "onScrollStateChanged: lastItem:" + lastItem + "  curItemPosition:" + curItemPosition);

                    if ((curItemPosition != lastItem - 1) && newState == SCROLL_STATE_IDLE) {
                        if (viewHolder != null) {
                            viewHolder.gsy_player.pause();
                        }
                        int index = (recyclerView.getChildCount() == 2) ? 0 : 1;
                        View view = recyclerView.getChildAt(index);
                        if (view != null) {
                            viewHolder = (LiveAdapter.ViewHolder) recyclerView.getChildViewHolder(view);
                            Log.i(TAG, "Count: " + recyclerView.getChildCount() + " name:" + viewHolder.tv_username.getText());
                        }
                        viewHolder.gsy_player.start();
                        curItemPosition = lastItem - 1;
                    }
                }

            }

            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (isSlideToBottom(recyclerView)) {
                    isRefresh = false;
                    requestData();
                }
            }
        });

    }


    private void requestData() {
        if (lastTime == -1) {
            lastTime = System.currentTimeMillis();
        }
        presenter.requestData(lastTime);
        lastTime = System.currentTimeMillis();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != mView) {
            ((ViewGroup) mView.getParent()).removeView(mView);
        }
    }

    /**
     * 是否滑动到底部
     *
     * @param recyclerView
     * @return
     */
    public static boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange() - 200)
            return true;
        return false;
    }


    @Override
    public void smoothScrollToPosition(int postion) {

    }

    @Override
    public void setData(List<SimpleLiveBean> data) {
        mHandler.sendEmptyMessage(HANDLER_REFRESH_CLOSE);
        if (isRefresh) {
            if (viewHolder != null)
                viewHolder.gsy_player.pause();
            mList.clear();
            mList.addAll(data);
            adapter.notifyDataSetChanged();
            mHandler.sendEmptyMessageDelayed(HANDLER_START_FRIST, 1000);
        } else {
            int start = mList.size();
            mList.addAll(data);
            adapter.notifyItemRangeInserted(start, mList.size() - 1);
        }

    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isHidden = hidden;

        if (viewHolder != null) {
            if (hidden) {
                viewHolder.gsy_player.pause();
            } else {
                viewHolder.gsy_player.start();
            }
        } else {
            mHandler.sendEmptyMessageDelayed(HANDLER_START_FRIST, 1000);
        }

    }
}
