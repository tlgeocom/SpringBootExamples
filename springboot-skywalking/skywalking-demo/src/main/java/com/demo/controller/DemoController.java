package com.demo.controller;

import com.demo.common.Result;
import lombok.extern.slf4j.Slf4j;
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

    @RequestMapping("/test")
    public Result<Object> test() {
        log.info(">>>>>>>>>>【INFO】DemoController.test()...");
        return Result.succeed();
    }
}
