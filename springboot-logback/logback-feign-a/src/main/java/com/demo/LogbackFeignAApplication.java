package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import uk.org.lidalia.sysoutslf4j.context.SysOutOverSLF4J;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class LogbackFeignAApplication {

    public static void main(String[] args) {
        // 加入这行代码时system.out输出为info日志
        SysOutOverSLF4J.sendSystemOutAndErrToSLF4J();
        // 启动应用
        SpringApplication.run(LogbackFeignAApplication.class, args);
    }

}
