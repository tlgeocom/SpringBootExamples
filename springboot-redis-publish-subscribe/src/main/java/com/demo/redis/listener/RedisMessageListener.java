package com.demo.redis.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * <p> @Title RedisMessageListener
 * <p> @Description Redis消息监听器（实现方式一）
 *
 * @author ACGkaka
 * @date 2023/10/7 15:55
 */
@Slf4j
@Component
public class RedisMessageListener implements MessageListener {

    @Qualifier("redisTemplate")
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void onMessage(Message message, byte[] pattern) {
        // 打印渠道
        log.info(">>>>>>>>>> 【INFO】订阅的channel：{}", new String(pattern));

        // 获取消息
        byte[] messageBody = message.getBody();
        // 序列化对象
        MessageDTO messageDTO = (MessageDTO) redisTemplate.getValueSerializer().deserialize(messageBody);

        // 打印消息
        log.info(">>>>>>>>>> 【INFO】收到的message：{}", messageDTO);
    }
}
