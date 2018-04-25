package com.utouch.controller;


import com.utouch.entity.ActivitySelector;
import com.utouch.entity.Subscription;
import com.utouch.entity.User;
import com.utouch.service.UserService;
import com.utouch.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    /*
    * 登录
    * */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody Json login(User user) {
        return userService.login(user);
    }

    /*
    * 注册
    * */
    //获取短信验证码
    @RequestMapping(value = "/getVerification",method =RequestMethod.POST)
    public @ResponseBody Json getVerification(String username, HttpServletRequest request){
        return userService.getVerification(username,request);
    }

    //短信验证码验证
    @RequestMapping(value = "/verify",method = RequestMethod.POST)
    public @ResponseBody Json verify(String verification,HttpServletRequest request){
        return userService.verify(verification,request);
    }

    //1.手机验证(无验证码)
    @RequestMapping(value = "/verifyTemp",method =RequestMethod.POST)
    public @ResponseBody Json verifyTemp(String username){
        return userService.verifyTemp(username);
    }

    /*
     * 2.学生身份认证
     * */
    //获取教务系统验证码
    @RequestMapping(value = "/getTeachingVerification",method = RequestMethod.GET)
    public @ResponseBody Json getTeachingVerification(HttpServletRequest request) {
         return userService.getTeachingVerification(request);
    }

    //模拟登陆教务系统
    @RequestMapping(value = "/loginTeachingSys",method = RequestMethod.POST)
    public @ResponseBody Json loginTeachingSys(String stuNo, String password, String verification, HttpServletRequest request) {
        return userService.loginTeachingSys(stuNo,password,verification,request);
    }

    //3.注册
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public @ResponseBody Json register(User user){
        return userService.register(user);
    }

    /*
    * 忘记密码
    * */
    //1.手机验证
    @RequestMapping(value = "/verifyTempAfterLosing",method = RequestMethod.POST)
    public @ResponseBody Json verifyTempAfterLosing(String username){
        return userService.verifyTempAfterLosing(username);
    }

    //2.重置密码
    @RequestMapping(value = "/resetPwd",method = RequestMethod.POST)
    public @ResponseBody Json resetPwd(User user){
        return userService.resetPwd(user);
    }

    /*
    * 订阅主页（选择用户订阅的用户的活动）
    * */
    @RequestMapping(value = "/selectSubscriptions",method = RequestMethod.POST)
    public @ResponseBody Json selectSubscriptions(ActivitySelector activitySelector,HttpServletRequest request){
        return userService.selectSubscriptions(activitySelector,request);
    }

    /*
    * 查询活动发布者信息
    * */
    @RequestMapping(value = "/selectAnnouncerInfo",method = RequestMethod.POST)
    public @ResponseBody Json selectAnnouncerInfo(int id,int announcerId){
        return userService.selectAnnouncerInfo(id,announcerId);
    }

    /*
     * 订阅
     * */
    @RequestMapping(value = "/subscribing",method = RequestMethod.POST)
    public @ResponseBody Json subscribing(Subscription subscription){
        return userService.subscribing(subscription);
    }

    /*
     * 取消订阅
     * */
    @RequestMapping(value = "/unsubscribing",method = RequestMethod.POST)
    public @ResponseBody Json unsubscribing(Subscription subscription){
        return userService.unsubscribing(subscription);
    }

    /*
    * 上传头像
    * */
    @RequestMapping(value = "/uploadAvatar",method = RequestMethod.POST)
    public @ResponseBody Json uploadAvatar(MultipartFile avatar,HttpServletRequest request){
        return userService.uploadAvatar(avatar,request);
    }

    /*
    * 修改个人信息
    * */
    @RequestMapping(value = "/editPersonalInfo",method = RequestMethod.POST)
    public @ResponseBody Json editPersonalInfo(User user){
        return userService.editPersonalInfo(user);
    }

    /*
    *显示用户所有订阅
    * */
    @RequestMapping(value = "/showAllSubscriptions",method = RequestMethod.POST)
    public @ResponseBody Json showAllSubscriptions(int id){
        return userService.showAllSubscriptions(id);
    }

    /*
    * 感兴趣的
    * */
    @RequestMapping(value = "/showInterestingActivities",method = RequestMethod.POST)
    public @ResponseBody Json showInterestingActivities(ActivitySelector activitySelector,HttpServletRequest request){
        return userService.showInterestingActivities(activitySelector,request);
    }

    /*
    * 我发布的
    * */
    @RequestMapping(value = "/showMyOwnActivities",method = RequestMethod.GET)
    public @ResponseBody Json showMyOwnActivities(ActivitySelector activitySelector,HttpServletRequest request){
        return userService.showMyOwnActivities(activitySelector,request);
    }
}
