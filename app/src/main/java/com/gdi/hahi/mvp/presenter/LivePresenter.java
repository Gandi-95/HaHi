package com.gdi.hahi.mvp.presenter;

import android.content.Context;

import com.gdi.hahi.mvp.contract.HahiContract;
import com.gdi.hahi.mvp.model.LiveModel;
import com.gdi.hahi.mvp.model.bean.LiveBean;
import com.gdi.hahi.mvp.model.bean.Neihan;
import com.gdi.hahi.network.Api;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by gandi on 2017/9/8 0008.
 */

public class LivePresenter implements HahiContract.Presenter {

    LiveModel model;
    HahiContract.View mView;
    Context context;

    public LivePresenter(HahiContract.View mView, Context context) {
        this.mView = mView;
        this.context = context;
        model = new LiveModel(Api.device_id);
    }

    @Override
    public void requestData(long lastTime) {
        Observable<LiveBean> observable = model.loadData(context, lastTime);
        observable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<LiveBean>() {
                    @Override
                    public void accept(LiveBean liveBean) throws Exception {
                        mView.setData(model.convertToDoMain(liveBean));
                    }
                });
    }
}
