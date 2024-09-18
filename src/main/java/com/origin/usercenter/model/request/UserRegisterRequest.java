package com.origin.usercenter.model.request;


import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class UserRegisterRequest implements Serializable {

    private String userAccount;

    private String userPassword;

    private String checkPassword;



    @Serial
    private static final long serialVersionUID = 4371237767961074230L;
}
