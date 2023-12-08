package com.demo.controller;

import com.demo.common.Result;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> @Title HystrixTestController
 * <p> @Description Hystrix测试Controller
 *
 * @author ACGkaka
 * @date 2023/4/7 21:14
 */
@RestController
@RequestMapping("/hystrix")
// 指定默认熔断方法
@DefaultProperties(defaultFallback = "defaultFallback")
public class HystrixTestController {

    /**
     * 用法1：直接使用@HystrixCommand注解，使用默认熔断方法
     */
    @HystrixCommand
    @GetMapping("/test1")
    public Result<Object> test1() {
        throw new RuntimeException("测试异常");
    }

    /**
     * 用法2：使用@HystrixCommand注解，指定熔断方法和超时时间
     */
    @HystrixCommand(
            fallbackMethod = "myFallback", // 降级的回调方法
            commandProperties = {
                    // 设置超时时间
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
            })
    @GetMapping("/test2")
    public Result<Object> test2() throws InterruptedException {
        int i = 0;
        for (; i < 10; i++) {
            System.out.println(i + 1 + "秒");
            Thread.sleep(1000);
        }
        return Result.succeed("测试成功，等待时间：" + i + "秒");
    }

    /**
     * 该方法是一个熔断方法，当方法出现异常时，会调用该方法
     */
    public Result<Object> defaultFallback() {
        return Result.failed("@DefaultProperties指定的熔断方法");
    }

    /**
     * 该方法是一个熔断方法，当方法出现异常时，会调用该方法
     */
    public Result<Object> myFallback() {
        return Result.failed("@HystrixCommand指定的熔断方法");
    }
}
