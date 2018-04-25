package com.utouch.util;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/*
* JSON类
* */
@Component
public class Json {
    private int code;//状态码
    private String message;//反馈信息
    private Object data;//业务数据

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
