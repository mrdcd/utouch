package com.utouch.dao;

import com.utouch.entity.Subscription;

public interface SubscriptionDao {
    //查询某一用户是否被当前用户订阅
    Subscription isSubscribed(Subscription subscription);

    //插入订阅记录
    int insertSubscription(Subscription subscription);

    //删除订阅记录
    int deleteSubscription(Subscription subscription);
}
