package com.origin.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.origin.usercenter.model.domain.User;
import com.origin.usercenter.service.UserService;
import com.origin.usercenter.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author wild hearts
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2024-09-18 00:08:21
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




