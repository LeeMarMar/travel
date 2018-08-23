package com.itheima.travel.controller;

import com.itheima.travel.domain.ResultInfo;
import com.itheima.travel.domain.User;
import com.itheima.travel.exception.*;
import com.itheima.travel.service.UserService;
import com.itheima.travel.utils.Md5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 推出登陆
     * @param session
     * @return
     */
    @RequestMapping("loginout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/login.html";
    }


    /**
     * 获取用户登陆信息
     * @param session
     * @return
     */
    @RequestMapping("getLoginUserData")
    @ResponseBody
    public ResultInfo getLoginUserData(HttpSession session) {
        // 实例返回结果对象
        ResultInfo resultInfo = null;

        // 从session里面获取登录的用户数据
        User user = (User) session.getAttribute("loginUser");
        if (user == null) {
            // 说明用户没有登录
            resultInfo = new ResultInfo(false, null, null);
        } else {
            // 说明用户登录
            resultInfo = new ResultInfo(true, user, null);
        }
        return resultInfo;
    }

    @RequestMapping("login")
    @ResponseBody
    public ResultInfo login(HttpSession session, @RequestParam("username") String username,
                            @RequestParam("password") String password) {
        ResultInfo resultInfo = null;
        try {
            // 加密密码，之后再去数据库对比
            password = Md5Util.encodeByMd5(password);

            // 调用业务进行登录
            User loginUser = userService.login(username, password);
            // 登录成功，返回成功信息
            if (loginUser != null) {
                // 登录成功，将登录用户数据写入session
                session.setAttribute("loginUser", loginUser);
                resultInfo = new ResultInfo(true, null, null);
            }

        } catch (UserNoActiveException e) {
            e.printStackTrace();// 让开发人员看
            resultInfo = new ResultInfo(false, null, e.getMessage());
        } catch (PasswordErrorException e) {
            e.printStackTrace();// 让开发人员看
            resultInfo = new ResultInfo(false, null, e.getMessage());
        } catch (UserNotExistsException e) {
            e.printStackTrace();// 让开发人员看
            resultInfo = new ResultInfo(false, null, e.getMessage());
        } catch (UserNameNotNullException e) {
            e.printStackTrace();// 让开发人员看
            resultInfo = new ResultInfo(false, null, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            resultInfo = new ResultInfo(false, null, "服务器忙");
        }
        return resultInfo;
    }


    /**
     * 用户激活
     * @param code
     * @return
     */
    @RequestMapping("active")
    public String active(@RequestParam("code") String code){
        try {
            // 调用业务进行激活
            boolean flag = userService.active(code);
            // 返回结果
            if (flag) {
                // 激活成功，跳转页面到login.html
                return "redirect:/login.html";
            } else {
                // 激活失败,输出失败
                return "redirect:/error/500.html";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "redirect:/error/500.html";
        }
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @RequestMapping("register")
    @ResponseBody
    public ResultInfo register(User user, @RequestParam("check")String userCheckCode, HttpSession session){
        ResultInfo resultInfo = null;

            try {
                // 验证验证码
                // 获取用户输入的验证码
                // 获取服务器端生成的验证码
                String serverCheckCode = (String) session.getAttribute("CHECKCODE_SERVER");
                if (!serverCheckCode.equalsIgnoreCase(userCheckCode)) {
                    // 验证失败
                    resultInfo = new ResultInfo(false, null, "验证码错误");
                } else {
                    // 调用业务逻辑方法实现注册功能
                    userService.register(user);
                    // 获取注册结果，封装返回结果对象ResultInfo
                    resultInfo = new ResultInfo(true, null, null);
                }

            } catch (UserNameNotNullException e) {
                e.printStackTrace();
                resultInfo = new ResultInfo(false, null, "用户名不能为空");
            }catch (UserExistsException e){
                e.printStackTrace();
                resultInfo = new ResultInfo(false, null, "用户名已经存在");
            }catch (Exception e){
                e.printStackTrace();
                resultInfo = new ResultInfo(false, null, "服务器忙,请稍后再试");
            }

            return resultInfo;
        }


    }
