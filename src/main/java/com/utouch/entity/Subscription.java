package com.utouch.entity;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/*
* 订阅类（映射用户与订阅者的一对多关系）
* */
@Component
public class Subscription {
    private int id;
    private int userId;//用户id
    private int subscribedId;//被订阅者id

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

    public int getSubscribedId() {
        return subscribedId;
    }

    public void setSubscribedId(int subscribedId) {
        this.subscribedId = subscribedId;
    }
}
