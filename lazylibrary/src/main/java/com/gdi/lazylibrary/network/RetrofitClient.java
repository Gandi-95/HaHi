package com.gdi.lazylibrary.network;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gandi on 2017/9/28 0028.
 */

public class RetrofitClient {
    private static final String TAG = "RetrofitClient";

    public static RetrofitClient instance;
    Context mContext;
    String mBaseUrl;
    Retrofit retrofit;
    OkHttpClient okHttpClient = null;
    File httpCacheDirectory = null;
    Cache cache = null;
    Long DEFAULT_TIMEOUT = 20l;


    public static RetrofitClient getInstance(Context context) {
        if (instance == null) {
            instance = new RetrofitClient(context);
        }
        return instance;
    }


    public RetrofitClient(Context context) {
        this.mContext = context;
//        this.mBaseUrl = baseUrl;
        instance = this;

        init();
    }

    void init() {
        if (mBaseUrl==null) return;

        if (httpCacheDirectory == null) {
            httpCacheDirectory = new File(mContext.getCacheDir(), "app_cache");
        }

        try {
            if (cache == null) {
                cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);
            }
        } catch (Exception e) {
            Log.e(TAG, "Could not create http cache", e);
        }

        okHttpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(new CacheInterceptor(mContext))
                .addNetworkInterceptor(new CacheInterceptor(mContext))
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();


        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(mBaseUrl)
                .build();

    }

    public RetrofitClient baseUrl(String baseUrl) {
        if (retrofit==null){
            mBaseUrl = baseUrl;
            init();
        }else{
            if (!baseUrl.equals(mBaseUrl)) {
                mBaseUrl = baseUrl;
                retrofit = retrofit.newBuilder().baseUrl(baseUrl).build();
            }
        }
        return this;
    }

    public <T> T create(Class<T> service) {
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return retrofit.create(service);
    }
}
