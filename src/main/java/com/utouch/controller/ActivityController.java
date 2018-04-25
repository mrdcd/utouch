package com.utouch.controller;

import com.utouch.entity.Activity;
import com.utouch.entity.ActivitySelector;
import com.utouch.service.ActivityService;
import com.utouch.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ActivityController {
    @Autowired
    ActivityService activityService;

    /*
    * 感兴趣
    * */
    @RequestMapping(value = "/interesting",method = RequestMethod.GET)
    public @ResponseBody Json interesting(int userId,int activityId,int isCancel){
        return activityService.interesting(userId,activityId,isCancel);
    }

    /*
    * 分享
    * */
    @RequestMapping(value = "/sharing",method = RequestMethod.POST)
    public @ResponseBody Json sharing (int id){
        return activityService.sharing(id);
    }

    /*
    * 举报
    * */
    @RequestMapping(value = "/reporting",method = RequestMethod.POST)
    public @ResponseBody Json reporting(int id){
        return activityService.reporting(id);
    }

    /*
    * 查看活动详情
    * */
    @RequestMapping(value = "/selectActivityInfo",method = RequestMethod.POST)
    public @ResponseBody Json selectActivityInfo(int id,int announcerId,int userId){
        return activityService.selectActivityInfo(id,announcerId,userId);
    }

    /*
     * 发现&广场（选择所有未过时的活动）
     * */
    @RequestMapping(value = "/selectActivities",method = RequestMethod.GET)
    public @ResponseBody Json selectActivities(ActivitySelector activitySelector,HttpServletRequest request){
        return activityService.selectActivities(activitySelector,request);
    }

    /*
    * 筛选活动
    * */
    @RequestMapping(value = "/selectProperActivities",method = RequestMethod.POST)
    public @ResponseBody Json selectProperActivities(ActivitySelector activitySelector,HttpServletRequest request){
        return activityService.selectProperActivities(activitySelector,request);
    }

    /*                     
    * 获取系统预设活动标签
    * */
    @RequestMapping(value = "/getActivityLabels",method = RequestMethod.GET)
    public @ResponseBody Json getActivityLabels(){
        return activityService.getActivityLabels();
    }

    /*
    * 上传图片
    * */
    @RequestMapping(value = "/uploadActivityPictures",method = RequestMethod.POST)
    public @ResponseBody Json uploadActivityPictures(MultipartFile []pictures, HttpServletRequest request){
        return activityService.uploadActivityPictures(pictures,request);
    }

    /*
    * 上传视频
    * */
    @RequestMapping(value = "/uploadActivityVideo",method = RequestMethod.POST)
    public @ResponseBody Json uploadActivityVideo(MultipartFile video,HttpServletRequest request){
        return activityService.uploadActivityVideo(video,request);
    }

    /*
    * 上传音频
    * */
    @RequestMapping(value = "/uploadActivityAudio",method = RequestMethod.POST)
    public @ResponseBody Json uploadActivityAudio(MultipartFile audio,HttpServletRequest request){
        return activityService.uploadActivityAudio(audio,request);
    }

    /*
    * 发布活动
    * */
    @RequestMapping(value = "/announceActivity",method = RequestMethod.POST)
    public @ResponseBody Json  announceActivity(Activity activity){
        return activityService.announceActivity(activity);
    }
}
