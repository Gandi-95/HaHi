package com.gdi.hahi.ui.fragment

import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import com.gdi.hahi.R
import com.gdi.hahi.base.BaseFragment
import com.gdi.hahi.ui.adapter.TabPagerAdapter
import java.util.ArrayList
import kotlin.properties.Delegates

/**
 * Created by gandi on 2017/9/7 0007.
 */

class HomeFragment : BaseFragment(){

    internal var mViewPager: ViewPager by Delegates.notNull()
    internal var tl_home: TabLayout by Delegates.notNull()

    private val mFragments = ArrayList<BaseFragment>()
    private val mTitles = arrayOf("推荐", "视频", "段子", "图片")
    private val contextType = arrayOf("-101", "-104", "-102", "-103")


    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun lazyLoad() {

        for (type in contextType) {
            mFragments.add(NeiHanFragment(type))
        }

        val adapter = TabPagerAdapter(fragmentManager, mFragments, mTitles)
        mViewPager = mView!!.findViewById(R.id.vp_home) as ViewPager
        tl_home = mView!!.findViewById(R.id.tl_home) as TabLayout
        mViewPager.adapter = adapter

        tl_home.setupWithViewPager(mViewPager);
        tl_home.setTabMode(TabLayout.MODE_FIXED);

    }



    override fun smoothScrollToPosition(postion: Int) {
        mFragments[mViewPager.currentItem].smoothScrollToPosition(postion)
    }

}
