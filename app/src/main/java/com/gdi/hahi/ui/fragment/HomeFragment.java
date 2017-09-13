package com.gdi.hahi.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.gdi.hahi.R;
import com.gdi.hahi.base.BaseFragment;
import com.gdi.hahi.ui.adapter.TabEntity;
import com.gdi.hahi.ui.adapter.TabPagerAdapter;

import java.util.ArrayList;

/**
 * Created by gandi on 2017/9/7 0007.
 */

public class HomeFragment extends BaseFragment implements OnTabSelectListener {

    View mView;
    ViewPager mViewPager;

    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private final String[] mTitles = {
            "推荐", "视频", "段子", "图片"
    };
    private final String[] contextType = {
            "-101", "-104", "-102", "-103"
    };

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mView == null) {
            mView = inflater.inflate(R.layout.fragment_home, null);
            init();
        }
        return mView;
    }

    private void init() {

        for (String title : mTitles) {
            mTabEntities.add(new TabEntity(title));
        }

        for (String type : contextType) {
            mFragments.add(new NeiHanFragment(type));
        }

        TabPagerAdapter adapter = new TabPagerAdapter(getFragmentManager(), mFragments, mTitles);
        mViewPager = (ViewPager) mView.findViewById(R.id.vp_photo);
        mViewPager.setAdapter(adapter);
        final CommonTabLayout commonTabLayout = (CommonTabLayout) mView.findViewById(R.id.tl_7);
        commonTabLayout.setTabData(mTabEntities);
        commonTabLayout.setOnTabSelectListener(this);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                commonTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    @Override
    public void smoothScrollToPosition(int postion) {

    }

    @Override
    public void onTabSelect(int position) {
        mViewPager.setCurrentItem(position);
    }

    @Override
    public void onTabReselect(int position) {

    }
}
