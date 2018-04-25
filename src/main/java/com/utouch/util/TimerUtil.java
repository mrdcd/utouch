package com.utouch.util;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 用于删除本地文件的定时器
 */
public class TimerUtil extends TimerTask {
    private long time;//多长时间后执行
    private String path;//文件路径

    public TimerUtil(long time,String path){
        this.time=time;
        this.path=path;
    }

    //删除文件
    public void run() {
        new File(path).delete();
    }

    public void excute(){
        new Timer().schedule(this,time);
    }
}
