package com.gdi.hahi.api

import com.gdi.hahi.mvp.model.bean.LiveBean
import com.gdi.hahi.mvp.model.bean.NeiHanBean

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by gandi on 2017/8/24 0024.
 */

interface Api {


    @get:GET("stream/mix/v1/?content_type=-101")
    val neiHanData: Observable<NeiHanBean>


    @GET("stream/mix/v1/")
    fun getNeiHanData(@Query("content_type") content_type: String, @Query("device_id") device_id: String): Observable<NeiHanBean>

    @GET("stream/mix/v1/")
    fun getNeiHanData(@Query("content_type") content_type: String, @Query("device_id") device_id: String, @Query("min_time") min_time: Long, @Query("am_loc_time") am_loc_time: Long): Observable<NeiHanBean>

    @GET("hotsoon/feed/")
    fun getLiveData(@Query("device_id") device_id: String, @Query("live_sdk_version") live_sdk_version: Int, @Query("min_time") min_time: Long): Observable<LiveBean>

    companion object {

        val BaseUrl = "http://lf.snssdk.com/neihan/"
        val LiveBaseUrl = "http://hotsoon.snssdk.com/"
        val device_id = "32613520945"
    }


}
