package com.gdi.hahi.ui.view;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.gdi.hahi.R;
import com.shuyu.gsyvideoplayer.GSYPreViewManager;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

/**
 * Created by gandi on 2017/9/9 0009.
 */

public class LiveGSYVideoPlayer extends StandardGSYVideoPlayer {

    Context mContext;

    public LiveGSYVideoPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
        this.mContext = context;
        setBottomProgressBarHide();
    }

    public LiveGSYVideoPlayer(Context context) {
        super(context);
//        this.mContext = context;
//        setBottomProgressBarHide();
    }

    public LiveGSYVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        setBottomProgressBarHide();
    }

    public ViewGroup getBottomProgressBar(){
        return mBottomContainer;
    }


    public void setBottomProgressBarHide(){
        setVisibility(INVISIBLE);
        mBottomContainer.setVisibility(GONE);
        mProgressBar.setVisibility(GONE);
        mCurrentTimeTextView.setVisibility(GONE);
        mTotalTimeTextView.setVisibility(GONE);
        mBottomContainer.setBackgroundColor(ContextCompat.getColor(mContext,R.color.live_bottom_bg));
        mFullscreenButton.setVisibility(GONE);

        mStartButton.setVisibility(GONE);
        setShowFullAnimation(true);

    }

    public View getThumbImageView(){
        return mThumbImageView;
    }


    public void start(){
        startPlayLogic();
    }

    public void pause(){
        if (GSYVideoManager.instance().getMediaPlayer().isPlaying()){
            GSYVideoManager.instance().getMediaPlayer().pause();
            setVisibility(INVISIBLE);
        }
    }

    public void stop(){
        GSYVideoManager.instance().getMediaPlayer().stop();
    }

}
