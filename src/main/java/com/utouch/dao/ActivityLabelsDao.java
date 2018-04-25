package com.utouch.dao;

import com.utouch.entity.ActivityLabel;

import java.util.List;

public interface ActivityLabelsDao {
    //获取所有系统预设活动标签
    List<ActivityLabel> selectAllLabels();
}
