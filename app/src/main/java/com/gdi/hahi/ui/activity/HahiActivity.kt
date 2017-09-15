package com.gdi.hahi.ui.activity

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import com.gdi.hahi.R
import com.gdi.hahi.base.BaseFragment
import com.gdi.hahi.ui.fragment.HomeFragment
import com.gdi.hahi.ui.fragment.LiveFragment
import com.gdi.hahi.ui.fragment.ShowFragment
import kotlin.properties.Delegates
import kotlinx.android.synthetic.main.activity_hahi.*

class HahiActivity : AppCompatActivity() {



    private var fragments: Array<BaseFragment>? = null
    private var index: Int = 0
    private var currentTabIndex: Int = 0
    internal var homeFragment: HomeFragment by Delegates.notNull()
    internal var liveFragment: LiveFragment by Delegates.notNull()
    internal var showFragment: ShowFragment by Delegates.notNull()

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> index = 0
            R.id.navigation_dashboard -> index = 1
            R.id.navigation_notifications -> index = 2
        }
        if (currentTabIndex != index) {
            val trx = supportFragmentManager.beginTransaction()
            trx.hide(fragments!![currentTabIndex])
            if (!fragments!![index].isAdded) {
                trx.add(R.id.fragment_container, fragments!![index])
            }
            trx.show(fragments!![index]).commit()
        } else {
            fragments!![currentTabIndex].smoothScrollToPosition(0)
        }
        currentTabIndex = index
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hahi)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        init()
    }

    private fun init() {
        homeFragment = HomeFragment()
        liveFragment = LiveFragment()
        showFragment = ShowFragment()
        fragments = arrayOf(homeFragment, liveFragment, showFragment)
        // add and show first fragment
        supportFragmentManager.beginTransaction().add(R.id.fragment_container, homeFragment)
                .add(R.id.fragment_container, liveFragment).hide(liveFragment).show(homeFragment)
                .commit()

    }
}
