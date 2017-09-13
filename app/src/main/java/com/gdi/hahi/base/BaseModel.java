package com.gdi.hahi.base;

import com.gdi.hahi.mvp.model.bean.Neihan;

/**
 * Created by gandi on 2017/8/31 0031.
 */

public interface BaseModel<T,V> {
    V convertToDoMain(T data);
}
