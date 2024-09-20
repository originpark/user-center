package com.origin.usercenter.controller;

import com.origin.usercenter.common.BaseResponse;
import com.origin.usercenter.common.ResponseType;
import com.origin.usercenter.common.ResponseUtils;
import com.origin.usercenter.exception.BusinessException;
import com.origin.usercenter.model.domain.User;
import com.origin.usercenter.model.request.UserLoginRequest;
import com.origin.usercenter.model.request.UserRegisterRequest;
import com.origin.usercenter.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;


import static com.origin.usercenter.constant.UserConstant.ADMIN_ROLE;
import static com.origin.usercenter.constant.UserConstant.USER_LOGIN_STATE;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            throw new BusinessException(ResponseType.NULL_ERROR, "请求体为空");
        }

        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();

        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ResponseType.PARAM_ERROR, "登录账号/密码/二次密码存为空");
        }

        Long result = userService.userRegister(userAccount, userPassword, checkPassword);
        return ResponseUtils.success(result);
    }

    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest httpServletRequest) {
        if (userLoginRequest == null) {
            throw new BusinessException(ResponseType.NULL_ERROR, "登录账号/密码/二次密码存为空");
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();


        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ResponseType.PARAM_ERROR, "登录账号,密码为空");
        }

        User user = userService.userLogin(userAccount, userPassword, httpServletRequest);
        return ResponseUtils.success(user);
    }

    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest httpServletRequest) {
        if (httpServletRequest == null) {
            throw new BusinessException(ResponseType.SYSTEM_ERROR, "");
        }
        int result = userService.userLogout(httpServletRequest);
        return ResponseUtils.success(result);
    }

    @GetMapping("/search")
    public BaseResponse<List<User>> searchUser(String username, HttpServletRequest httpServletRequest) {

        // 仅管理员
        if (!is_admin(httpServletRequest)) {
            throw new BusinessException(ResponseType.NO_AUTH, "无权查看用户列表");
        }


        List<User> userList = userService.searchUsersByUsername(username);
        userList = userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());

        return ResponseUtils.success(userList);

    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody long id, HttpServletRequest httpServletRequest) {

        // 仅管理员
        if (!is_admin(httpServletRequest)) {
            throw new BusinessException(ResponseType.NO_AUTH, "无权删除用户");
        }

        if (id <= 0) {
            throw new BusinessException(ResponseType.PARAM_ERROR, "要删除的id < 0");
        }
        boolean result = userService.removeById(id);
        if (result) {
            return ResponseUtils.success(true);
        } else {
            throw new BusinessException(ResponseType.PARAM_ERROR, "不存在id为" + id + "的用户");
        }
    }

    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest httpServletRequest) {
        Object userObj = httpServletRequest.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null) {
            throw new BusinessException(ResponseType.NULL_ERROR, "当前用户不存在");
        }
        Long userId = currentUser.getId();
        User user = userService.getById(userId);
        User safetyUser = userService.getSafetyUser(user);
        return ResponseUtils.success(safetyUser);
    }

    private boolean is_admin(HttpServletRequest httpServletRequest) {
        Object userObj = httpServletRequest.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User) userObj;
        return user != null && user.getUserRole() == ADMIN_ROLE;
    }
}
