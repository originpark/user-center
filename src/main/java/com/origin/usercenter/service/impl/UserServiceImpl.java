package com.origin.usercenter.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.origin.usercenter.model.domain.User;
import com.origin.usercenter.service.UserService;
import com.origin.usercenter.mapper.UserMapper;
import com.origin.usercenter.utils.RegexUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;

import static com.origin.usercenter.constant.UserConstant.USER_LOGIN_STATE;

/**
* @author wild hearts
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2024-09-18 00:08:21
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{


    public static final String SALT = "origin";



    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassWord) {
        // 校验非空
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassWord)) {
            return -1;
        }

        // 账户长度不小于4
        if (userAccount.length() < 4) {
            return -1;
        }

        // 密码长度不小于8
        if (userPassword.length() < 8 || checkPassWord.length() < 8) {
            return -1;
        }

        // 密码和校验密码相同
        if (!userPassword.equals(checkPassWord)) {
            return -1;
        }


        // 账户不包含特殊字符
        if (!RegexUtils.matchA1_(userAccount)) {
            return -1;
        }

        // 账户不能重复
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_account", userAccount);
        long count = this.count(userQueryWrapper);
        if (count > 0) {
            return -1;
        }

        // 密码加密
        String md5Password = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        // 插入用户
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(md5Password);
        boolean result = save(user);
        if (!result) {
            return -1;
        }

        // 返回用户id
        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest httpServletRequest) {
        // 校验非空
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        // 账户长度不小于4
        if (userAccount.length() < 4) {
            return null;
        }

        // 密码长度不小于8
        if (userPassword.length() < 8) {
            return null;
        }

        // 账户不包含特殊字符
        if (!RegexUtils.matchA1_(userAccount)) {
            return null;
        }

        // 密码加密
        String md5Password = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        // 查询用户是否存在
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_account", userAccount);
        userQueryWrapper.eq("user_password", md5Password);
        User user = this.getOne(userQueryWrapper);
        if (user == null) {
            this.log.warn("user login failed, userAccount cannot match userPassword!");
            return null;
        }

        // 用户脱敏
        User safetyUser = getSafetyUser(user);

        // 记录用户登录态
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute(USER_LOGIN_STATE, safetyUser);

        System.out.println(safetyUser);
        // 返回脱敏后用户信息
        return safetyUser;
    }

    @Override
    public List<User> searchUsersByUsername(String username) {
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        if (!StringUtils.isAnyBlank(username)) {
            userQueryWrapper.like("username", username);
        }
        return list(userQueryWrapper);
    }

    @Override
    public User getSafetyUser(User user) {
        User safetyUser = new User();
        safetyUser.setId(user.getId());
        safetyUser.setUsername(user.getUsername());
        safetyUser.setUserAccount(user.getUserAccount());
        safetyUser.setAvatarUrl(user.getAvatarUrl());
        safetyUser.setGender(user.getGender());
        safetyUser.setPhone(user.getPhone());
        safetyUser.setEmail(user.getEmail());
        safetyUser.setUserStatus(user.getUserStatus());
        safetyUser.setCreateTime(user.getCreateTime());
        safetyUser.setUserRole(user.getUserRole());
        return safetyUser;
    }

    @Override
    public int userLogout(HttpServletRequest httpServletRequest) {
        httpServletRequest.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }
}




