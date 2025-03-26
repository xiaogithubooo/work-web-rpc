package com.easyprovider.provider;

import com.work.easycommon.entity.User;
import com.work.easycommon.service.UserService;

/**
 * 用户服务实现
 */
public class UserServiceImpl implements UserService {

    public User getUser(User user) {
        System.out.println("用户名：" + user.getName());
        return user;
    }

}
