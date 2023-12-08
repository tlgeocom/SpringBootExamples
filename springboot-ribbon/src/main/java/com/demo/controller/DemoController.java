package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * <p> @Title DemoController
 * <p> @Description DemoController
 *
 * @author ACGkaka
 * @date 2023/7/3 16:34
 */
@RestController
@RequestMapping("/demo")
public class DemoController {

    /**
     * 服务地址
     */
    private static final String INVOKE_URL = "http://springboot-ribbon";

    @Value("${server.port}")
    private String port;

    @Autowired
    @LoadBalanced
    private RestTemplate restTemplate;

    @GetMapping("/test")
    public String test() {
        return "Hello, Ribbon! port:" + port;
    }

    @GetMapping("/ribbonTest")
    public String ribbonTest() {
        return restTemplate.getForObject(INVOKE_URL + "/demo/test", String.class);
    }
}
