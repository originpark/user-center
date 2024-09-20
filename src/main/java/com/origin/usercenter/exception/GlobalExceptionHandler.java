package com.origin.usercenter.exception;

import com.origin.usercenter.common.BaseResponse;
import com.origin.usercenter.common.ResponseType;
import com.origin.usercenter.common.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(BusinessException.class)
    public <T> BaseResponse<T> businessExceptionHandler(BusinessException e) {
        log.error("businessException " + e.getMessage(), e);
        return ResponseUtils.error(e, e.getDescription());
    }


    @ExceptionHandler(RuntimeException.class)
    public <T> BaseResponse<T> runtimeExceptionHandler(RuntimeException e) {
        log.error("runtimeException ", e);
        return ResponseUtils.error(ResponseType.SYSTEM_ERROR, "");
    }
}
