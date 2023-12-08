package com.demo.controller;

import com.demo.common.Result;
import com.demo.common.feign.FeignBFeignClient;
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
    private FeignBFeignClient feignBFeignClient;

    @GetMapping("/test")
    public Result<Object> test() {
        String data = "This is a test! port:" + port;
        log.info(">>>>>>>>>> 【INFO】data:{}", data);
        return Result.succeed().setData(data);
    }

    @GetMapping("/feignTest")
    public Result<Object> feignTest() {
        log.info(">>>>>>>>>> 【INFO】feignTest");
        return feignBFeignClient.test();
    }
}
