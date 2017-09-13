package com.gdi.hahi.mvp.model;

import android.content.Context;

import com.gdi.hahi.mvp.contract.HahiContract;
import com.gdi.hahi.mvp.model.bean.LiveBean;
import com.gdi.hahi.mvp.model.bean.Neihan;
import com.gdi.hahi.mvp.model.bean.SimpleLiveBean;
import com.gdi.hahi.network.Api;
import com.gdi.hahi.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by gandi on 2017/9/8 0008.
 */

public class LiveModel implements HahiContract.Model<LiveBean,List<SimpleLiveBean>> {

    String device_id;

    public LiveModel(String device_id) {
        this.device_id = device_id;
    }

    @Override
    public List<SimpleLiveBean> convertToDoMain(LiveBean data) {
        List<SimpleLiveBean> list = new ArrayList<>();

        List<LiveBean.DataBeanX> datal =  data.getData();
        for (LiveBean.DataBeanX beans :datal){
            SimpleLiveBean lBean = new SimpleLiveBean();
            LiveBean.DataBeanX.DataBean bean = beans.getData();
            lBean.setUser_name(bean.getOwner().getNickname());
            lBean.setAvatar_url(bean.getOwner().getAvatar_medium().getUrl_list().get(0));
            lBean.setAvatar_jpg(bean.getOwner().getAvatar_jpg().getUrl_list().get(0));
            lBean.setCity(bean.getOwner().getCity());
            lBean.setRtmp_pull_url(bean.getStream_url().getRtmp_pull_url());
            list.add(lBean);
        }

        return list;
    }

    @Override
    public Observable<LiveBean> loadData(Context context, long lastTime) {
        RetrofitClient retrofitClient = RetrofitClient.getInstance(context, Api.LiveBaseUrl);
        Observable<LiveBean> observable = retrofitClient.create(Api.class).getLiveData(device_id,170,lastTime);
        return observable;
    }
}
