package com.gdi.hahi.mvp.contract;


import android.content.Context;

import com.gdi.hahi.base.BaseModel;
import com.gdi.hahi.base.BasePresenter;
import com.gdi.hahi.base.BaseView;

import java.util.Collection;

import io.reactivex.Observable;
import io.reactivex.Observer;

/**
 * Created by gandi on 2017/8/24 0024.
 */

public interface HahiContract {
    interface Model<T, V> extends BaseModel<T,V> {
        Observable<T> loadData(Context context,long lastTime);

    }

    interface View<T> extends BaseView {
        void setData(T data);
    }

    interface Presenter extends BasePresenter {
        void requestData(long lastTime);
    }


}
