package com.gdi.hahi.mvp.model.bean;

/**
 * Created by gandi on 2017/9/8 0008.
 */

public class SimpleLiveBean {

    /**
     * 昵称
     */
    String user_name;
    /**
     * 头像url
     */
    String avatar_url;
    /**
     * 直播url
     */
    String rtmp_pull_url;
    /**
     * 预览url
     */
    String avatar_jpg;
    /**
     * 城市
     */
    String city;


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

    public String getRtmp_pull_url() {
        return rtmp_pull_url;
    }

    public void setRtmp_pull_url(String rtmp_pull_url) {
        this.rtmp_pull_url = rtmp_pull_url;
    }

    public String getAvatar_jpg() {
        return avatar_jpg;
    }

    public void setAvatar_jpg(String avatar_jpg) {
        this.avatar_jpg = avatar_jpg;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "SimpleLiveBean{" +
                "user_name='" + user_name + '\'' +
                ", avatar_url='" + avatar_url + '\'' +
                ", rtmp_pull_url='" + rtmp_pull_url + '\'' +
                ", avatar_jpg='" + avatar_jpg + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
