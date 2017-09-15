package com.gdi.hahi.base

import android.support.v4.app.Fragment

/**
 * Created by gandi on 2017/9/7 0007.
 */

abstract class BaseFragment : Fragment() {

    abstract fun smoothScrollToPosition(postion: Int)
}
