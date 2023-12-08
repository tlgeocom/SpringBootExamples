package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import uk.org.lidalia.sysoutslf4j.context.SysOutOverSLF4J;

@EnableEurekaServer
@SpringBootApplication
public class LogbackEurekaApplication {

    public static void main(String[] args) {
        // 加入这行代码时system.out输出为info日志
        SysOutOverSLF4J.sendSystemOutAndErrToSLF4J();
        // 启动应用
        SpringApplication.run(LogbackEurekaApplication.class, args);
    }

}
