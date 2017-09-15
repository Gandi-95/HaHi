package com.gdi.hahi.network

import android.content.Context

import com.gdi.hahi.utils.NetworkUtils

import java.io.IOException

import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Created by gandi on 2017/8/26 0026.
 */

class CacheInterceptor(internal var mContext: Context) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (NetworkUtils.isNetConneted(mContext)) {
            val response = chain.proceed(request)
            val max = 60
            val cacheControl = request.cacheControl()
            return response.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control").header("Cache-Control", "public, max-age=" + max).build()
        } else {
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
            val response = chain.proceed(request)
            //set cahe times is 3 days
            val maxStale = 60 * 60 * 24 * 3
            return response.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control").header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale).build()
        }
    }
}
