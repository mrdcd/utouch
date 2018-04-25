package com.utouch.dao;

import com.utouch.entity.ActivitySelector;
import com.utouch.entity.ActivityWithUserInfo;
import com.utouch.entity.Subscription;
import com.utouch.entity.User;

import java.util.List;

public interface UserDao {
    //登录
    User login(User user);

    //检测用户是否注册
    User isRegister(String username);

    //注册
    Integer register(User user);

    //重置密码
    Integer resetPwd(User user);

    //查询订阅的活动
    List<ActivityWithUserInfo> selectSubscriptions(ActivitySelector activitySelector);

    //查询用户信息
    User selectUserInfo(int id);

    //增加订阅数
    int addSubscriptionNum(int userId);

    //减少订阅数
    int subtractSubscriptionNum(int userId);

    //增加活动数
    int addActivityNum(int userId);

    //修改个人信息
    int updatePersonalInfo(User user);

    //显示所有订阅
    List<User> showAllSubscriptions(int id);

    //选择所有用户感兴趣的活动
    List<ActivityWithUserInfo> selectInterestingActivities(ActivitySelector activitySelector);
}
