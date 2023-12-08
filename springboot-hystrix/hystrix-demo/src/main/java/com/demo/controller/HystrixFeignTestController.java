package com.demo.controller;

import com.demo.feign.HystrixTestFeignClient;
import com.demo.common.Result;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> @Title HystrixFeignTestController
 * <p> @Description Hystrix测试Controller
 *
 * @author ACGkaka
 * @date 2023/4/23 14:17
 */
@RestController
@RequestMapping("/hystrixFeign")
public class HystrixFeignTestController {

    @Autowired
    private HystrixTestFeignClient hystrixTestFeignClient;

    /**
     * 测试feign调用
     */
    @GetMapping("/testFeign")
    public Result<Object> testFeign() {
        long start = System.currentTimeMillis();
        Result<Object> result = hystrixTestFeignClient.test();
        long end = System.currentTimeMillis();
        return result.setMessage(result.getMessage() + "，调用时间：" + (end - start) / 1000 + "秒");
    }

    /**
     * 测试feign调用
     */
    @GetMapping("/test1Feign")
    public Result<Object> test1Feign() {
        long start = System.currentTimeMillis();
        Result<Object> result = hystrixTestFeignClient.test1();
        long end = System.currentTimeMillis();
        return result.setMessage(result.getMessage() + "，调用时间：" + (end - start) / 1000 + "秒");
    }

    /**
     * 测试正常方法
     */
    @GetMapping("/test")
    public Result<Object> test() throws InterruptedException {
        // 配置中指定了默认15秒超时，这里设置了20秒超时，所以会调用熔断方法
        int i = 0;
        for (; i < 20; i++) {
            System.out.println(i + 1 + "秒");
            Thread.sleep(1000);
        }
        return Result.succeed("测试成功，等待时间：" + i + "秒");
    }

    /**
     * 直接抛出异常
     */
    @HystrixCommand
    @GetMapping("/test1")
    public Result<Object> test1() {
        throw new RuntimeException("测试异常");
    }

}
