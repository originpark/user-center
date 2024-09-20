package com.origin.usercenter.exception;

import com.origin.usercenter.common.ResponseType;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{
    private final int code;

    private final String description;

    public BusinessException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public BusinessException(ResponseType responseType, String description) {
        super(responseType.getMessage());
        this.code = responseType.getCode();
        this.description = description;
    }
}
