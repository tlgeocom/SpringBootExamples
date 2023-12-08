package com.demo.redis.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <p> @Title RedisMessageReceiver
 * <p> @Description Redis消息接收器（实现方式二）
 *
 * @author ACGkaka
 * @date 2023/10/7 18:28
 */
@Slf4j
@Component
public class RedisMessageReceiver {

    /**
     * 接收消息（在 RedisConfig.java 中反射调用）
     */
    public void receiveMessage(MessageDTO messageDTO, String channel) {
        // 打印渠道
        log.info(">>>>>>>>>> 【INFO】订阅的channel：{}", channel);

        // 打印消息
        log.info(">>>>>>>>>> 【INFO】收到的message：{}", messageDTO);
    }
}
