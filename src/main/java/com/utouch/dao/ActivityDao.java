package com.utouch.dao;

import com.utouch.entity.Activity;
import com.utouch.entity.ActivitySelector;
import com.utouch.entity.ActivityWithUserInfo;

import java.util.List;

public interface ActivityDao {
    //感兴趣
    int interesting(int id);

    //取消感兴趣
    int interestCanceling(int id);

    //分享
    int sharing(int id);

    //举报
    int reporting(int id);

    //查看举报数
    int selectReportedNum(int id);

    /*
    * 修改时间：2018/4/20
    * 修改内容：注释对于活动的删除
    * */
    //删除举报数达到上限（5）或过期的活动
    // int deleteActivity(int id);

    //根据id查看活动详情
    ActivityWithUserInfo selectActivityInfo(int id);

    //选择所有未过时的活动
    List<ActivityWithUserInfo> selectActivities(ActivitySelector activitySelector);

    //根据条件选择活动
    List<ActivityWithUserInfo> selectProperActivities(ActivitySelector activitySelector);

    //插入活动记录
    int insertActivity(Activity activity);

    //选择指定用户发布的活动
    List<Activity> selectMyOwnActivities(ActivitySelector activitySelector);
}
