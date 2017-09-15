package com.gdi.hahi.mvp.presenter

import android.content.Context

import com.gdi.hahi.mvp.contract.HahiContract
import com.gdi.hahi.mvp.model.NeihanModel
import com.gdi.hahi.mvp.model.bean.NeiHanData
import com.gdi.hahi.network.Api

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlin.properties.Delegates

/**
 * Created by gandi on 2017/8/24 0024.
 */

class NeihanPresenter(internal var mView: HahiContract.View<List<NeiHanData>>, internal var context: Context) : HahiContract.Presenter {

    internal var hotModel: NeihanModel by Delegates.notNull()

    fun setContentType(content_type: String) {
        hotModel = NeihanModel(content_type, Api.device_id)
    }


    override fun requestData(lastTime: Long) {
        val observable = hotModel.loadData(context, lastTime)
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { neihan -> mView.setData(hotModel.convertToDoMain(neihan)) }
    }
}
