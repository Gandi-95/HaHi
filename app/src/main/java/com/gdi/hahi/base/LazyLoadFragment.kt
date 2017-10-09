package com.gdi.hahi.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by gandi on 2017/9/7 0007.
 */

abstract class LazyLoadFragment : Fragment() {

    internal var mView: View? = null
    internal var isPrepared: Boolean = false
    internal var isVisible: Boolean = false
    internal var Name:String ?=null
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mView == null) {
            mView = inflater!!.inflate(getLayoutId(), null)
            //已经初始化布局了
            isPrepared = true
            init()
        }
        return mView
    }

    open fun init() {
        if (!isPrepared || !isVisible) {
            return
        }
        // setUserVisibleHint、onHiddenChanged 不再触发loadData
        isPrepared = false

        lazyLoad()
    }

    /**
     * 如果是与ViewPager一起使用，调用的是setUserVisibleHint
     *
     * @param isVisibleToUser 是否显示出来了
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

    /**
     * 如果是通过FragmentTransaction的show和hide的方法来控制显示，调用的是onHiddenChanged.
     * 若是初始就show的Fragment 为了触发该事件 需要先hide再show
     *
     * @param hidden hidden True if the fragment is now hidden, false if it is not
     *               visible.
     */
    override fun onHiddenChanged(hidden: Boolean) {
        if (!hidden) {
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

    abstract fun getLayoutId(): Int

    abstract fun lazyLoad()

}