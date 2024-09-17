package com.origin.usercenter.service;

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
        User user = new User();
        user.setUsername("test-user");
        user.setUserAccount("123");
        user.setAvatarUrl("");
        user.setGender(0);
        user.setUserPassword("123");
        user.setPhone("18222239438");
        user.setEmail("xxx@email.com");

        boolean result = userService.save(user);
        Assertions.assertTrue(result);
    }
}