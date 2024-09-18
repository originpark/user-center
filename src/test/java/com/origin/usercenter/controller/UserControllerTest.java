package com.origin.usercenter.controller;

import com.origin.usercenter.model.request.UserLoginRequest;
import com.origin.usercenter.model.request.UserRegisterRequest;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerTest {

    @Resource
    private UserController userController;


    @Test
    void testLogin() {
        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setUserAccount("origin_park");
        userLoginRequest.setUserPassword("1223003136");
    }
}