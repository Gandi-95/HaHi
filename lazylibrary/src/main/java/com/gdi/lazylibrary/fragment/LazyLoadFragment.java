package com.gdi.lazylibrary.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by gandi on 2017/9/27 0027.
 *
 * Fragment 数据懒加载
 */
public abstract class LazyLoadFragment extends Fragment {

    View mView;
    boolean isPrepared = false;
    boolean isVisible = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(getLayoutId(), null);
            //已经初始化布局了
            isPrepared = true;
            init();
        }
        return mView;
    }

    public void init() {
        if (!isPrepared || !isVisible) {
            return;
        }
        // setUserVisibleHint、onHiddenChanged 不再触发loadData
        isPrepared = false;

        lazyLoad();
    }


    /**
     * 如果是与ViewPager一起使用，调用的是setUserVisibleHint
     *
     * @param isVisibleToUser 是否显示出来了
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


    /**
     * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
     * 若是初始就show的Fragment 为了触发该事件 需要先hide再show
     *
     * @param hidden
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            isVisible = true;
            init();
        } else {
            isVisible = false;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mView != null) ((ViewGroup) mView.getParent()).removeView(mView);
    }

    public abstract void lazyLoad();

    public abstract int getLayoutId();
}
