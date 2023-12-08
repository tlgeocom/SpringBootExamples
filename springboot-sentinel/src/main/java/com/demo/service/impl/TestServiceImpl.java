package com.demo.service.impl;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.demo.service.TestService;
import org.springframework.stereotype.Service;

/**
 * <p> @Title TestServiceImpl
 * <p> @Description 测试Service
 *
 * @author ACGkaka
 * @date 2023/3/26 21:51
 */
@Service
public class TestServiceImpl implements TestService {

    @SentinelResource(value = "sayHello")
    public String sayHello(String name) {
        // 测试方法
        return "Hello, " + name;
    }

}
