package com.demo.service.impl;

import com.demo.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p> @Title DemoServiceImpl
 * <p> @Description 测试ServiceImpl
 *
 * @author ACGkaka
 * @date 2023/4/24 18:14
 */
@Slf4j
@Service
public class DemoServiceImpl implements DemoService {

    @Override
    public int test() {
        log.info(">>>>>>>>>>【INFO】DemoService.test()...");
        return 0;
    }
}
