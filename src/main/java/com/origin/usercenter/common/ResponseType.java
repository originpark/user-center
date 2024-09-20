package com.origin.usercenter.common;


import lombok.Getter;

@Getter
public enum ResponseType {

    SUCCESS(20000, "OK"),
    PARAM_ERROR(40000, "请求参数错误"),
    NULL_ERROR(40001, "请求数据为空"),
    NOT_LOGIN(40100, "未登录"),
    NO_AUTH(40101, "无权限"),
    SYSTEM_ERROR(50000, "系统内部异常");


    private final int code;

    private final String message;


    ResponseType(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
