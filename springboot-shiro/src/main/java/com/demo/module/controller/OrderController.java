package com.demo.module.controller;

import com.demo.common.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> @Title OrderController
 * <p> @Description 订单控制器
 *
 * @author ACGkaka
 * @date 2023/10/15 17:15
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    /**
     * 新增订单（代码实现权限判断）
     */
    @GetMapping("/add")
    public Result<Object> add() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.hasRole("admin")) {
            return Result.succeed().setData("新增订单成功");
        } else {
            return Result.failed().setData("操作失败，无权访问");
        }
    }

    /**
     * 编辑订单（注解实现权限判断）
     */
    @RequiresRoles({"admin", "user"}) // 用来判断角色，同时拥有admin和user角色才能访问
    @RequiresPermissions("user:edit:01") // 用来判断权限，拥有user:edit:01权限才能访问
    @GetMapping("/edit")
    public String edit() {
        System.out.println("编辑订单");
        return "redirect:/index";
    }
}
