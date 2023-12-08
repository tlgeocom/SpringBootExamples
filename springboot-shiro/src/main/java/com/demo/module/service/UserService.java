package com.demo.module.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.demo.module.entity.User;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author ACGkaka
 * @since 2021-04-25
 */
public interface UserService extends IService<User> {

    /**
     * 注册
     * @param user 用户
     */
    void register(User user);

    /**
     * 根据用户名查询用户
     * @param principal 用户名
     * @return 用户
     */
    User findByUsername(String principal);
}
