package com.gdi.hahi.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
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
import com.gdi.hahi.callback.LiveVideoAllCallBack
import com.gdi.hahi.callback.OnItemClickLitener
import com.gdi.hahi.mvp.model.bean.LiveData
import com.gdi.hahi.ui.view.LiveGSYVideoPlayer
//import kotlin.properties.Delegates
//import kotlinx.android.synthetic.main.item_live.*


/**
 * Created by Administrator on 2017/8/2 0002.
 */

class LiveAdapter(internal var mContext: Context, internal var mList: List<LiveData>?) : RecyclerView.Adapter<LiveAdapter.ViewHolder>() {
    internal var mOnItemClickLitener: OnItemClickLitener? = null
    internal var WindowWidth: Int = 0
    internal var WindowHeight: Int = 0

    var fristViewHolder: ViewHolder? = null


    init {
        val wm = mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        WindowWidth = wm.defaultDisplay.width
        WindowHeight = wm.defaultDisplay.height
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_live, parent, false)
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

            holder.iv_img.layoutParams = setHeight(holder.iv_img, WindowWidth, WindowWidth)
            Glide.with(mContext).load(bean.avatar_url).apply(RequestOptions().centerCrop()).into(holder.iv_img)
            holder.gsy_player.layoutParams = setHeight(holder.gsy_player, (WindowWidth - 50) / 3, WindowHeight / 3)
            holder.gsy_player.setUp(bean.rtmp_pull_url, false, null)
            holder.gsy_player.pause()


            holder.itemView.setOnClickListener { view ->
                holder.gsy_player.startWindowFullscreen(mContext, true, true)
                if (mOnItemClickLitener != null) {
                    mOnItemClickLitener!!.setOnItemClickLitener(view, position)
                }
            }

            holder.gsy_player.setStandardVideoAllCallBack(object : LiveVideoAllCallBack() {
                override fun onPrepared(url: String, vararg objects: Any) {
                    holder.gsy_player.visibility = View.VISIBLE
                    Log.i(TAG, "onPrepared: ")
                }
            })

            if (position == 0) {
                fristViewHolder = holder
            }
        }
    }


    private fun setHeight(view: View, width: Int, height: Int): ViewGroup.LayoutParams {
        val layoutParams = view.layoutParams
        if (width != 0 && height != 0) {
            layoutParams.width = width
            layoutParams.height = height
        }
        return layoutParams
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var iv_useravatar: ImageView
        var tv_username: TextView
        var iv_img: ImageView
        var gsy_player: LiveGSYVideoPlayer

        init {
            iv_useravatar = itemView.findViewById(R.id.iv_useravatar) as ImageView
            tv_username = itemView.findViewById(R.id.tv_username) as TextView
            iv_img = itemView.findViewById(R.id.iv_img) as ImageView
            gsy_player = itemView.findViewById(R.id.gsy_player) as LiveGSYVideoPlayer
        }
    }

    fun setOnItemClickLitener(mOnItemClickLitener: OnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener
    }

    companion object {

        private val TAG = "LiveAdapter"
    }
}
