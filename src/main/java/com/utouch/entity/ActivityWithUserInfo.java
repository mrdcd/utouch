package com.utouch.entity;

/*
 *订阅类  用于查询当前用户所订阅的用户的活动信息和用户信息
 * */
public class ActivityWithUserInfo extends Activity {
    private String nickname;//用户昵称
    private String avatar;//头像路径
    private String profile;//个签（简介）

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
