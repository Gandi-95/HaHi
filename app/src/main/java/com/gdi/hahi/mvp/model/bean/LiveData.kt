package com.gdi.hahi.mvp.model.bean

/**
 * Created by gandi on 2017/9/8 0008.
 */

data class LiveData(var user_name: String, var avatar_url: String, var avatar_jpg: String, var city: String, var rtmp_pull_url: String)



//class LiveData {
//
//    /**
//     * 昵称
//     */
//    var user_name: String
//    /**
//     * 头像url
//     */
//    var avatar_url: String
//    /**
//     * 直播url
//     */
//    var rtmp_pull_url: String
//    /**
//     * 预览url
//     */
//    var avatar_jpg: String
//    /**
//     * 城市
//     */
//    var city: String
//
//    override fun toString(): String {
//        return "LiveData{" +
//                "user_name='" + user_name + '\'' +
//                ", avatar_url='" + avatar_url + '\'' +
//                ", rtmp_pull_url='" + rtmp_pull_url + '\'' +
//                ", avatar_jpg='" + avatar_jpg + '\'' +
//                ", city='" + city + '\'' +
//                '}'
//    }
//}
