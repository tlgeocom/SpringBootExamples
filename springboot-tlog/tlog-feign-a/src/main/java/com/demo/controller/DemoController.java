package com.demo.controller;

import com.demo.common.Result;
import com.demo.feign.DemoFeignClient;
import com.yomahub.tlog.core.annotation.TLogAspect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p> @Title DemoController
 * <p> @Description DemoController
 *
 * @author ACGkaka
 * @date 2023/7/2 13:18
 */
@Slf4j
@RestController
@RequestMapping("/demo")
public class DemoController {

    @Value("${server.port:}")
    private String port;

    @Autowired
    private DemoFeignClient demoFeignClient;

    @GetMapping("/test")
    public Result<Object> test() {
        log.info("这是第一条日志。port:" + port);
        log.info("这是第二条日志。port:" + port);
        log.info("这是第三条日志。port:" + port);
        return Result.succeed();
    }

    @GetMapping("/feignTest")
    public Result<Object> feignTest() {
        log.info(">>>>>>>>>> 【INFO】feignTest");
        return demoFeignClient.test();
    }

    @TLogAspect({"id","name"})
    @GetMapping("/tlogTest")
    public Result<Object> tlogTest(String id,String name){
        log.info("这是第一条日志");
        log.info("这是第二条日志");
        log.info("这是第三条日志");
        new Thread(() -> log.info("这是异步日志")).start();
        return Result.succeed();
    }
}
