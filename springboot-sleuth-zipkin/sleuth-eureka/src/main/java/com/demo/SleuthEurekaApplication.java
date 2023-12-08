package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class SleuthEurekaApplication {

    public static void main(String[] args) {
        // 启动应用
        SpringApplication.run(SleuthEurekaApplication.class, args);
    }

}
