package com.demo.module.service.impl;

import com.demo.module.common.transaction.TransactionConsumer;
import com.demo.module.entity.TUser;
import com.demo.module.mapper.TUserMapper;
import com.demo.module.service.TUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author ACGkaka
 * @since 2021-04-25
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements TUserService {

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void testTransactional() {
        // 测试 @Transactional 注解

        addUserA();

        // 事务提交后执行
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void beforeCommit(boolean readOnly) {
                System.out.println("事务提交前执行");
            }

            @Override
            public void afterCommit() {
                System.out.println("事务提交后执行");
                List<TUser> list = list();
                System.out.println("list2 = " + list);
            }
        });

        System.out.println("方法末尾执行");
        List<TUser> list = list();
        System.out.println("list1 = " + list);

    }

    @Override
    public void testWithoutTransactional() {
        // 测试不加 @Transactional 注解（方式一：采用编程式事务）
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                // 业务逻辑
                addUserA();
                System.out.println("事务提交前执行");
                List<TUser> list = list();
                System.out.println("list1 = " + list);

                // 测试事务回滚
//                int i = 1 / 0;
            }
        });

        // 方法末尾执行
        System.out.println("方法末尾执行");
        List<TUser> list = list();
        System.out.println("list2 = " + list);
    }

    @Override
    public void testWithoutTransactionalPlus() {
        // 测试不加 @Transactional 注解（方式二：采用函数式接口）
        TransactionConsumer transactionConsumer = () -> {
            // 业务逻辑
            addUserA();
            System.out.println("事务提交前执行");
            List<TUser> list = list();
            System.out.println("list1 = " + list);

            // 测试事务回滚（亲测事务不回滚）
            int i = 1 / 0;
        };
        transactionConsumer.doInTransaction();

        // 方法末尾执行
        System.out.println("方法末尾执行");
        List<TUser> list = list();
        System.out.println("list2 = " + list);
    }

    @Override
    public void addUserA() {
        // 添加用户A
        TUser user = new TUser(null, "username_aaa", "pwd_aaa", LocalDateTime.now(), LocalDateTime.now());
        this.save(user);
    }

    @Override
    public void addUserB() {
        // 添加用户B
        TUser user = new TUser(null, "username_bbb", "pwd_bbb", LocalDateTime.now(), LocalDateTime.now());
        this.save(user);
    }
}
