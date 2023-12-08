package com.demo.module.controller;

import com.demo.module.entity.User;
import com.demo.module.service.UserService;
import lombok.AllArgsConstructor;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;

/**
 * <p> @Title IndexController
 * <p> @Description 鉴权Controller
 *
 * @author ACGkaka
 * @date 2019/10/23 20:23
 */
@Controller
@AllArgsConstructor
public class AuthController {

    @Resource
    private UserService userService;

    /**
     * 默认跳转主页
     */
    @GetMapping("/")
    public String showIndex() {
        return "redirect:/index";
    }

    /**
     * 主页
     */
    @GetMapping("/index")
    public String index() {
        return "index.html";
    }

    /**
     * 主页
     */
    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    /**
     * 注册页
     */
    @GetMapping("/register")
    public String register() {
        return "/register.html";
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    public String login(String username, String password) {
        // 获取主题对象
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(username, password));
            System.out.println("登录成功！！！");
            return "redirect:/index";
        } catch (UnknownAccountException e) {
            System.out.println("用户错误！！！");
        } catch (IncorrectCredentialsException e) {
            System.out.println("密码错误！！！");
        }
        return "redirect:/login";
    }

    /**
     * 注册
     */
    @PostMapping("/register")
    public String register(User user) {
        userService.register(user);
        return "redirect:/login.html";
    }

    /**
     * 退出登录
     */
    @GetMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "redirect:/login.html";
    }
}
