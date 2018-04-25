package com.utouch.service;

import com.utouch.dao.ActivityDao;
import com.utouch.dao.SubscriptionDao;
import com.utouch.dao.UserDao;
import com.utouch.entity.*;
import com.utouch.util.*;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class UserService {
    @Autowired
    UserDao userDao;
    @Autowired
    ActivityDao activityDao;
    @Autowired
    SubscriptionDao subscriptionDao;
    @Autowired
    Json json;
    @Autowired
    Subscription subscription;
    /**
     * 登录
     *
     * @param user 用户信息
     * @return json数据
     */
    public Json login(User user) {
        User usr = userDao.login(user);
        if (null == usr) {
            json.setCode(400);
            json.setMessage("用户名或密码错误");
            json.setData(null);
        } else {
            json.setCode(200);
            json.setMessage("登录成功");
            json.setData(usr);
        }
        return json;
    }

    /**
     * 短信验证码获取(30s)
     *
     * @param username 用户名（电话号）
     * @param request  获取session
     * @return json数据
     */
    public Json getVerification(String username, HttpServletRequest request) {
        User user = userDao.isRegister(username);
        if (user != null) {
            json.setCode(400);
            json.setMessage("该用户已注册");
            json.setData(null);
        } else {
            String verification = (new Random().nextInt()) * 8999 + 1000 + "";
            String apikey = "";
            String mobile = username;
            String text = "【UTouch】您的验证码是" + verification;
            if (SmsUtil.sendSms(apikey, mobile, text)) {
                json.setCode(200);
                json.setMessage("验证码获取成功");
                json.setData(null);
                HttpSession session = request.getSession();
                session.setAttribute("verification", verification);
            } else {
                json.setCode(400);
                json.setMessage("验证码获取失败");
                json.setData(null);
            }
        }
        return json;
    }

    /**
     * 短信验证码验证
     *
     * @param verification 短信验证码
     * @param request      获取session
     * @return json数据
     */
    public Json verify(String verification, HttpServletRequest request) {
        if (verification.equals(request.getSession().getAttribute("verification"))) {
            json.setCode(200);
            json.setMessage("验证成功");
            json.setData(null);
        } else {
            json.setCode(400);
            json.setMessage("验证码错误");
            json.setData(null);
        }
        return json;
    }

    /**
     * 手机号临时验证
     *
     * @param username 用户名（电话号）
     * @return json数据
     */
    public Json verifyTemp(String username) {
        User user = userDao.isRegister(username);//判断用户是否已经注册
        if (user != null) {
            json.setCode(400);
            json.setMessage("该用户已注册");
            json.setData(null);
        } else {
            json.setCode(200);
            json.setMessage("验证成功");
            json.setData(null);
        }
        return json;
    }

    //学生身份认证

    /**
     * 获取教务系统验证码
     *
     * @param request 获取session
     * @return json数据
     */
    public Json getTeachingVerification(HttpServletRequest request) {
        String verificationName="check"+(new Random()).nextInt(1000000)+".jpg";
        String imgPath = request.getServletContext().getRealPath("/verification/") +verificationName ;

        //获取验证码的二进制数据并保存到本地
        byte[] imgByte = HttpUtil.getInfo(request);
        OutputStream out = null;
        try {
            File filePath=new File(imgPath);
            if(!filePath.getParentFile().exists()){
                filePath.getParentFile().mkdirs();
            }
            out = new FileOutputStream(filePath);
            out.write(imgByte);
            out.flush();
            
            //使用定时器设置验证码的存活时间，防止存储空间爆炸
            new TimerUtil(60000,imgPath).excute();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (null == imgByte || imgByte.length == 0) {
            json.setCode(400);
            json.setMessage("教务系统验证码获取失败");
            json.setData(null);
        } else {
            json.setCode(200);
            json.setMessage("教务系统验证码获取成功");
            json.setData("http://140.143.25.150:8080/utouch/verification/"+verificationName);
        }
        return json;
    }

    /**
     * 模拟登陆教务系统
     *
     * @param stuNo        学号
     * @param password     密码
     * @param verification 验证码
     * @param request      获取session中的cookie
     * @return json数据
     */
    public Json loginTeachingSys(String stuNo, String password, String verification, HttpServletRequest request) {
        String html = "";

        //组织登陆请求参数
        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("id", stuNo));//学号
        params.add(new BasicNameValuePair("pwd", password));//密码
        params.add(new BasicNameValuePair("xdvfb", verification));//验证码

        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "GB2312"); //封装成参数对象
            html = HttpUtil.sendPostRequest(entity, request);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //根据相应内容判断是否登录成功
        if (html.contains("验证码错误")) {
            json.setCode(400);
            json.setMessage("验证码错误");
            json.setData(null);
        } else if (html.contains("用户名/密码错误")) {
            json.setCode(400);
            json.setMessage("学号或密码错误");
            json.setData(null);
        } else {
            json.setCode(200);
            json.setMessage("身份验证成功");
            json.setData(null);
        }

        return json;
    }

    /**
     * 用户注册
     *
     * @param user 注册用户信息
     * @return json数据
     */
    public Json register(User user) {
        int num = userDao.register(user);
        if (num > 0) {
            json.setCode(200);
            json.setMessage("注册成功");
            json.setData(null);
        } else {
            json.setCode(400);
            json.setMessage("注册失败");
            json.setData(null);
        }
        return json;
    }

    /**
     * 手机号临时验证
     *
     * @param username 用户名（电话号）
     * @return json数据
     */
    public Json verifyTempAfterLosing(String username) {
        User user = userDao.isRegister(username);//判断用户是否已经注册
        if (user == null) {
            json.setCode(400);
            json.setMessage("用户不存在");
            json.setData(null);
        } else {
            json.setCode(200);
            json.setMessage("验证成功");
            json.setData(null);
        }
        return json;
    }

    /**
     * 忘记密码后重置密码
     *
     * @param user 用户信息，包括id和新的password
     * @return json数据
     */
    public Json resetPwd(User user) {
        int num = userDao.resetPwd(user);
        if (num > 0) {
            json.setCode(200);
            json.setMessage("重置成功");
            json.setData(null);
        } else {
            json.setCode(400);
            json.setMessage("重置失败");
            json.setData(null);
        }
        return json;
    }

    /**
     * 选择当前用户订阅的用户的活动
     *
     * @param activitySelector 活动选择信息（包含用户id）
     * @return json数据
     */
    public Json selectSubscriptions(ActivitySelector activitySelector,HttpServletRequest request) {
        HttpSession session=request.getSession();
        if(session.getAttribute("subscriptionStart")==null){
            session.setAttribute("subscriptionStart",0);
        }
        activitySelector.setStart(Integer.parseInt(session.getAttribute("subscriptionStart").toString()));
        session.setAttribute("subscriptionStart",activitySelector.getStart()+activitySelector.getNum());
        List<ActivityWithUserInfo> res = userDao.selectSubscriptions(activitySelector);
        if (res == null || res.isEmpty()) {
            json.setCode(400);
            json.setMessage("您还未订阅任何用户");
            json.setData(null);
        } else {
            json.setCode(200);
            json.setMessage("查询成功");
            json.setData(res);
        }
        return json;
    }


    /**
     * 查看活动发布者详细信息
     *
     * @param id 当前用户id
     * @param announcerId 活动发布者id
     * @return json数据
     */
    @Transactional
    public Json selectAnnouncerInfo(int id,int announcerId) {
        subscription.setUserId(id);
        subscription.setSubscribedId(announcerId);
        Subscription subs = subscriptionDao.isSubscribed(subscription);
        User user = userDao.selectUserInfo(announcerId);
        if (user == null) {
            json.setCode(400);
            json.setMessage("查询失败");
            json.setData(null);
        } else {
            json.setCode(200);
            json.setData(user);
            if (subs == null) {
                json.setMessage("未订阅该用户");
            } else {
                json.setMessage("已订阅该用户");
            }

        }
        return json;
    }

    /**
     * 订阅某一用户
     *
     * @param subscription 订阅信息（包含当前用户id及被订阅用户id）
     * @return json数据
     */
    @Transactional
    public Json subscribing(Subscription subscription) {
        Subscription sub = subscriptionDao.isSubscribed(subscription);
        if (sub == null) {
            int num1 = subscriptionDao.insertSubscription(subscription);
            int num2 = userDao.addSubscriptionNum(subscription.getUserId());
            if (num1 > 0 && num2 > 0) {
                json.setCode(200);
                json.setMessage("订阅成功");
                json.setData(null);
            } else {
                json.setCode(400);
                json.setMessage("订阅失败");
                json.setData(null);
            }
        } else {
            json.setCode(400);
            json.setMessage("您已订阅该用户");
            json.setData(null);
        }
        return json;
    }

    /**
     * 取消订阅某一用户
     *
     * @param subscription 订阅信息（包含用户id及被订阅用户id）
     * @return json数据
     */
    @Transactional
    public Json unsubscribing(Subscription subscription) {
        int num1 = subscriptionDao.deleteSubscription(subscription);
        int num2 = userDao.subtractSubscriptionNum(subscription.getUserId());

        if (num1 > 0 && num2 > 0) {
            json.setCode(200);
            json.setMessage("取消成功");
            json.setData(null);
        } else {
            json.setCode(400);
            json.setMessage("取消失败");
            json.setData(null);
        }

        return json;
    }

    /**
     * 上传头像
     *
     * @param avatar 头像文件
     * @param request 用于获取项目路径
     * @return json数据（包含头像文件url）
     */
    public Json uploadAvatar(MultipartFile avatar,HttpServletRequest request) {
        String url=FileUtil.uploadFile(avatar,"avatar",request);
        if(url!=null&&url.length()!=0){
            json.setCode(200);
            json.setMessage("上传成功");
            json.setData(url);
        }else {
            json.setCode(400);
            json.setMessage("上传失败");
            json.setData(null);
        }

        return json;
    }

    /**
     * 修改个人信息
     *
     * @param user 修改后的用户信息
     * @return json数据（是否修改成功）
     */
    public Json editPersonalInfo(User user) {
        int num=userDao.updatePersonalInfo(user);
        if(num>0){
            json.setCode(200);
            json.setMessage("编辑成功");
            json.setData(null);
        }else {
            json.setCode(400);
            json.setMessage("编辑失败");
            json.setData(null);
        }
        return json;
    }

    /**
     * 显示用户的所有订阅
     *
     * @param id 用户id
     * @return  json数据（包含所有订阅信息）
     */
    public Json showAllSubscriptions(int id) {
        List<User> list=userDao.showAllSubscriptions(id);
        if(list==null||list.isEmpty()){
            json.setCode(400);
            json.setMessage("查看失败");
            json.setData(null);
        }else {
            json.setCode(200);
            json.setMessage("查看成功");
            json.setData(list);
        }

        return json;
    }

    /**
     * 感兴趣的
     *
     * @param activitySelector 活动选择信息（用户id，选择数量）
     * @param request 用于获取已经查询的数量
     * @return json数据
     */
    public Json showInterestingActivities(ActivitySelector activitySelector, HttpServletRequest request) {
        HttpSession session=request.getSession();
        if(session.getAttribute("interestingActivitiesStart")==null){
            session.setAttribute("interestingActivitiesStart",0);
        }
        activitySelector.setStart(Integer.parseInt(session.getAttribute("interestingActivitiesStart").toString()));
        session.setAttribute("interestingActivitiesStart",activitySelector.getStart()+activitySelector.getNum());
        List<ActivityWithUserInfo> list=userDao.selectInterestingActivities(activitySelector);
        if(list==null||list.isEmpty()){
            json.setCode(400);
            json.setMessage("您当前无感兴趣的活动");
            json.setData(null);
        }else {
            json.setCode(200);
            json.setMessage("信息获取成功");
            json.setData(list);
        }
        
        return json;
    }

    /**
     * 我发布的
     *
     * @param activitySelector 活动选择信息
     * @param request 用于获取已经查询的数量
     * @return json数据
     */
    public Json showMyOwnActivities(ActivitySelector activitySelector, HttpServletRequest request) {
        HttpSession session=request.getSession();
        if(session.getAttribute("myActivitiesStart")==null){
            session.setAttribute("myActivitiesStart",0);
        }
        activitySelector.setStart(Integer.parseInt(session.getAttribute("myActivitiesStart").toString()));
        session.setAttribute("myActivitiesStart",activitySelector.getStart()+activitySelector.getNum());

        List<Activity> list=activityDao.selectMyOwnActivities(activitySelector);
        if(list==null||list.isEmpty()){
            json.setCode(400);
            json.setMessage("您还未发布活动");
            json.setData(null);
        }else {
            json.setCode(400);
            json.setMessage("信息获取成功");
            json.setData(list);
        }
        return json;
    }
}
