package com.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {

    @Autowired
    private RestTemplate restTemplate;

    //@Bean
    //@LoadBalanced
    //public RestTemplate restTemplate() {
    //    return new RestTemplate();
    //}

    @GetMapping("/hello")
    public String hello(@RequestParam String name) {
        String url = "http://provider/hello?name=" + name;
        return restTemplate.getForObject(url, String.class);
    }
}
