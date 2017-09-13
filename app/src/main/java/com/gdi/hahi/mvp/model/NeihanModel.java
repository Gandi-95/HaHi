package com.gdi.hahi.mvp.model;

import android.content.Context;
import android.util.Log;

import com.gdi.hahi.mvp.contract.HahiContract;
import com.gdi.hahi.mvp.model.bean.HahiBean;
import com.gdi.hahi.mvp.model.bean.Neihan;
import com.gdi.hahi.network.Api;
import com.gdi.hahi.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by gandi on 2017/8/24 0024.
 */

public class NeihanModel implements HahiContract.Model<Neihan, List<HahiBean>> {

    private static final String TAG = "NeihanModel";
    String content_type;
    String device_id;

    public NeihanModel(String content_type, String device_id) {
        this.content_type = content_type;
        this.device_id = device_id;
    }

    @Override
    public List<HahiBean> convertToDoMain(Neihan data) {
        List<HahiBean> hahiList = new ArrayList<>();
        if (data.getData()==null){
            return hahiList;
        }
        List<Neihan.DataBeanX.DataBean> data1 = data.getData().getData();
        for (Neihan.DataBeanX.DataBean dataBean : data1) {
            HahiBean hahiBean = new HahiBean();
            hahiBean.setUser_name(dataBean.getGroup().getUser().getName());
            hahiBean.setAvatar_url(dataBean.getGroup().getUser().getAvatar_url());
            hahiBean.setContext(dataBean.getGroup().getContent());
            int meida_type = dataBean.getGroup().getMedia_type();
            switch (meida_type) {
                case 0:
                    break;
                case 1:
                    hahiBean.setMeida_url(dataBean.getGroup().getLarge_image().getUrl_list().get(0).getUrl());
                    break;
                case 2:
                    hahiBean.setMeida_url(dataBean.getGroup().getLarge_image().getUrl_list().get(0).getUrl());
                    hahiBean.setIs_gif(dataBean.getGroup().getIs_gif());
                    hahiBean.setWidth(dataBean.getGroup().getLarge_image().getWidth());
                    hahiBean.setHight(dataBean.getGroup().getLarge_image().getHeight());
                    break;
                case 3:
                    hahiBean.setMeida_url(dataBean.getGroup().getMp4_url());
                    hahiBean.setIs_video(dataBean.getGroup().getIs_video());
                    hahiBean.setWidth(dataBean.getGroup().get_$360p_video().getWidth());
                    hahiBean.setHight(dataBean.getGroup().get_$360p_video().getHeight());
                    hahiBean.setDuration((int) dataBean.getGroup().getDuration());
                    hahiBean.setThumbImage(dataBean.getGroup().getLarge_cover().getUrl_list().get(0).getUrl());
                    hahiBean.setPlay_count(dataBean.getGroup().getPlay_count());
                    break;
            }
            hahiBean.setMedia_type(meida_type);
            Log.i(TAG, "convertToDoMain: " + hahiBean.toString());
            hahiList.add(hahiBean);
        }

        return hahiList;
    }

    @Override
    public Observable<Neihan> loadData(Context context,long lastTime) {
        RetrofitClient retrofitClient = RetrofitClient.getInstance(context, Api.BaseUrl);
        Observable<Neihan> observable = retrofitClient.create(Api.class).getNeiHanData(content_type,device_id,lastTime,System.currentTimeMillis());
        return observable;
    }
}



