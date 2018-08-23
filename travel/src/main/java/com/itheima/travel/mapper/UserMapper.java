package com.itheima.travel.mapper;

import com.itheima.travel.domain.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {

    /**
     * 根据用户名去数据库查找用户对象
     * @param username
     * @return
     */
    User getUserByUserName(@Param("username") String username);

    /**
     * 注册添加用户
     * @param user
     */
    void addUser(User user);

    int active(@Param("code") String code);
}
