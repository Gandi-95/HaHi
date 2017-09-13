package com.gdi.hahi.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by gandi on 2017/8/27 0027.
 */

public class NetworkUtils {

    public static Boolean isNetConneted(Context context) {
        ConnectivityManager connectManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectManager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return false;
        } else {
            return networkInfo.isAvailable() && networkInfo.isConnected();
        }
    }
}
