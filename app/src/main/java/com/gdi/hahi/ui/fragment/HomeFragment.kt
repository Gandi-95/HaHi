package com.gdi.hahi.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.flyco.tablayout.CommonTabLayout
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.gdi.hahi.R
import com.gdi.hahi.base.BaseFragment
import com.gdi.hahi.ui.adapter.TabEntity
import com.gdi.hahi.ui.adapter.TabPagerAdapter
import java.util.ArrayList
import kotlin.properties.Delegates

/**
 * Created by gandi on 2017/9/7 0007.
 */

class HomeFragment : BaseFragment(), OnTabSelectListener {

    internal var mView: View? = null
    internal var mViewPager: ViewPager by Delegates.notNull()
    internal var tl_home: CommonTabLayout by Delegates.notNull()

    private val mFragments = ArrayList<BaseFragment>()
    private val mTitles = arrayOf("推荐", "视频", "段子", "图片")
    private val contextType = arrayOf("-101", "-104", "-102", "-103")

    private val mTabEntities = ArrayList<CustomTabEntity>()


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mView == null) {
            mView = inflater!!.inflate(R.layout.fragment_home, null)
            init()
        }
        return mView
    }

    private fun init() {

        for (title in mTitles) {
            mTabEntities.add(TabEntity(title))
        }

        for (type in contextType) {
            mFragments.add(NeiHanFragment(type))
        }

        val adapter = TabPagerAdapter(fragmentManager, mFragments, mTitles)
        mViewPager = mView!!.findViewById(R.id.vp_home) as ViewPager
        tl_home = mView!!.findViewById(R.id.tl_home) as CommonTabLayout
        mViewPager.adapter = adapter
        tl_home.setTabData(mTabEntities)
        tl_home.setOnTabSelectListener(this)

        mViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                tl_home.currentTab = position
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
    }


    override fun smoothScrollToPosition(postion: Int) {
        mFragments[mViewPager.currentItem].smoothScrollToPosition(postion)
    }

    override fun onTabSelect(position: Int) {
        mViewPager.currentItem = position
    }

    override fun onTabReselect(position: Int) {

    }
}
