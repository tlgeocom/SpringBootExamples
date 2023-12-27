package com.demo.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URL;


@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class DemoServiceImplTest {

    @Test
    public void test() {

        log.info("123456");
        // 加载动态库
        URL url = ClassLoader.getSystemResource("lib/opencv/opencv_java440.dll");
        System.load(url.getPath());

    }
}