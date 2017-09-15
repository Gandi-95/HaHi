package com.gdi.hahi.mvp.contract


import android.content.Context

import com.gdi.hahi.base.BaseModel
import com.gdi.hahi.base.BasePresenter
import com.gdi.hahi.base.BaseView
import com.gdi.hahi.mvp.model.bean.NeiHanData

import io.reactivex.Observable

/**
 * Created by gandi on 2017/8/24 0024.
 */

interface HahiContract {
    interface Model<T, V> : BaseModel<T, V> {
        fun loadData(context: Context, lastTime: Long): Observable<T>

    }

    interface View<T> : BaseView {
        fun setData(data: T?)
    }

    interface Presenter : BasePresenter {
        fun requestData(lastTime: Long)
    }


}
