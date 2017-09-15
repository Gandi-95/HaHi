package com.gdi.hahi.callback

import android.util.Log

import com.shuyu.gsyvideoplayer.listener.StandardVideoAllCallBack
import com.shuyu.gsyvideoplayer.listener.VideoAllCallBack

/**
 * Created by gandi on 2017/9/10 0010.
 */

abstract class LiveVideoAllCallBack : StandardVideoAllCallBack {
    override fun onPrepared(url: String, vararg objects: Any) {
        Log.i(TAG, "onPrepared: ")
    }

    override fun onClickStartIcon(url: String, vararg objects: Any) {
        Log.i(TAG, "onClickStartIcon: ")
    }

    override fun onClickStartError(url: String, vararg objects: Any) {
        Log.i(TAG, "onClickStartError: ")
    }

    override fun onClickStop(url: String, vararg objects: Any) {
        Log.i(TAG, "onClickStop: ")
    }

    override fun onClickStopFullscreen(url: String, vararg objects: Any) {
        Log.i(TAG, "onClickStopFullscreen: ")
    }

    override fun onClickResume(url: String, vararg objects: Any) {
        Log.i(TAG, "onClickResume: ")
    }

    override fun onClickResumeFullscreen(url: String, vararg objects: Any) {
        Log.i(TAG, "onClickResumeFullscreen: ")
    }

    override fun onClickSeekbar(url: String, vararg objects: Any) {
        Log.i(TAG, "onClickSeekbar: ")
    }

    override fun onClickSeekbarFullscreen(url: String, vararg objects: Any) {
        Log.i(TAG, "onClickSeekbarFullscreen: ")
    }

    override fun onAutoComplete(url: String, vararg objects: Any) {
        Log.i(TAG, "onAutoComplete: ")
    }

    override fun onEnterFullscreen(url: String, vararg objects: Any) {
        Log.i(TAG, "onEnterFullscreen: ")
    }

    override fun onQuitFullscreen(url: String, vararg objects: Any) {
        Log.i(TAG, "onQuitFullscreen: ")
    }

    override fun onQuitSmallWidget(url: String, vararg objects: Any) {
        Log.i(TAG, "onQuitSmallWidget: ")
    }

    override fun onEnterSmallWidget(url: String, vararg objects: Any) {
        Log.i(TAG, "onEnterSmallWidget: ")
    }

    override fun onTouchScreenSeekVolume(url: String, vararg objects: Any) {
        Log.i(TAG, "onTouchScreenSeekVolume: ")
    }

    override fun onTouchScreenSeekPosition(url: String, vararg objects: Any) {
        Log.i(TAG, "onTouchScreenSeekPosition: ")
    }

    override fun onTouchScreenSeekLight(url: String, vararg objects: Any) {
        Log.i(TAG, "onTouchScreenSeekLight: ")
    }

    override fun onPlayError(url: String, vararg objects: Any) {
        Log.i(TAG, "onPlayError: ")
    }

    override fun onClickStartThumb(url: String, vararg objects: Any) {
        Log.i(TAG, "onClickStartThumb: ")
    }

    override fun onClickBlank(url: String, vararg objects: Any) {
        Log.i(TAG, "onClickBlank: ")
    }

    override fun onClickBlankFullscreen(url: String, vararg objects: Any) {
        Log.i(TAG, "onClickBlankFullscreen: ")
    }

    companion object {
        private val TAG = "LiveVideoAllCallBack"
    }
}
