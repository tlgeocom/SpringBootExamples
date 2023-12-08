package com.demo;

import com.yomahub.tlog.core.enhance.bytes.AspectLogEnhance;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class TLogFeignBApplication {

    //进行日志增强，自动判断日志框架
    static {AspectLogEnhance.enhance();}

    public static void main(String[] args) {
        SpringApplication.run(TLogFeignBApplication.class, args);
    }

}
