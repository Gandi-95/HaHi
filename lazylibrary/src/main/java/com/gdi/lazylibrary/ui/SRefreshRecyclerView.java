package com.gdi.lazylibrary.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.gdi.lazylibrary.R;


/**
 * Created by gandi on 2017/9/17 0017.
 */

public class SRefreshRecyclerView extends FrameLayout implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "SRefreshRecyclerView";
    Context mContext;
    SwipeRefreshLayout refreshLayout;
    RecyclerView recyclerView;

    OnRefreshViewListener mRefreshViewListener;
    OnScrollListener mScrollListener;

    boolean isLoading = false;
    boolean isRefresh = true;

    final int CLOSE_REFRESH_TIME = 5000;
    final int CLOSE_REFRESH = 1;

    Handler mHandler = new Handler() {
        @SuppressLint("WrongConstant")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CLOSE_REFRESH:
                    isLoading = false;
                    if (refreshLayout.isRefreshing()) {
                        refreshLayout.setRefreshing(false);
                    }
                    break;
            }
        }
    };

    public SRefreshRecyclerView(@NonNull Context context) {
        this(context, null, 0);
    }

    public SRefreshRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SRefreshRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
        obtainAttributes(context, attrs);
    }

    private void init(Context context) {
        this.mContext = context;

        LayoutInflater.from(context).inflate(R.layout.swiperefresh_recyclerview, this);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);

        refreshLayout.setOnRefreshListener(this);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (mScrollListener != null) {
                    mScrollListener.onScrollStateChanged(recyclerView, newState);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
                    int lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                    int totalItemCount = recyclerView.getAdapter().getItemCount();

                    Log.i(TAG, "last: " + lastVisibleItem + "  total: " + totalItemCount + "  isLoading: " + isLoading);

                    if (lastVisibleItem >= totalItemCount - 3 && dy > 0 && !isLoading) {
                        Log.i(TAG, "onScrolled: ");
                        mRefreshViewListener.onLoadMore();
                        isLoading = true;
                        isRefresh = false;
                    }
                }
                if (mScrollListener != null) {
                    mScrollListener.onScrolled(recyclerView, dx, dy);
                }

//                if (isSlideToBottom(recyclerView)) {
//                    mRefreshViewListener.onLoadMore();
//                }
            }
        });
    }

    private void obtainAttributes(Context context, AttributeSet attrs) {

    }

    public void setRecyclerView(RecyclerView.LayoutManager layout, RecyclerView.ItemDecoration decor, RecyclerView.ItemAnimator animator, RecyclerView.Adapter adapter) {
        if (layout == null) {
            throw new NullPointerException("LayoutManager is null");
        }
        if (adapter == null) {
            throw new NullPointerException("Adapter is null");
        }
        setLayoutManager(layout);
        if (decor != null) {
            addItemDecoration(decor);
        }
        if (animator != null) {
            setItemAnimator(animator);
        }
        setAdapter(adapter);
    }


    public void setLayoutManager(RecyclerView.LayoutManager layout) {
        recyclerView.setLayoutManager(layout);
    }

    public void addItemDecoration(RecyclerView.ItemDecoration decor) {
        recyclerView.addItemDecoration(decor);
    }

    public void setItemAnimator(RecyclerView.ItemAnimator animator) {
        recyclerView.setItemAnimator(animator);
    }

    public void setAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setAdapter(adapter);
    }

    public void smoothScrollToPosition(int postion) {
        recyclerView.smoothScrollToPosition(postion);
    }


    @Override
    public void onRefresh() {
        //5秒取消刷新效果
        mHandler.sendEmptyMessageDelayed(CLOSE_REFRESH, CLOSE_REFRESH_TIME);
        //进行刷新数据请求
        mRefreshViewListener.onRefresh();
        isRefresh = true;
    }

    public void onCloseRefresh() {
        mHandler.sendEmptyMessage(CLOSE_REFRESH);
    }

    public void setRefreshing(boolean refreshing) {
        refreshLayout.setRefreshing(refreshing);
    }

    public boolean getRefresh() {
        return isRefresh;
    }


    /**
     * 是否滑动到底部
     *
     * @param recyclerView
     * @return
     */
    public boolean isSlideToBottom(RecyclerView recyclerView) {
        if (recyclerView == null) return false;
        if (recyclerView.computeVerticalScrollExtent() + recyclerView.computeVerticalScrollOffset() >= recyclerView.computeVerticalScrollRange())
            return true;
        return false;
    }

    public SwipeRefreshLayout getRefreshLayout() {
        return refreshLayout;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }


    public interface OnRefreshViewListener {
        /**
         * 下拉刷新的回调
         */
        void onRefresh();

        /**
         * 上拉加载更多的回调
         */
        void onLoadMore();
    }

    public void setOnRefreshListener(OnRefreshViewListener listener) {
        mRefreshViewListener = listener;
    }


    public interface OnScrollListener {
        void onScrollStateChanged(RecyclerView recyclerView, int newState);

        void onScrolled(RecyclerView recyclerView, int dx, int dy);
    }

    public void setOnScrollListener(OnScrollListener listener) {
        mScrollListener = listener;
    }
}
