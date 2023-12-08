package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoadBalancerConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(LoadBalancerConsumerApplication.class, args);
    }

}
