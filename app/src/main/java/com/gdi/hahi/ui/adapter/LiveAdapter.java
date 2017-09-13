package com.gdi.hahi.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gdi.hahi.R;
import com.gdi.hahi.callback.LiveVideoAllCallBack;
import com.gdi.hahi.callback.OnItemClickLitener;
import com.gdi.hahi.mvp.contract.HahiContract;
import com.gdi.hahi.mvp.model.bean.SimpleLiveBean;
import com.gdi.hahi.ui.view.LiveGSYVideoPlayer;

import java.util.List;



/**
 * Created by Administrator on 2017/8/2 0002.
 */

public class LiveAdapter extends RecyclerView.Adapter<LiveAdapter.ViewHolder> {

    private static final String TAG = "LiveAdapter";
    
    List<SimpleLiveBean> mList;
    Context mContext;
    OnItemClickLitener mOnItemClickLitener;
    int WindowWidth;
    int WindowHeight;

    ViewHolder fristViewHolder;

    public LiveAdapter(Context context, List<SimpleLiveBean> mList) {
        this.mContext = context;
        this.mList = mList;
        init();
    }

    private void init() {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        WindowWidth = wm.getDefaultDisplay().getWidth();
        WindowHeight = wm.getDefaultDisplay().getHeight();
//        Resources resources = mContext.getResources();
//        DisplayMetrics dm = resources.getDisplayMetrics();
//        WindowWidth= dm.widthPixels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_live, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return (mList != null) ? mList.size() : 0;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (mList != null) {
            SimpleLiveBean bean = mList.get(position);
            Glide.with(mContext).load(bean.getAvatar_url()).apply(new RequestOptions().circleCrop()).into(holder.iv_useravatar);
            holder.tv_username.setText(bean.getUser_name());

            holder.iv_img.setLayoutParams(setHeight(holder.iv_img, WindowWidth, WindowWidth));
            Glide.with(mContext).load(bean.getAvatar_url()).apply(new RequestOptions().centerCrop()).into(holder.iv_img);
            holder.gsy_player.setLayoutParams(setHeight(holder.gsy_player, (WindowWidth-50) / 3, WindowHeight / 3));
            holder.gsy_player.setUp(bean.getRtmp_pull_url(), false, null);


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    holder.gsy_player.startWindowFullscreen(mContext, true, true);
                    if (mOnItemClickLitener != null) {
                        mOnItemClickLitener.setOnItemClickLitener(view, position);
                    }
                }
            });


            holder.gsy_player.setStandardVideoAllCallBack(new LiveVideoAllCallBack(){
                @Override
                public void onPrepared(String url, Object... objects) {
                    holder.gsy_player.setVisibility(View.VISIBLE);
                    Log.i(TAG, "onPrepared: ");
                }
            });

            if (position==0){
                fristViewHolder = holder;
            }
        }
    }


    public ViewHolder getFristViewHolder(){
        return fristViewHolder;
    }


    private ViewGroup.LayoutParams setHeight(View view, int width, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (width != 0 && height != 0) {
            layoutParams.width = width;
            layoutParams.height = height;
        }
        return layoutParams;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

       public ImageView iv_useravatar;
        public TextView tv_username;
        TextView tv_context;
        ImageView iv_img;
        RelativeLayout rl_video;
       public LiveGSYVideoPlayer gsy_player;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_useravatar = (ImageView) itemView.findViewById(R.id.iv_useravatar);
            tv_username = (TextView) itemView.findViewById(R.id.tv_username);
            tv_context = (TextView) itemView.findViewById(R.id.tv_context);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            rl_video = (RelativeLayout) itemView.findViewById(R.id.rl_video);
            gsy_player = (LiveGSYVideoPlayer) itemView.findViewById(R.id.gsy_player);
        }
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
}
