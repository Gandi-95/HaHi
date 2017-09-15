package com.gdi.hahi.mvp.model

import android.content.Context

import com.gdi.hahi.mvp.contract.HahiContract
import com.gdi.hahi.mvp.model.bean.DataL
import com.gdi.hahi.mvp.model.bean.LData
import com.gdi.hahi.mvp.model.bean.LiveBean
import com.gdi.hahi.mvp.model.bean.LiveData
import com.gdi.hahi.network.Api
import com.gdi.hahi.network.RetrofitClient

import java.util.ArrayList

import io.reactivex.Observable

/**
 * Created by gandi on 2017/9/8 0008.
 */

class LiveModel(internal var device_id: String) : HahiContract.Model<LiveBean, List<LiveData>> {

    override fun convertToDoMain(data: LiveBean): List<LiveData>? {
        if (data == null)
            return null

        return data.data.map { convertLiveDataToDoMain(it) }
    }


    fun convertLiveDataToDoMain(data: LData): LiveData {
        with(data.data) {
            return LiveData(owner.nickname, owner.avatar_medium.url_list[0], owner.avatar_jpg.url_list[0], owner.city, stream_url.rtmp_pull_url)
        }
    }

    override fun loadData(context: Context, lastTime: Long): Observable<LiveBean> {
        val retrofitClient = RetrofitClient.getInstance(context, Api.LiveBaseUrl)
        val observable = retrofitClient.create<Api>(Api::class.java).getLiveData(device_id, 170, lastTime)
        return observable
    }
}
