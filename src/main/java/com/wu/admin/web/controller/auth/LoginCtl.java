package com.wu.admin.web.controller.auth;

import com.wu.admin.pojo.auth.ao.LoginAO;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;

/**
 * Description : Created by intelliJ IDEA
 * Author : wubo
 * Date : 2017/10/12
 * Time : 上午10:13
 */
@Controller
public class LoginCtl {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "/toLogin", method = RequestMethod.GET)
    public String toLogin() {
        try {
            Boolean isAuth = SecurityUtils.getSubject().isAuthenticated();
            if (isAuth) {
                return "redirect:/index";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@Valid LoginAO loginForm, BindingResult result) {
        if (result.hasErrors()) {
            return "login";
        }
        UsernamePasswordToken token = new UsernamePasswordToken(loginForm.getUserName(), loginForm.getPassword());
        Subject subject = SecurityUtils.getSubject();
        subject.login(token);
        if (subject.isAuthenticated()) {
            //登录成功，数据初始化
            //1，session信息，用户名，上次登录时间信息
            //2，菜单列表信息
            //3，系统该要信息，待办事项
            return "redirect:/index";
        } else {
            token.clear();
            return "login";
        }
    }
}
