package com.gdi.hahi.mvp.model.bean

import com.google.gson.annotations.SerializedName

/**
 * Created by gandi on 2017/9/14 0014.
 */

data class NeiHanBean(
		var message: String,// success
		var data: NData
)

data class NData(
		var has_more: Boolean,// true
		var tip: String,// 20条新内容
		var has_new_message: Boolean,// false
		var max_time: Double,// 1505400969
		var min_time: Double,// 1505401083
		var data: List<DataN>
)

data class DataN(
		var group: Group,
		var comments: List<Comment>,
		var type: Int,// 1
		var display_time: Double,// 1505401077
		var online_time: Double// 1505401077
)

data class Group(
		var large_image_list: List<Large_image_list>,
		var large_image: Large_image,
		var text: String,// 王宝强前经纪人宋喆因涉王宝强离婚案相关问题于近日被警方抓获。2016年8月14日，王宝强突发离婚声明，称妻子马蓉出轨自己的经纪人宋喆，并决定解除与马蓉的婚姻关系，停止与宋喆的合作。后王宝强到法院起诉其妻马蓉要求离婚。马蓉也委托律师到法院起诉王宝强侵犯名誉，要求删博并道歉30天。去年10月18日下午，王宝强起诉马蓉离婚、马蓉起诉王宝强侵犯名誉权两起案件在北京市朝阳区法院开庭，王宝强与其律师张起淮一起现身参与了庭审。距离此事过去一年多之后，从知情人处获悉，王宝强的前经纪人宋喆目前已经被抓获，罪名可能是涉嫌职务侵占。这口恶气等了一年终于出了？
		var neihan_hot_start_time: String,// 00-00-00
		var dislike_reason: List<Dislike_reason>,
		var create_time: Int,// 1505299511
		var id: Long,// 69069651716
		var favorite_count: Int,// 927
		var go_detail_count: Int,// 1205088
		var user_favorite: Int,// 0
		var share_type: Int,// 1
		var user: User,
		var is_can_share: Int,// 1
		var category_type: Int,// 1
		var share_url: String,// http://m.neihanshequ.com/share/group/69069651716/?iid=3216590132&app=joke_essay
		var label: Int,// 1
		var content: String,// 王宝强前经纪人宋喆因涉王宝强离婚案相关问题于近日被警方抓获。2016年8月14日，王宝强突发离婚声明，称妻子马蓉出轨自己的经纪人宋喆，并决定解除与马蓉的婚姻关系，停止与宋喆的合作。后王宝强到法院起诉其妻马蓉要求离婚。马蓉也委托律师到法院起诉王宝强侵犯名誉，要求删博并道歉30天。去年10月18日下午，王宝强起诉马蓉离婚、马蓉起诉王宝强侵犯名誉权两起案件在北京市朝阳区法院开庭，王宝强与其律师张起淮一起现身参与了庭审。距离此事过去一年多之后，从知情人处获悉，王宝强的前经纪人宋喆目前已经被抓获，罪名可能是涉嫌职务侵占。这口恶气等了一年终于出了？
		var comment_count: Int,// 3637
		var id_str: String,// 69069651716
		var media_type: Int,// 4
		var share_count: Int,// 1209
		var type: Int,// 5
		var status: Int,// 112
		var has_comments: Int,// 1
		var user_bury: Int,// 0
		var activity: Activity,
		var status_desc: String,// 热门发帖
		var quick_comment: Boolean,// false
		var display_type: Int,// 0
		var neihan_hot_end_time: String,// 00-00-00
		var user_digg: Int,// 0
		var online_time: Int,// 1505299511
		var category_name: String,// 明星圈儿
		var category_visible: Boolean,// true
		var bury_count: Int,// 6545
		var is_anonymous: Boolean,// false
		var repin_count: Int,// 927
		var is_neihan_hot: Boolean,// false
		var digg_count: Int,// 144416
		var has_hot_comments: Int,// 0
		var allow_dislike: Boolean,// true
		var user_repin: Int,// 0
		var neihan_hot_link: Neihan_hot_link,
		var is_multi_image: Int,// 1
		var group_id: Long,// 69069651716
		var thumb_image_list: List<Thumb_image_list>,
		var category_id: Int,// 61
		var mp4_url: String,// http://ic.snssdk.com/neihan/video/playback/1505401084.61/?video_id=bb50a7d8adee4e55a06d38ef56465448&quality=480p&line=0&is_gif=0&device_platform=android.mp4
        var duration: Double,
        var is_gif: Int,// 1
        var play_count: Int,
		var is_video: Int,// 1
		var large_cover: Large_cover,
		@SerializedName("360p_video")
		var video360p: Videop,
		@SerializedName("480p_video")
		var video480p: Videop,
		@SerializedName("720p_video")
		var video720p: Videop
)

