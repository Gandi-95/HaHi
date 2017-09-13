package com.gdi.hahi.mvp.presenter;

import android.content.Context;

import com.gdi.hahi.mvp.contract.HahiContract;
import com.gdi.hahi.mvp.model.NeihanModel;
import com.gdi.hahi.mvp.model.bean.Neihan;
import com.gdi.hahi.network.Api;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by gandi on 2017/8/24 0024.
 */

public class NeihanPresenter implements HahiContract.Presenter {

    NeihanModel hotModel;
    HahiContract.View mView;
    Context context;

    public NeihanPresenter(HahiContract.View mView, Context context) {

        this.mView = mView;
        this.context = context;
    }

    public void setContentType(String content_type) {
        hotModel = new NeihanModel(content_type, Api.device_id);
    }


    @Override
    public void requestData(long lastTime) {
        Observable<Neihan> observable = hotModel.loadData(context, lastTime);
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Neihan>() {
                    @Override
                    public void accept(Neihan neihan) throws Exception {
                        mView.setData(hotModel.convertToDoMain(neihan));
                    }
                });
    }
}
