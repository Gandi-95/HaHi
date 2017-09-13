package com.gdi.hahi.callback;

import android.util.Log;

import com.shuyu.gsyvideoplayer.listener.StandardVideoAllCallBack;
import com.shuyu.gsyvideoplayer.listener.VideoAllCallBack;

/**
 * Created by gandi on 2017/9/10 0010.
 */

public abstract class LiveVideoAllCallBack implements StandardVideoAllCallBack {
    private static final String TAG = "LiveVideoAllCallBack";
    @Override
    public void onPrepared(String url, Object... objects) {
        Log.i(TAG, "onPrepared: ");
    }

    @Override
    public void onClickStartIcon(String url, Object... objects) {
        Log.i(TAG, "onClickStartIcon: ");
    }

    @Override
    public void onClickStartError(String url, Object... objects) {
        Log.i(TAG, "onClickStartError: ");
    }

    @Override
    public void onClickStop(String url, Object... objects) {
        Log.i(TAG, "onClickStop: ");
    }

    @Override
    public void onClickStopFullscreen(String url, Object... objects) {
        Log.i(TAG, "onClickStopFullscreen: ");
    }

    @Override
    public void onClickResume(String url, Object... objects) {
        Log.i(TAG, "onClickResume: ");
    }

    @Override
    public void onClickResumeFullscreen(String url, Object... objects) {
        Log.i(TAG, "onClickResumeFullscreen: ");
    }

    @Override
    public void onClickSeekbar(String url, Object... objects) {
        Log.i(TAG, "onClickSeekbar: ");
    }

    @Override
    public void onClickSeekbarFullscreen(String url, Object... objects) {
        Log.i(TAG, "onClickSeekbarFullscreen: ");
    }

    @Override
    public void onAutoComplete(String url, Object... objects) {
        Log.i(TAG, "onAutoComplete: ");
    }

    @Override
    public void onEnterFullscreen(String url, Object... objects) {
        Log.i(TAG, "onEnterFullscreen: ");
    }

    @Override
    public void onQuitFullscreen(String url, Object... objects) {
        Log.i(TAG, "onQuitFullscreen: ");
    }

    @Override
    public void onQuitSmallWidget(String url, Object... objects) {
        Log.i(TAG, "onQuitSmallWidget: ");
    }

    @Override
    public void onEnterSmallWidget(String url, Object... objects) {
        Log.i(TAG, "onEnterSmallWidget: ");
    }

    @Override
    public void onTouchScreenSeekVolume(String url, Object... objects) {
        Log.i(TAG, "onTouchScreenSeekVolume: ");
    }

    @Override
    public void onTouchScreenSeekPosition(String url, Object... objects) {
        Log.i(TAG, "onTouchScreenSeekPosition: ");
    }

    @Override
    public void onTouchScreenSeekLight(String url, Object... objects) {
        Log.i(TAG, "onTouchScreenSeekLight: ");
    }

    @Override
    public void onPlayError(String url, Object... objects) {
        Log.i(TAG, "onPlayError: ");
    }

    @Override
    public void onClickStartThumb(String url, Object... objects) {
        Log.i(TAG, "onClickStartThumb: ");
    }

    @Override
    public void onClickBlank(String url, Object... objects) {
        Log.i(TAG, "onClickBlank: ");
    }

    @Override
    public void onClickBlankFullscreen(String url, Object... objects) {
        Log.i(TAG, "onClickBlankFullscreen: ");
    }
}
