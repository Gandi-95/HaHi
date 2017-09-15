package com.gdi.hahi.ui.view

import android.content.Context
import android.os.Build
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar

import com.gdi.hahi.R
import com.shuyu.gsyvideoplayer.GSYPreViewManager
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer
import kotlin.properties.Delegates

/**
 * Created by gandi on 2017/9/9 0009.
 */

class LiveGSYVideoPlayer : StandardGSYVideoPlayer {

    var mContext: Context by Delegates.notNull()

    constructor(context: Context, fullFlag: Boolean?) : super(context, fullFlag!!) {
//        mContext = context
        setBottomProgressBarHide()
    }

    constructor(context: Context) : super(context) {
        //        this.mContext = context;
        //        setBottomProgressBarHide();
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
//        mContext = context
        setBottomProgressBarHide()
    }

    val bottomProgressBar: ViewGroup
        get() = mBottomContainer


    fun setBottomProgressBarHide() {
        visibility = View.INVISIBLE
        mBottomContainer.visibility = View.GONE
        mProgressBar.visibility = View.GONE
        mCurrentTimeTextView.visibility = View.GONE
        mTotalTimeTextView.visibility = View.GONE
        mBottomContainer.setBackgroundColor(resources.getColor(R.color.live_bottom_bg))
        mFullscreenButton.visibility = View.GONE

        mStartButton.visibility = View.GONE
        isShowFullAnimation = true

    }

    val thumbImageView: View
        get() = mThumbImageView


    fun start() {
        startPlayLogic()
    }

    fun pause() {
        if (GSYVideoManager.instance().mediaPlayer.isPlaying) {
            GSYVideoManager.instance().mediaPlayer.pause()
            visibility = View.INVISIBLE
        }
    }

    fun stop() {
        GSYVideoManager.instance().mediaPlayer.stop()
    }

}
