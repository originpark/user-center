package com.origin.usercenter.service;

import com.origin.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

/**
* @author wild hearts
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2024-09-18 00:08:21
*/
public interface UserService extends IService<User> {


    /**
     * 用户注册
     * @param userAccount 用户账号
     * @param userPassword 用户密码
     * @param checkPassWord 二次输入密码
     * @return 注册用户id
     */
    long userRegister(String userAccount, String userPassword, String checkPassWord);

    /**
     * 用户登录
     * @param userAccount 用户账号
     * @param userPassword 用户密码
     * @param httpServletRequest 请求对象
     * @return 脱敏登录用户
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest httpServletRequest);


    /**
     * 根据用户名模糊查询用户列表
     * @param username 用户名(模糊)
     * @return 用户列表
     */
    List<User> searchUsersByUsername(String username);


    /**
     * 用户脱敏
     * @param originUser 原始用户
     * @return 脱敏后用户
     */
    User getSafetyUser(User originUser);

}
