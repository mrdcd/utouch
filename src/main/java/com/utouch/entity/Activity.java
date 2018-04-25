package com.utouch.entity;

/*
 * 活动类
 * */
public class Activity {
    private int id;
    private int announcerId;//活动发布者id
    private String activityName;//活动名称
    private String activityIntro;//活动介绍
    private String activityLabel;//活动标签,多个标签采用分号分隔
    private String activityVideo;//活动视频路径
    private String activityAudio;//活动音频路径
    private String activityPicture;//活动图片路径,多张图片采用分号分隔
    private String activityTime;//活动时间
    private String activityPlace;//活动地点
    private int interestedNum;//感兴趣数
    private int sharedNum;//分享数
    private int reportedNum;//举报数

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAnnouncerId() {
        return announcerId;
    }

    public void setAnnouncerId(int announcerId) {
        this.announcerId = announcerId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getActivityIntro() {
        return activityIntro;
    }

    public void setActivityIntro(String activityIntro) {
        this.activityIntro = activityIntro;
    }

    public String getActivityLabel() {
        return activityLabel;
    }

    public void setActivityLabel(String activityLabel) {
        this.activityLabel = activityLabel;
    }

    public String getActivityVideo() {
        return activityVideo;
    }

    public void setActivityVideo(String activityVideo) {
        this.activityVideo = activityVideo;
    }

    public String getActivityAudio() {
        return activityAudio;
    }

    public void setActivityAudio(String activityAudio) {
        this.activityAudio = activityAudio;
    }

    public String getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(String activityTime) {
        this.activityTime = activityTime;
    }

    public String getActivityPlace() {
        return activityPlace;
    }

    public void setActivityPlace(String activityPlace) {
        this.activityPlace = activityPlace;
    }

    public int getInterestedNum() {
        return interestedNum;
    }

    public void setInterestedNum(int interestedNum) {
        this.interestedNum = interestedNum;
    }

    public int getSharedNum() {
        return sharedNum;
    }

    public void setSharedNum(int sharedNum) {
        this.sharedNum = sharedNum;
    }

    public int getReportedNum() {
        return reportedNum;
    }

    public void setReportedNum(int reportedNum) {
        this.reportedNum = reportedNum;
    }

    public String getActivityPicture() {
        return activityPicture;
    }

    public void setActivityPicture(String activityPicture) {
        this.activityPicture = activityPicture;
    }
}
