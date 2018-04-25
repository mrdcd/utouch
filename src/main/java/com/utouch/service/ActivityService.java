package com.utouch.service;

import com.utouch.dao.*;
import com.utouch.entity.*;
import com.utouch.util.FileUtil;
import com.utouch.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class ActivityService {
    @Autowired
    UserDao userDao;
    @Autowired
    ActivityDao activityDao;
    @Autowired
    ActivityLabelsDao activityLabelsDao;
    @Autowired
    SubscriptionDao subscriptionDao;
    @Autowired
    InterestDao interestDao;
    @Autowired
    Json json;
    @Autowired
    Subscription subscription;
    @Autowired
    Interest interest;

    /**
     * 感兴趣
     *
     * @param userId 用户id
     * @param activityId 活动id
     * @return json数据
     */
    @Transactional
    public Json interesting(int userId,int activityId, int isCancel) {
        int num1,num2;
        System.out.println(interest.getUserId());
        interest.setUserId(userId);
        interest.setActivityId(activityId);
        if(isCancel==0){
            num1 = activityDao.interesting(activityId);
            num2= interestDao.insertInterest(interest);
        }else {
            num1=activityDao.interestCanceling(activityId);
            num2=interestDao.deleteInterest(interest);
        }
        if (num1 > 0&&num2>0) {
            json.setCode(200);
            json.setMessage("操作成功");
            json.setData(null);
        } else {
            json.setCode(400);
            json.setMessage("操作失败");
            json.setData(null);
        }

        return json;
    }

    /**
     * 分享
     *
     * @param id 活动id
     * @return json数据
     */
    public Json sharing(int id) {
        int num = activityDao.sharing(id);
        if (num > 0) {
            json.setCode(200);
            json.setMessage("操作成功");
            json.setData(null);
        } else {
            json.setCode(400);
            json.setMessage("操作失败");
            json.setData(null);
        }

        return json;
    }

    /**
     * 举报
     *
     * @param id 活动id
     * @return json数据
     */
    @Transactional
    public Json reporting(int id) {
        int reportedNum=activityDao.selectReportedNum(id);
        if(reportedNum<=9){
            int num=activityDao.reporting(id);
            if(num>0){
                json.setCode(200);
                json.setMessage("操作成功");
                json.setData(null);
            }else {
                json.setCode(400);
                json.setMessage("操作失败");
                json.setData(null);
            }
        }else {
            /*int num=activityDao.deleteActivity(id);
            if(num>0){
                json.setCode(200);
                json.setMessage("操作成功");
                json.setData(null);
            }else {
                json.setCode(400);
                json.setMessage("操作失败");
                json.setData(null);
            }*/
            json.setCode(400);
            json.setMessage("操作失败");
            json.setData(null);
        }
        return json;
    }

    /**
     * 查看活动详情
     *
     * @param id  活动id
     * @param announcerId 活动发布者id
     * @param userId 当前用户id
     * @return json数据
     */
    @Transactional
    public Json selectActivityInfo(int id, int announcerId, int userId) {
        subscription.setUserId(userId);
        subscription.setSubscribedId(announcerId);
        Subscription subs=subscriptionDao.isSubscribed(subscription);
        ActivityWithUserInfo activityWithUserInfo=activityDao.selectActivityInfo(id);
        if(activityWithUserInfo==null){
            json.setCode(400);
            json.setMessage("查询失败");
            json.setData(null);
        }else {
            json.setCode(200);
            json.setData(activityWithUserInfo);
            if(subs==null){
                json.setMessage("未订阅该用户");
            }else {
                json.setMessage("已订阅该用户");
            }
        }

        return json;
    }


    /**
     * 显示未过时活动
     *
     * @param activitySelector 活动选择信息
     * @param request 用于获取session
     * @return json数据（包含符合要求的活动信息）
     */
    public Json selectActivities(ActivitySelector activitySelector,HttpServletRequest request) {
        HttpSession session=request.getSession();
        if(session.getAttribute("activityStart")==null){
            session.setAttribute("activityStart",0);
        }
        activitySelector.setStart(Integer.parseInt(session.getAttribute("activityStart").toString()));
        session.setAttribute("activityStart",activitySelector.getStart()+activitySelector.getNum());
        List<ActivityWithUserInfo> res=activityDao.selectActivities(activitySelector);
        if(res==null||res.isEmpty()){
            json.setCode(400);
            json.setMessage("当前无活动");
            json.setData(null);
        }else {
            json.setCode(200);
            json.setMessage("活动获取成功");
            json.setData(res);
        }

        return json;
    }


    /**
     * 筛选活动
     *
     * @param activitySelector 活动选择信息（包含筛选条件）
     * @param request 用于获取session
     * @return json数据（包含活动信息）
     */
    public Json selectProperActivities(ActivitySelector activitySelector,HttpServletRequest request) {
        HttpSession session=request.getSession();
        if(session.getAttribute("properActivityStart")==null){
            session.setAttribute("properActivityStart",0);
        }
        activitySelector.setStart(Integer.parseInt(session.getAttribute("properActivityStart").toString()));
        session.setAttribute("properActivityStart",activitySelector.getStart()+activitySelector.getNum());
        List<ActivityWithUserInfo> res=activityDao.selectProperActivities(activitySelector);

        if(res==null||res.isEmpty()){
            json.setCode(400);
            json.setMessage("筛选失败");
            json.setData(null);
        }else {
            json.setCode(200);
            json.setMessage("筛选成功");
            json.setData(res);
        }
        return json;
    }

    /**
     * 获取系统预设活动标签
     *
     * @return json数据（标签数组）
     */
    public Json getActivityLabels() {
        List<ActivityLabel> list=activityLabelsDao.selectAllLabels();
        if(list==null||list.isEmpty()){
            json.setCode(400);
            json.setMessage("标签信息获取失败");
            json.setData(null);
        }else {
            json.setCode(200);
            json.setMessage("标签信息获取成功");
            json.setData(list);
        }

        return json;
    }

    /**
     * 上传图片
     *
     * @param pictures 图片文件数组
     * @param request  用于获取绝对路径
     * @return json数据（包含图片url连接组成的字符串）
     */
    public Json uploadActivityPictures(MultipartFile[] pictures, HttpServletRequest request){
        String urls=FileUtil.uploadFiles(pictures,"picture",request);

        if(urls==null||urls.length()==0){
            json.setCode(400);
            json.setMessage("文件上传失败");
            json.setData(null);
        }else {
            json.setCode(200);
            json.setMessage("文件上传成功");
            json.setData(urls);
        }
        return json;
    }

    /**
     * 上传视频
     *
     * @param video 视频文件
     * @param request 用于获取绝对路径
     * @return json数据（包含视频url）
     */
    public Json uploadActivityVideo(MultipartFile video, HttpServletRequest request) {
        String url=FileUtil.uploadFile(video,"video",request);
        if(url==null||url.length()==0){
            json.setCode(400);
            json.setMessage("文件上传失败");
            json.setData(null);
        }else {
            json.setCode(200);
            json.setMessage("文件上传成功");
            json.setData(url);
        }
        return json;
    }

    /**
     * 上传音频
     *
     * @param audio 音频文件
     * @param request 用于获取绝对路径
     * @return json数据（包含音频url）
     */
    public Json uploadActivityAudio(MultipartFile audio, HttpServletRequest request) {
        String url=FileUtil.uploadFile(audio,"audio",request);
        if(url==null||url.length()==0){
            json.setCode(400);
            json.setMessage("文件上传失败");
            json.setData(null);
        }else {
            json.setCode(200);
            json.setMessage("文件上传成功");
            json.setData(url);
        }
        return json;
    }

    /**
     * 发布活动
     *
     * @param activity 活动信息
     * @return json数据（发布结果）
     */
    @Transactional
    public Json announceActivity(Activity activity) {
        int num1=activityDao.insertActivity(activity);
        int num2=userDao.addActivityNum(activity.getAnnouncerId());
        if(num1>0&&num2>0){
            json.setCode(200);
            json.setMessage("发布成功");
            json.setData(null);
        }else {
            json.setCode(400);
            json.setMessage("发布失败");
            json.setData(null);
        }

        return json;
    }

}
