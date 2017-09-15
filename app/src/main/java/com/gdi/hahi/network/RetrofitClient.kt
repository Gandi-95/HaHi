package com.gdi.hahi.network

import android.content.Context
import android.util.Log

import java.io.File
import java.util.concurrent.TimeUnit

import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.properties.Delegates

/**
 * Created by gandi on 2017/8/24 0024.
 */

class RetrofitClient(internal var mContext: Context, baseUrl: String) {
    internal var httpCacheDirectory: File? = null
    internal var cache: Cache? = null
    internal var DEFAULT_TIMEOUT: Long? = 20L


    companion object {
        private val TAG = "RetrofitClient"

        var instance: RetrofitClient? = null
        internal var mBaseUrl: String = ""
        internal var retrofit: Retrofit by Delegates.notNull()
        internal var okHttpClient: OkHttpClient? = null


        fun getInstance(context: Context, baseUrl: String): RetrofitClient {
            if (instance == null) {
                instance = RetrofitClient(context, baseUrl)
            }
            if (baseUrl != mBaseUrl) {
                mBaseUrl = baseUrl
                setRetrofitBaseUrl()
            }
            return instance!!
        }
        internal fun setRetrofitBaseUrl() {
            retrofit = retrofit.newBuilder().baseUrl(mBaseUrl).build()
//            retrofit = Retrofit.Builder()
//                    .client(okHttpClient!!)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                    .baseUrl(mBaseUrl)
//                    .build()
        }

    }



    init {
        mBaseUrl = baseUrl
        instance = this

        init()
    }

    internal fun init() {
        if (httpCacheDirectory == null) {
            httpCacheDirectory = File(mContext.cacheDir, "app_cache")
        }

        try {
            if (cache == null) {
                cache = Cache(httpCacheDirectory!!, (10 * 1024 * 1024).toLong())
            }
        } catch (e: Exception) {
            Log.e(TAG, "Could not create http cache", e)
        }

        okHttpClient = OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(CacheInterceptor(mContext))
                .addNetworkInterceptor(CacheInterceptor(mContext))
                .connectTimeout(DEFAULT_TIMEOUT!!, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT!!, TimeUnit.SECONDS)
                .build()


        retrofit = Retrofit.Builder()
                .client(okHttpClient!!)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(mBaseUrl)
                .build()

    }


    fun <T> create(service: Class<T>?): T {
        if (service == null) {
            throw RuntimeException("Api service is null!")
        }
        return retrofit.create(service)
    }




}
