package com.utouch.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/*
* 兴趣类，用于表示用户感兴趣的活动
* */
@Component
public class Interest {
    private int id;//兴趣id，主键
    private int userId;//用户id
    private int activityId;//活动id

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }
}
