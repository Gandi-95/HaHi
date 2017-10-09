package com.gdi.hahi.mvp.model

import android.content.Context

import com.gdi.hahi.mvp.contract.HahiContract
import com.gdi.hahi.mvp.model.bean.DataN
import com.gdi.hahi.mvp.model.bean.NeiHanData
import com.gdi.hahi.mvp.model.bean.NeiHanBean
import com.gdi.hahi.api.Api
import com.gdi.lazylibrary.network.RetrofitClient

import io.reactivex.Observable

/**
 * Created by gandi on 2017/8/24 0024.
 */

class NeihanModel(internal var content_type: String, internal var device_id: String) : HahiContract.Model<NeiHanBean, List<NeiHanData>> {

    override fun convertToDoMain(neiHanBean: NeiHanBean): List<NeiHanData>? {
//        val hahiList = ArrayList<NeiHanData>()
        if (neiHanBean.data == null) {
            return null
        }
        return neiHanBean.data.data.map { convertNeiHanDataToDoMain(it) }
    }

    fun convertNeiHanDataToDoMain(dataN: DataN): NeiHanData {
        var group = dataN.group
        var is_gif: Int = 0
        var is_video: Int = 0
        var meida_url: String=""
        var width: Int = 0
        var hight: Int = 0
        var duration: Int = 0
        var play_count: Int = 0
        var thumbImage: String = ""
        
        with(group){
            when (media_type) {
                1 -> {
                    meida_url = large_image.url_list[0].url
                }
                2 -> {
                    meida_url = large_image.url_list[0].url
                    is_gif = is_gif
                    width = large_image.width
                    hight = large_image.height
                }
                3 -> {
                    meida_url = mp4_url
                    is_video = is_video
                    width = video360p.width
                    hight = video360p.height
                    duration = duration.toInt()
                    thumbImage = large_cover.url_list[0].url
                    play_count = play_count
                }
            }
            return NeiHanData(user.name, user.avatar_url, content, media_type, is_gif, is_video, meida_url!!, width, hight, duration, play_count, thumbImage!!)
        }
    }




    override fun loadData(context: Context, lastTime: Long): Observable<NeiHanBean> {
        val retrofitClient = RetrofitClient.getInstance(context).baseUrl(Api.BaseUrl)
        val observable = retrofitClient.create<Api>(Api::class.java).getNeiHanData(content_type, device_id, lastTime, System.currentTimeMillis())
        return observable
    }

    companion object {

        private val TAG = "NeihanModel"
    }
}



