package com.utouch.entity;

/**
 * 活动选择器（用于接收参数）
 */
public class ActivitySelector {
    private int id;//用户id，用于选择用户订阅的活动
    private String info;//筛选条件，用于筛选活动
    private int start;//选择的起始记录序号
    private int num;//选择记录数量

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
}
