package com.demo.controller;

import com.demo.common.Result;
import com.demo.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.ToIntFunction;

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
    private DemoService demoService;

    @RequestMapping("/test")
    public Result<Object> test() throws InterruptedException {
        Result<Object> result = Result.succeed();
        log.info(">>>>>>>>>>【INFO】DemoController.test()...");
        int msg = demoService.test();
        return result.setData(msg);
    }
}
