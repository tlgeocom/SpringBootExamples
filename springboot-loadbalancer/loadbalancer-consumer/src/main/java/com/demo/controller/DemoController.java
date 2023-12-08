package com.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * <p> @Title DemoController
 * <p> @Description 测试Controller
 *
 * @author ACGkaka
 * @date 2023/4/24 18:02
 */
@Slf4j
@RestController
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/consumeTest")
    public Object test() {
        log.info(">>>>>>>>>>【INFO】DemoController.consumeTest()...");
        String url = "http://loadbalancer-producer/demo/produceTest";
        return restTemplate.getForObject(url, Object.class);
    }
}
