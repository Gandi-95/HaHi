package com.gdi.hahi.mvp.presenter

import android.content.Context

import com.gdi.hahi.mvp.contract.HahiContract
import com.gdi.hahi.mvp.model.LiveModel
import com.gdi.hahi.mvp.model.bean.LiveData
import com.gdi.hahi.api.Api

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by gandi on 2017/9/8 0008.
 */

class LivePresenter(internal var mView: HahiContract.View<List<LiveData>>, internal var context: Context) : HahiContract.Presenter {

    internal var model: LiveModel

    init {
        model = LiveModel(Api.device_id)
    }

    override fun requestData(lastTime: Long) {
        val observable = model.loadData(context, lastTime)
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { liveBean -> mView.setData(model.convertToDoMain(liveBean)) }
    }
}
