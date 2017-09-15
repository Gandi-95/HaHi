package com.gdi.hahi.mvp.model.bean

/**
 * Created by gandi on 2017/9/6 0006.
 */


data class NeiHanData(var user_name: String, var avatar_url: String, var context: String, var media_type: Int, var is_gif: Int, var is_video: Int,
                      var meida_url: String, var width: Int, var hight: Int, var duration: Int, var play_count: Int, var thumbImage: String)

//class NeiHanData {
//
//    /**
//     * 用户名
//     */
//    var user_name: String
//    /**
//     * 用户头像url
//     */
//    var avatar_url: String
//    /**
//     * 文字内容
//     */
//    var context: String
//    /**
//     * media类型 0文字，1图片，2gif,3视频
//     */
//    var media_type: Int = 0
//    /**
//     * 是否是gif
//     */
//    var is_gif: Int = 0
//    /**
//     * 是否是视频
//     */
//    var is_video: Int = 0
//
//    /**
//     * meida的url
//     */
//    var meida_url: String
//
//    /**
//     * meida的width
//     */
//    var width: Int = 0
//
//    /**
//     * meida的hight
//     */
//    var hight: Int = 0
//
//    /**
//     * meida时长
//     */
//    var duration: Int = 0
//
//    /**
//     * 播放次数
//     */
//    var play_count: Int = 0
//
//    /**
//     * 视频预览图片
//     */
//    var thumbImage: String
//
//    override fun toString(): String {
//        return "NeiHanData{" +
//                "user_name='" + user_name + '\'' +
//                ", avatar_url='" + avatar_url + '\'' +
//                ", context='" + context + '\'' +
//                ", media_type=" + media_type +
//                ", is_gif=" + is_gif +
//                ", is_video=" + is_video +
//                ", meida_url='" + meida_url + '\'' +
//                ", width=" + width +
//                ", hight=" + hight +
//                ", duration=" + duration +
//                ", play_count=" + play_count +
//                ", thumbImage='" + thumbImage + '\'' +
//                '}'
//    }
//}
