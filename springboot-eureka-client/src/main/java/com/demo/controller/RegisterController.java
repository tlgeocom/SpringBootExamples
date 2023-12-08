package com.demo.controller;

import com.demo.common.Result;
import com.netflix.discovery.DiscoveryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p> @Title RegisterController
 * <p> @Description 注册客户端Controller
 *
 * @author ACGkaka
 * @date 2023/7/12 22:16
 */
@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private DiscoveryClient discoveryClient;

    /** 获取服务实例信息 */
    @GetMapping("/getInstance")
    public Result<Object> getInstance() {
        List<ServiceInstance> instances = discoveryClient.getInstances("springboot-eureka-client");
        return Result.succeed().setData(instances);
    }

    /** 获取服务列表 */
    @GetMapping("/getServices")
    public Result<Object> getServices() {
        List<String> services = discoveryClient.getServices();
        return Result.succeed().setData(services);
    }

    /** 服务下线 */
    @GetMapping("/offline")
    public Result<Object> offline() {
        DiscoveryManager.getInstance().shutdownComponent();
        return Result.succeed();
    }

}
