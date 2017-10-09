package com.gdi.hahi.base

/**
 * Created by gandi on 2017/9/7 0007.
 */

abstract class BaseFragment : LazyLoadFragment() {

    abstract fun smoothScrollToPosition(postion: Int)
}
