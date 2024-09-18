package com.origin.usercenter.controller;

import com.origin.usercenter.model.domain.User;
import com.origin.usercenter.model.request.UserLoginRequest;
import com.origin.usercenter.model.request.UserRegisterRequest;
import com.origin.usercenter.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


import static com.origin.usercenter.constant.UserConstant.ADMIN_ROLE;
import static com.origin.usercenter.constant.UserConstant.USER_LOGIN_STATE;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            return null;
        }

        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();

        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return null;
        }

        userService.userRegister(userAccount, userPassword, checkPassword);
        return null;
    }

    @PostMapping("/login")
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest httpServletRequest) {
        if (userLoginRequest == null) {
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();



        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }

        return userService.userLogin(userAccount, userPassword, httpServletRequest);
    }

    @GetMapping("/search")
    public List<User> searchUser(String username, HttpServletRequest httpServletRequest) {

        // 仅管理员
        if (!is_admin(httpServletRequest)) {
            return new ArrayList<>();
        }

        List<User> userList = userService.searchUsersByUsername(username);
        return userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());

    }

    @PostMapping("/delete")
    public boolean deleteUser(@RequestBody long id, HttpServletRequest httpServletRequest) {

        // 仅管理员
        if (!is_admin(httpServletRequest)) {
            return false;
        }

        if (id <= 0) {
            return false;
        }
        return userService.removeById(id);
    }


    private boolean is_admin(HttpServletRequest httpServletRequest) {
        Object userObj = httpServletRequest.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return user != null && user.getUserRole() == ADMIN_ROLE;
    }
}
