package com.itheima.travel.service;

import com.itheima.travel.domain.User;
import com.itheima.travel.exception.UserNotExistsException;

public interface UserService {

    /**
     * 用户注册
     * @param user
     */
    void register(User user) throws Exception;

    /**
     * 注册用户激活
     * @param code
     * @return
     */
    boolean active(String code);

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    User login(String username, String password) throws UserNotExistsException, Exception;
}
