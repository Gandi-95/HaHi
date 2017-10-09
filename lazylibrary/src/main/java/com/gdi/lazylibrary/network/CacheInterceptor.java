package com.gdi.lazylibrary.network;



import android.content.Context;

import com.gdi.lazylibrary.utils.NetworkUtils;

import java.io.IOException;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

    /**
     * Created by gandi on 2017/8/26 0026.
     */

    public class CacheInterceptor implements Interceptor {

        Context mContext;

        public CacheInterceptor(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (NetworkUtils.isNetConneted(mContext)) {
                Response response = chain.proceed(request);
                int max = 60;
                CacheControl cacheControl = request.cacheControl();
                return response.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control").header("Cache-Control", "public, max-age=" + max).build();
            } else {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
                Response response = chain.proceed(request);
                //set cahe times is 3 days
                int maxStale = 60 * 60 * 24 * 3;
                return response.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control").header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale).build();
            }
        }
    }

