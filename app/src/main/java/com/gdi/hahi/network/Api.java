package com.gdi.hahi.network;

import com.gdi.hahi.mvp.model.bean.LiveBean;
import com.gdi.hahi.mvp.model.bean.Neihan;

import io.reactivex.Observable;
import io.reactivex.Observer;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by gandi on 2017/8/24 0024.
 */

public interface Api {

    String BaseUrl = "http://lf.snssdk.com/neihan/";
    String LiveBaseUrl = "http://hotsoon.snssdk.com/";
    String device_id = "32613520945";


    @GET("stream/mix/v1/?content_type=-101")
    Observable<Neihan> getNeiHanData();


    @GET("stream/mix/v1/")
    Observable<Neihan> getNeiHanData(@Query("content_type") String content_type,@Query("device_id") String device_id);

    @GET("stream/mix/v1/")
    Observable<Neihan> getNeiHanData(@Query("content_type") String content_type,@Query("device_id") String device_id,@Query("min_time") long min_time,@Query("am_loc_time") long am_loc_time);

    @GET("hotsoon/feed/")
    Observable<LiveBean> getLiveData(@Query("device_id") String device_id,@Query("live_sdk_version") int live_sdk_version,@Query("min_time") long min_time);


}
