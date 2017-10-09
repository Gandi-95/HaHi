package com.gdi.hahi.ui.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gdi.hahi.R
import com.gdi.hahi.callback.OnItemClickLitener
import com.gdi.hahi.mvp.model.bean.NeiHanData
import com.gdi.hahi.ui.activity.PhotoViewActivity
import com.shuyu.gsyvideoplayer.utils.ListVideoUtil
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer


/**
 * Created by Administrator on 2017/8/2 0002.
 */

class NeihanAdapter(internal var mContext: Context, internal var mList: List<NeiHanData>?) : RecyclerView.Adapter<NeihanAdapter.ViewHolder>() {
    private var mOnItemClickLitener: OnItemClickLitener? = null
    private var WindowWidth: Int = 0
    private var viewHolderList: MutableList<RecyclerView.ViewHolder>? = null

    private var viewHolderMap: MutableMap<Int, ViewHolder>? = null

    init {
        val wm = mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        WindowWidth = wm.defaultDisplay.width
        viewHolderList = ArrayList()
        viewHolderMap = HashMap()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_neihan, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (mList != null) mList!!.size else 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (mList != null) {
            val bean = mList!![position]
            Glide.with(mContext).load(bean.avatar_url).apply(RequestOptions().circleCrop()).into(holder.iv_useravatar)
            holder.tv_username.text = bean.user_name
            if (!TextUtils.isEmpty(bean.context)) {
                holder.tv_context.text = bean.context
            } else {
                holder.tv_context.visibility = View.GONE
            }

            when (bean.media_type) {
                0 -> {
                    holder.iv_img.visibility = View.GONE
                    holder.rl_video.visibility = View.GONE
                }
                1 -> {
                    holder.iv_img.layoutParams = setHeight(holder.iv_img, bean.width, bean.hight)
                    holder.iv_img.visibility = View.VISIBLE
                    holder.rl_video.visibility = View.GONE
                    Glide.with(mContext).load(bean.meida_url).apply(RequestOptions().centerCrop()).into(holder.iv_img)
                }
                2 -> {
                    holder.iv_img.layoutParams = setHeight(holder.iv_img, bean.width, bean.hight)
                    holder.iv_img.visibility = View.VISIBLE
                    holder.rl_video.visibility = View.GONE
                    Glide.with(mContext).load(bean.meida_url).apply(RequestOptions().centerCrop()).into(holder.iv_img)
                }
                3 -> {
                    holder.rl_video.layoutParams = setHeight(holder.rl_video, bean.width, bean.hight)
                    holder.iv_img.visibility = View.GONE
                    holder.rl_video.visibility = View.VISIBLE
                    holder.gsy_player.setUp(bean.meida_url, false, null)
                    holder.gsy_player.backButton.visibility = View.GONE

                    //添加视预览图
                    var iv_thumb = ImageView(mContext)
                    Glide.with(mContext).asDrawable().load(bean.thumbImage).apply(RequestOptions().centerCrop()).into(iv_thumb)
                    holder.gsy_player.setThumbImageView(iv_thumb)

                    //全屏
                    holder.gsy_player.fullscreenButton.setOnClickListener {
                        //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                        holder.gsy_player.startWindowFullscreen(mContext, true, true);
                    }

                }
            }
            //查看大图
            holder.iv_img.setOnClickListener {
                val intent = PhotoViewActivity.newUrlIntent(mContext,bean.meida_url)
                mContext.startActivity(intent)
            }

            holder.itemView.setOnClickListener { view ->
                if (mOnItemClickLitener != null) {
                    mOnItemClickLitener!!.setOnItemClickLitener(view, position)
                }
            }

            viewHolderMap?.let { viewHolderMap!!.put(position, holder) }
        }
    }


    private fun setHeight(view: View, width: Int, height: Int): ViewGroup.LayoutParams {
        val layoutParams = view.layoutParams
        var lheight = 0
        if (width != 0 && height != 0) {
            lheight = height * WindowWidth / width
            if (lheight > WindowWidth) {
                lheight = WindowWidth
            }
            layoutParams.height = lheight
        }
        return layoutParams
    }

     fun getViewHolder(postion: Int): ViewHolder? {
        if (viewHolderMap!!.get(postion)!==null){
            return viewHolderMap!!.get(postion)
        }
        return null
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var iv_useravatar: ImageView
        var tv_username: TextView
        var tv_context: TextView
        var iv_img: ImageView
        var rl_video: RelativeLayout
        var gsy_player: StandardGSYVideoPlayer

        init {
            iv_useravatar = itemView.findViewById(R.id.iv_useravatar) as ImageView
            tv_username = itemView.findViewById(R.id.tv_username) as TextView
            tv_context = itemView.findViewById(R.id.tv_context) as TextView
            iv_img = itemView.findViewById(R.id.iv_img) as ImageView
            rl_video = itemView.findViewById(R.id.rl_video) as RelativeLayout
            gsy_player = itemView.findViewById(R.id.gsy_player) as StandardGSYVideoPlayer
        }
    }

    fun setOnItemClickLitener(mOnItemClickLitener: OnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener
    }
}
