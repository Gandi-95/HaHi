package com.gdi.hahi.network;

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
 * Created by gandi on 2017/8/24 0024.
 */

public class RetrofitClient {

    private static final String TAG = "RetrofitClient";

    public static RetrofitClient instance;
    Context mContext;
    static String mBaseUrl;
    static Retrofit retrofit;
    static OkHttpClient okHttpClient = null;
    File httpCacheDirectory = null;
    Cache cache = null;
    Long DEFAULT_TIMEOUT  = 20l;


    public static RetrofitClient getInstance(Context context,String baseUrl){
        if (instance==null){
            instance = new RetrofitClient(context,baseUrl);
        }
        if (!baseUrl.equals(mBaseUrl)){
            mBaseUrl = baseUrl;
            setRetrofitBaseUrl();
        }
        return instance;
    }


    public RetrofitClient(Context context,String baseUrl) {
        this.mContext = context;
        this.mBaseUrl = baseUrl;
        instance = this;

        init();
    }

    void init(){
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


        setRetrofitBaseUrl();

    }

   static void setRetrofitBaseUrl(){
       retrofit = new Retrofit.Builder()
               .client(okHttpClient)
               .addConverterFactory(GsonConverterFactory.create())
               .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
               .baseUrl(mBaseUrl)
               .build();
    }


    public <T> T create( Class<T> service){
        if (service == null) {
            throw new RuntimeException("Api service is null!");
        }
        return retrofit.create(service);
    }


}
