package com.utouch.util;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
/*
 * http工具类 用于模拟登陆
 * */
public class HttpUtil {
    //教务系统URL
    private static final String BASEIC_URL="http://210.42.121.241/";
    //验证码URL
    private static final String  CHECK_IMG_URL=BASEIC_URL+"servlet/GenImg";
    //登录URL
    private static final String LOGIN_URL=BASEIC_URL+"servlet/Login";

    /**
     * 获取cookie和验证码,并将cookie保存在session中
     * @param request 用于获取session
     * @return 返回验证码图片的字节数组
     */
    public static byte[] getInfo(HttpServletRequest request) {
        String cookie = "";
        byte[] imgByte = null;
        HttpGet httpGet = new HttpGet(CHECK_IMG_URL);
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            cookie = response.getFirstHeader("Set-Cookie").getValue();
            imgByte = EntityUtils.toByteArray(response.getEntity());
        } catch (IOException e) {
            e.printStackTrace();
        }

        request.getSession().setAttribute("cookie", cookie);
        return imgByte;
    }

    /**
     * 包装请求并获取返回结果
     * @param entity 请求体
     * @param request 获取session
     * @return 响应html界面对应的字符串
     */
    public static String sendPostRequest(UrlEncodedFormEntity entity,HttpServletRequest request){
        StringBuffer sb=new StringBuffer();
        HttpPost httpPost=new HttpPost(LOGIN_URL);
        InputStream in=null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            //提交post请求，并获取返回结果
            httpPost.setHeader("Cookie", request.getSession().getAttribute("cookie").toString());  //设置cookie
            httpPost.setHeader("Referer",BASEIC_URL);//设置地址引用
            httpPost.setEntity(entity);//设置请求参数
            CloseableHttpResponse response=httpClient.execute(httpPost);//提交请求
            in=response.getEntity().getContent();//获得响应流对象

            //获取响应内容
            int len=-1;
            byte[] data=new byte[1024];
            while((len=in.read(data))!=-1){
                String s=new String(data,0,len,"GB2312");//设置编码，防止读入的数据乱码
                sb.append(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if(in!=null){
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

        return sb.toString();//返回响应内容（html界面）
    }
}
