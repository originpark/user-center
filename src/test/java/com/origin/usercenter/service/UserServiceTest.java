package com.origin.usercenter.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.origin.usercenter.model.domain.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    void testInsertUser() {
        String account = "origin_park";
        String password = "1223003136";
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_account", account);
        userQueryWrapper.eq("user_password", password);
        User user = userService.getOne(userQueryWrapper);
        System.out.println(user);
    }
}