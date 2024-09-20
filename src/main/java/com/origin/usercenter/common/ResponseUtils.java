package com.origin.usercenter.common;


import com.origin.usercenter.exception.BusinessException;

public class ResponseUtils {
    public static <T> BaseResponse<T> response(ResponseType responseType, T data, String description) {
        return new BaseResponse<>(responseType, data, description);
    }

    public static <T> BaseResponse<T> success(T data) {
        return response(ResponseType.SUCCESS, data, "");
    }

    public static <T> BaseResponse<T> error(ResponseType responseType) {
        return response(responseType, null, "");
    }

    public static <T> BaseResponse<T> error(ResponseType responseType, String description) {
        return response(responseType, null, description);
    }

    public static <T> BaseResponse<T> error(BusinessException e, String description) {
        return new BaseResponse<>(e.getCode(), null, e.getMessage(), description);
    }

}
