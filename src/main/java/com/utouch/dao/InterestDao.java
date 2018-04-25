package com.utouch.dao;

import com.utouch.entity.Interest;

public interface InterestDao {
    //添加记录
    int insertInterest(Interest interest);

    //删除记录
    int deleteInterest(Interest interest);
}
