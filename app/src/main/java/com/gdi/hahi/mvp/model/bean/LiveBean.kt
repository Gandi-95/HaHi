package com.gdi.hahi.mvp.model.bean

/**
 * Created by gandi on 2017/9/14 0014.
 */

data class LiveBean(
		var status_code: Int,// 0
		var data: List<LData>,
		var extra: Extra
)

data class LData(
		var type: Int,// 1
		var rid: String,// 2017091423385801001104204842670E
		var data: DataL
)

data class DataL(
		var status: Int,// 2
		var with_linkmic: Boolean,// false
		var enable_room_perspective: Boolean,// true
		var owner: Owner,
		var create_time: Int,// 1505395587
		var mosaic_status: Int,// 0
		var dynamic_cover_low: String,
		var stream_id_str: String,// 6465624814031686413
		var user_count: Int,// 20487
		var stats: Stats,
		var title: String,// 晚点了，你睡了吗？
		var finish_time: Int,// 1505403538
		var share_url: String,// http://m.neihanshequ.com/share/live/6465624813837912845/
		var id: Long,// 6465624813837913000
		var stream_id: Long,// 6465624814031687000
		var cell_style: Int,// 3
		var id_str: String,// 6465624813837912845
		var stream_url: Stream_url,
		var feed_room_label: Feed_room_label,
		var dynamic_cover: String
)

data class Owner(
		var allow_find_by_contacts: Boolean,// true
		var avatar_large: Avatar_large,
		var allow_use_linkmic: Boolean,// true
		var allow_others_download_video: Boolean,// false
		var is_follower: Boolean,// false
		var hotsoon_verified_reason: String,
		var constellation: String,// 射手座
		var id: Long,// 60941496367
		var city: String,// 大庆
		var verified: Boolean,// false
		var short_id: Int,// 122369960
		var is_personal_show_author: Boolean,// false
		var push_follow: Boolean,// true
		var verified_reason: String,
		var push_digg: Boolean,// true
		var pay_grade: Pay_grade,
		var id_str: String,// 60941496367
		var avatar_medium: Avatar_medium,
		var fold_stranger_chat: Boolean,// false
		var birthday_valid: Boolean,// true
		var need_profile_guide: Boolean,// false
		var hotsoon_verified: Boolean,// false
		var birthday_description: String,// 90后
		var birthday: Long,// 754531200
		var stats: StatsO,
		var push_video_post: Boolean,// true
		var push_video_recommend: Boolean,// true
		var nickname: String,// 妞妞（粉丝破70w）
		var avatar_thumb: Avatar_thumb,
		var avatar_jpg: Avatar_jpg,
		var fan_ticket_count: Int,// 622332
		var push_status: Boolean,// true
		var follow_status: Int,// 0
		var allow_be_located: Boolean,// true
		var level: Int,// 1
		var push_comment_status: Boolean,// true
		var gender: Int,// 0
		var allow_show_in_gossip: Boolean,// true
		var allow_strange_comment: Boolean,// true
		var linkmic_share_percent: Int,// 60
		var signature: String// 谢谢关注我的朋友们 每天晚上9点直播胃:xfsh5544332211加吧
)

data class Avatar_thumb(
		var url_list: List<String>,
		var uri: String// 100x100/36cc0003c9c7ccbc3b83
)

data class Avatar_jpg(
		var url_list: List<String>,
		var uri: String// 720x720/36cc0003c9c7ccbc3b83
)

data class Avatar_large(
		var url_list: List<String>,
		var uri: String// 1080x1080/36cc0003c9c7ccbc3b83
)

data class Pay_grade(
		var diamond_icon: Diamond_icon,
		var this_grade_min_diamond: Int,// 1000
		var total_diamond_count: Int,// 1719
		var name: String,// 树苗
		var level: Int,// 5
		var live_icon: Live_icon,
		var next_diamond: Int,// 10000
		var screen_chat_type: Int,// 2
		var im_icon: Im_icon,
		var now_diamond: Int,// 1678
		var this_grade_max_diamond: Int,// 1999
		var next_icon: Next_icon,
		var icon: Icon,
		var im_icon_with_level: Im_icon_with_level,
		var next_name: String,// 树叶
		var pay_diamond_bak: Int// 0
)

data class Next_icon(
		var url_list: List<String>,
		var uri: String// 12400003aae89daccd69
)

data class Diamond_icon(
		var url_list: List<String>,
		var uri: String// 12400003aba3dd42e213
)

data class Im_icon(
		var url_list: List<String>,
		var uri: String// 2ea8000962099e965ff0
)

data class Icon(
		var url_list: List<String>,
		var uri: String// 30eb0000a101d40eea0c
)

data class Im_icon_with_level(
		var url_list: List<String>,
		var uri: String// 30ee0007c3c292ef7fa9
)

data class Live_icon(
		var url_list: List<String>,
		var uri: String// 30ee0007ccef28b99639
)

data class Avatar_medium(
		var url_list: List<String>,
		var uri: String// 720x720/36cc0003c9c7ccbc3b83
)

data class StatsO(
		var record_count: Int,// 61
		var following_count: Int,// 93
		var total_duration: Int,// 321496
		var daily_income: Int,// 0
		var item_count: Int,// 253
		var daily_fan_ticket_count: Int,// 0
		var id_str: String,// 60941496367
		var follower_count: Int,// 706029
		var diamond_count: Int,// 1678
		var id: Long,// 60941496367
		var diamond_consumed_count: Int// 1678
)

data class Feed_room_label(
		var url_list: List<String>,
		var uri: String// 2ea90002aca1159b5c67
)

data class Stream_url(
		var rtmp_pull_url: String,// http://pull-flv-l6-hs.pstatp.com/live/stream-6465624814031686413.flv
		var provider: Int,// 1
		var id: Long,// 6465624814031687000
		var id_str: String// 6465624814031686413
)

data class Stats(
		var money: Int,// 209928
		var fan_ticket: Int,// 34988
		var id: Long,// 6465624813837913000
		var total_user: Int,// 179679
		var id_str: String// 6465624813837912845
)

data class Extra(
		var total: Int,// 20
		var has_more: Boolean,// true
		var fatal_ids: List<Long>,
		var max_time: Long,// 1505403539323
		var min_time: Int,// 0
		var now: Long// 1505403539324
)