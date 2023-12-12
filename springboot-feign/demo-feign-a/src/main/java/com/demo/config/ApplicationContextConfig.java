package com.demo.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootConfiguration//声明一个配置类
public class ApplicationContextConfig {


    //重点 模板类
    //RestTemplate 只能进行远程调用
    @Bean
    @LoadBalanced //RestTemplate + Ribbon 实现远程调用和负载均衡(轮询)
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
