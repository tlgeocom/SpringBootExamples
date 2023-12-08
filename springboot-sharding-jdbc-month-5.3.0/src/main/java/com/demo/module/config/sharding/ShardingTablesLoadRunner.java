package com.demo.module.config.sharding;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.demo.module.entity.User;
import com.demo.module.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * <p> @Title ShardingTablesLoadRunner
 * <p> @Description 项目启动后，读取已有分表，进行缓存
 *
 * @author ACGkaka
 * @date 2022/12/20 15:41
 */
@Slf4j
@Order(value = 1) // 数字越小，越先执行
@Component
public class ShardingTablesLoadRunner implements CommandLineRunner {

    @Resource
    private UserService userService;

    @Override
    public void run(String... args) {
        // 读取已有分表，进行缓存
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getCreateTime, LocalDateTime.now()).last("limit 1");
        userService.list(queryWrapper);

        log.info(">>>>>>>>>> 【ShardingTablesLoadRunner】缓存已有分表成功 <<<<<<<<<<");
    }
}
