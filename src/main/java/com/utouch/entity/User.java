package com.utouch.entity;

/*
 * 用户类
 * */
public class User {

    private Integer id;
    private String username;// 账号
    private String password;//密码
    private String nickname;// 用户名/昵称
    private int sex;// 性别
    private String grade;//年纪
    private String major;//专业
    private int activityNum;//活动数
    private int subscriptionNum;//订阅数
    private String avatar;//头像路径
    private String profile;//个人简介
    private String background;//个人背景路径

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public int getActivityNum() {
        return activityNum;
    }

    public void setActivityNum(int activityNum) {
        this.activityNum = activityNum;
    }

    public int getSubscriptionNum() {
        return subscriptionNum;
    }

    public void setSubscriptionNum(int subscriptionNum) {
        this.subscriptionNum = subscriptionNum;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}

