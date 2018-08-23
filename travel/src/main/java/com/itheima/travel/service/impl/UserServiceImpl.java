package com.itheima.travel.service.impl;

import com.itheima.travel.domain.User;
import com.itheima.travel.exception.*;
import com.itheima.travel.mapper.UserMapper;
import com.itheima.travel.service.UserService;
import com.itheima.travel.utils.MailUtil;
import com.itheima.travel.utils.Md5Util;
import com.itheima.travel.utils.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public void register(User user) throws Exception {
        //数据验证,用户名不能为空
        if(user.getUsername()==null||"".equals(user.getUsername())){
            //抛出自定义异常
            throw new UserNameNotNullException("用户名不能为空");
        }

        //判断用户名是否已经被注册
        User dbUser = userMapper.getUserByUserName(user.getUsername());
        if(dbUser!=null){
            //抛出自定义异常
            throw new UserExistsException("该用户名以存在");
        }

        //封装业务字段-激活状态为未激活
        user.setStatus("N");
        //封装业务字段-激活码(唯一,uuid)
        user.setCode(UuidUtil.getUuid());
        //对密码进行加密
        user.setPassword(Md5Util.encodeByMd5(user.getPassword()));
        //发送激活邮件
        MailUtil.sendMail(user.getEmail(),
                "<a href='http://localhost:8080/user/active?code=" + user.getCode() + "'>用户激活</a>");


        //注册用户添加用户
        userMapper.addUser(user);
    }

    @Override
    public boolean active(String code) {
        int row = userMapper.active(code);
        return row>0;

    }

    @Override
    public User login(String username, String password) throws Exception {
        //数据校验
        if(username==null || username.equalsIgnoreCase("")){
            throw new UserNameNotNullException("用户名不能为空");
        }
        //判断用户名是否存在
        User dbUser = userMapper.getUserByUserName(username);
        if(dbUser==null){
            throw new UserNotExistsException("用户名不存在");
        }
        //判断密码是否正确
        if(!dbUser.getPassword().equals(password)){
            throw new PasswordErrorException("密码错误");
        }
        //用户是否已激活
        if(dbUser.getStatus().equals("N")){
            throw new UserNoActiveException("用户未激活");
        }
        return dbUser;

    }
}
