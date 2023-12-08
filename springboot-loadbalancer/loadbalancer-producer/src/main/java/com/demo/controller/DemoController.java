package com.demo.controller;

import com.demo.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Value("${server.port}")
    private String port;

    @RequestMapping("/produceTest")
    public Result<Object> produceTest() {
        Result<Object> result = Result.succeed();
        log.info(">>>>>>>>>>【INFO】DemoController.produceTest()...");
        return result.setData("Hello, I'm from port: " + port);
    }
}
