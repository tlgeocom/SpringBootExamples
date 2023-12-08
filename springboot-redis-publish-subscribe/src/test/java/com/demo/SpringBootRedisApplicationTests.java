package com.demo;

import com.demo.redis.listener.MessageDTO;
import com.demo.util.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRedisApplicationTests {

    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void test1() {
        // 订阅主题
        final String TOPIC_NAME_1 = "TEST_TOPIC_1";
        final String TOPIC_NAME_2 = "TEST_TOPIC_2";
        // 发布消息
        MessageDTO dto = new MessageDTO("测试标题", "测试内容", LocalDateTime.now());
        redisUtil.publish(TOPIC_NAME_1, dto);
    }

}
