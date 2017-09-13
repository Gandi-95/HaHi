package com.gdi.hahi.mvp.model.bean;

/**
 * Created by gandi on 2017/9/6 0006.
 */

public class HahiBean {

    /**
     * 用户名
     */
    String user_name;
    /**
     * 用户头像url
     */
    String avatar_url;
    /**
     * 文字内容
     */
    String context;
    /**
     * media类型 0文字，1图片，2gif,3视频
     */
    int media_type;
    /**
     * 是否是gif
     */
    int is_gif;
    /**
     * 是否是视频
     */
    int is_video;

    /**
     * meida的url
     */
    String meida_url;

    /**
     * meida的width
     */
    int width;

    /**
     * meida的hight
     */
    int hight;

    /**
     * meida时长
     */
    int  duration;

    /**
     * 播放次数
     */
    int play_count;

    /**
     * 视频预览图片
     */
    String thumbImage;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public int getMedia_type() {
        return media_type;
    }

    public void setMedia_type(int media_type) {
        this.media_type = media_type;
    }

    public int getIs_gif() {
        return is_gif;
    }

    public void setIs_gif(int is_gif) {
        this.is_gif = is_gif;
    }

    public int getIs_video() {
        return is_video;
    }

    public void setIs_video(int is_video) {
        this.is_video = is_video;
    }

    public String getMeida_url() {
        return meida_url;
    }

    public void setMeida_url(String meida_url) {
        this.meida_url = meida_url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHight() {
        return hight;
    }

    public void setHight(int hight) {
        this.hight = hight;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPlay_count() {
        return play_count;
    }

    public void setPlay_count(int play_count) {
        this.play_count = play_count;
    }


    public String getThumbImage() {
        return thumbImage;
    }

    public void setThumbImage(String thumbImage) {
        this.thumbImage = thumbImage;
    }

    @Override
    public String toString() {
        return "HahiBean{" +
                "user_name='" + user_name + '\'' +
                ", avatar_url='" + avatar_url + '\'' +
                ", context='" + context + '\'' +
                ", media_type=" + media_type +
                ", is_gif=" + is_gif +
                ", is_video=" + is_video +
                ", meida_url='" + meida_url + '\'' +
                ", width=" + width +
                ", hight=" + hight +
                ", duration=" + duration +
                ", play_count=" + play_count +
                ", thumbImage='" + thumbImage + '\'' +
                '}';
    }
}
