package com.gdi.hahi.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import kotlin.properties.Delegates

/**
 * Created by gandi on 2017/8/20 0020.
 */

class TabPagerAdapter : FragmentPagerAdapter {

    internal var mFragments: List<Fragment> by Delegates.notNull()
    internal var mTitles: Array<String> by Delegates.notNull()


    constructor(fm: FragmentManager) : super(fm) {}

    constructor(fm: FragmentManager, mFragments: List<Fragment>, mTitles: Array<String>) : super(fm) {
        this.mFragments = mFragments
        this.mTitles = mTitles
    }

    override fun getCount(): Int {
        return mFragments.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mTitles[position]
    }

    override fun getItem(position: Int): Fragment {
        return mFragments[position]
    }
}