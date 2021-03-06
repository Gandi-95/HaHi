package com.gdi.hahi.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

/**
 * Created by gandi on 2017/8/27 0027.
 */

object NetworkUtils {

    fun isNetConneted(context: Context): Boolean {
        val connectManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectManager.activeNetworkInfo
        if (networkInfo == null) {
            return false
        } else {
            return networkInfo.isAvailable && networkInfo.isConnected
        }
    }
}