data class Videop(
		var width: Int,// 480
		var url_list: List<Url_list>,
		var uri: String,// 360p/c6891964d1194ff18e07a41eb9b03e67
		var height: Int// 854
)

data class User(
		var user_id: Long,// 59765011680
		var name: String,// 忍冬淡逝
		var followings: Int,// 0
		var user_verified: Boolean,// false
		var ugc_count: Int,// 12
		var avatar_url: String,// http://q.qlogo.cn/qqapp/100290348/C5E57F87E520951350484FA328853F83/100
		var followers: Int,// 478
		var is_following: Boolean,// false
		var is_pro_user: Boolean// false
)

data class Dislike_reason(
		var type: Int,// 1
		var id: Long,// 318
		var title: String// 明星
)

data class Activity(
        var activity:String
)

data class Thumb_image_list(
		var url: String,// http://p1.pstatp.com/list/s362/38d5000524b5ae0046d9.webp
		var url_list: List<Url_list>,
		var uri: String,// list/s362/38d5000524b5ae0046d9
		var height: Int,// 362
		var width: Int,// 362
		var is_gif: Boolean// false
)

data class Url_list(
		var url: String// http://p1.pstatp.com/list/s362/38d5000524b5ae0046d9.webp
)

data class Neihan_hot_link(
        var Neihan_hot_link:String
)

data class Large_image_list(
		var url: String,// http://p1.pstatp.com/large/38d5000524b5ae0046d9.webp
		var url_list: List<Url_list>,
		var uri: String,// large/38d5000524b5ae0046d9
		var height: Int,// 417
		var width: Int,// 440
		var is_gif: Boolean// false
)

data class Large_image(
        var width: Int,// 500
        var r_height: Int,// 281
        var r_width: Int,// 500
        var url_list: List<Url_list>,
        var uri: String,// large/3a2c0000a8995102e329
        var height: Int// 281
)

data class Video720p(
		var width: Int,// 480
		var url_list: List<Url_list>,
		var uri: String,// 720p/c6891964d1194ff18e07a41eb9b03e67
		var height: Int// 854
)


data class Large_cover(
		var url_list: List<Url_list>,
		var uri: String// large/39860000738b38e7b95c
)


data class Comment(
		var text: String,// 一杯敬羽凡一杯敬宝强，守住你的善良，逼着你成长！
		var create_time: Int,// 1505302331
		var user_verified: Boolean,// false
		var user_bury: Int,// 0
		var user_id: Long,// 3896451899
		var bury_count: Int,// 0
		var share_url: String,// http://m.neihanshequ.com/share/group/69069651716/?comment_id=1578423896519709
		var id: Long,// 1578423896519709
		var platform: String,// feifei
		var is_digg: Int,// 0
		var user_name: String,// 你狠29243093
		var user_profile_image_url: String,// http://p9.pstatp.com/thumb/249a0017d7c41c988761
		var status: Int,// 5
		var description: String,//
		var user_digg: Int,// 0
		var user_profile_url: String,//
		var share_type: Int,// 2
		var digg_count: Int,// 52459
		var is_pro_user: Boolean,// false
		var platform_id: String,// feifei
		var avatar_url: String,// http://p9.pstatp.com/thumb/249a0017d7c41c988761
		var group_id: Long// 69069651716
)