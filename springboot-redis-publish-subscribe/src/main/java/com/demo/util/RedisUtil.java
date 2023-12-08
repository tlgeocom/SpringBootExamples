package com.demo.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * <p> @Title RedisUtil
 * <p> @Description Redis工具类
 *
 * @author ACGkaka
 * @date 2021/6/16 16:32
 */
@Slf4j
@Component
public class RedisUtil {

    @Qualifier("redisTemplate")
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 向频道发布消息
     * @param channel   频道
     * @param message   消息
     * @return true成功 false失败
     */
    public boolean publish(String channel, Object message) {
        if (!StringUtils.hasText(channel)) {
            return false;
        }
        try {
            redisTemplate.convertAndSend(channel, message);
            log.info("发送消息成功，channel:{}, message:{}", channel, message);
            return true;
        } catch (Exception e) {
            log.error("发送消息失败，channel:{}, message:{}", channel, message, e);
        }
        return false;
    }

}
