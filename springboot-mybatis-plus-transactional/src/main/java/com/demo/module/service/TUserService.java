package com.demo.module.service;

import com.demo.module.entity.TUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author ACGkaka
 * @since 2021-04-25
 */
public interface TUserService extends IService<TUser> {

    /**
     * 测试 @Transactional 注解
     */
    void testTransactional();

    /**
     * 测试不加 @Transactional 注解（方式一：采用编程式事务）
     */
    void testWithoutTransactional();

    /**
     * 测试不加 @Transactional 注解（方式二：采用函数式接口）
     */
    void testWithoutTransactionalPlus();

    /**
     * 添加用户A
     */
    void addUserA();

    /**
     * 添加用户B
     */
    void addUserB();
}
