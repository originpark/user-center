package com.origin.usercenter.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponse<T> implements Serializable {
    private int code;

    private T data;

    private String message;

    private String description;

    public BaseResponse(int code, T data, String message, String description) {
        this.code = code;
        this.data = data;
        this.message = message;
        this.description = description;
    }

    public BaseResponse(ResponseType responseType, T data, String description) {
        this(responseType.getCode(), data, responseType.getMessage(), description);
    }
}
