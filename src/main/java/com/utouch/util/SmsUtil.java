package com.utouch.util;


import org.springframework.stereotype.Component;

/*
* 短信验证码
* */
@Component
public class SmsUtil {
    /**
     * 使用JDK发送单条短信,智能匹配短信模板
     *
     * @param apikey 成功注册后登录云片官网,进入后台可查看
     * @param text   需要使用已审核通过的模板或者默认模板
     * @param mobile 接收的手机号,仅支持单号码发送
     */
    public static boolean sendSms(String apikey, String mobile, String text) {
        /*YunpianRestClient client = new YunpianRestClient(apikey);//用apikey生成client,可作为全局静态变量
        SmsOperator smsOperator = client.getSmsOperator();//获取所需操作类
        ResultDO<SendSingleSmsInfo> result = smsOperator.singleSend(mobile, text);*///发送短信,ResultDO<?>.isSuccess()判断是否成功

        return true;
    }
}
