package com.gdi.hahi.base

import com.gdi.hahi.mvp.model.bean.LiveData
import com.gdi.hahi.mvp.model.bean.NeiHanData

/**
 * Created by gandi on 2017/8/31 0031.
 */

interface BaseModel<T, V> {
    fun convertToDoMain(data: T): V?
}
