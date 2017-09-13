package com.gdi.hahi.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.gdi.hahi.callback.OnItemClickLitener;
import com.gdi.hahi.mvp.model.bean.HahiBean;

import java.util.List;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * Created by Administrator on 2017/8/2 0002.
 */

public class NeihanAdapter extends RecyclerView.Adapter<NeihanAdapter.ViewHolder> {

    List<HahiBean> mList;
    Context mContext;
    OnItemClickLitener mOnItemClickLitener;
    int WindowWidth;

    public NeihanAdapter(Context context, List<HahiBean> mList) {
        this.mContext = context;
        this.mList = mList;
        init();
    }

    private void init() {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        WindowWidth = wm.getDefaultDisplay().getWidth();
//        Resources resources = mContext.getResources();
//        DisplayMetrics dm = resources.getDisplayMetrics();
//        WindowWidth= dm.widthPixels;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_neihan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return (mList != null) ? mList.size() : 0;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (mList != null) {
            HahiBean bean = mList.get(position);
            Glide.with(mContext).load(bean.getAvatar_url()).apply(new RequestOptions().circleCrop()).into(holder.iv_useravatar);
            holder.tv_username.setText(bean.getUser_name());
            if (!TextUtils.isEmpty(bean.getContext())){
                holder.tv_context.setText(bean.getContext());
            }else{
                holder.tv_context.setVisibility(View.GONE);
            }

            switch (bean.getMedia_type()) {
                case 0:
                    holder.iv_img.setVisibility(View.GONE);
                    holder.rl_video.setVisibility(View.GONE);
                    break;
                case 1:
                    holder.iv_img.setLayoutParams(setHeight(holder.iv_img, bean.getWidth(), bean.getHight()));
                    holder.iv_img.setVisibility(View.VISIBLE);
                    holder.rl_video.setVisibility(View.GONE);
                    Glide.with(mContext).load(bean.getMeida_url()).apply(new RequestOptions().centerCrop()).into(holder.iv_img);
                    break;
                case 2:
                    holder.iv_img.setLayoutParams(setHeight(holder.iv_img, bean.getWidth(), bean.getHight()));
                    holder.iv_img.setVisibility(View.VISIBLE);
                    holder.rl_video.setVisibility(View.GONE);
                    Glide.with(mContext).load(bean.getMeida_url()).apply(new RequestOptions().centerCrop()).into(holder.iv_img);
                    break;
                case 3:
                    holder.rl_video.setLayoutParams(setHeight(holder.rl_video, bean.getWidth(), bean.getHight()));
                    holder.gsy_player.setLayoutParams(setHeight(holder.gsy_player, bean.getWidth(), bean.getHight()));
                    holder.iv_img.setVisibility(View.GONE);
                    holder.rl_video.setVisibility(View.VISIBLE);
//                    bean.setMeida_url("http://pull-flv-l1-hs.pstatp.com/hudong/stream-6463304935256951565.flv");
                    holder.gsy_player.setUp(bean.getMeida_url(), JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL);
                    holder.gsy_player.backButton.setVisibility(View.GONE);
                    Glide.with(mContext).load(bean.getThumbImage()).apply(new RequestOptions().centerCrop()).into(holder.gsy_player.thumbImageView);
                    break;
            }


            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mOnItemClickLitener != null) {
                        mOnItemClickLitener.setOnItemClickLitener(view, position);
                    }
                }
            });
        }
    }


    private ViewGroup.LayoutParams setHeight(View view, int width, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        int lheight = 0;
        if (width!=0&&height!=0){
            lheight = height * WindowWidth / width;
            if (lheight>WindowWidth){
                lheight = WindowWidth;
            }
            layoutParams.height = lheight;
        }
        return layoutParams;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_useravatar;
        TextView tv_username;
        TextView tv_context;
        ImageView iv_img;
        RelativeLayout rl_video;
        JCVideoPlayerStandard gsy_player;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_useravatar = (ImageView) itemView.findViewById(R.id.iv_useravatar);
            tv_username = (TextView) itemView.findViewById(R.id.tv_username);
            tv_context = (TextView) itemView.findViewById(R.id.tv_context);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_img);
            rl_video = (RelativeLayout) itemView.findViewById(R.id.rl_video);
            gsy_player = (JCVideoPlayerStandard) itemView.findViewById(R.id.gsy_player);
        }
    }

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
}
