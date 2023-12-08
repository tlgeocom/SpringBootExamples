package com.demo.config;

import com.demo.redis.listener.RedisMessageListener;
import com.demo.redis.listener.RedisMessageReceiver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;

/**
 * <p> @Title LettuceRedisConfig
 * <p> @Description Lettuce Redis客户端配置
 *
 * @author ACGkaka
 * @date 2020/12/9 15:16
 */
@Configuration
public class RedisConfig {

    /**
     * 配置RedisTemplate
     *
     * @param redisConnectionFactory 连接工厂
     * @return RedisTemplate
     */
    @Bean
    public RedisTemplate<String, Serializable> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Serializable> redisTemplate = new RedisTemplate<>();
        //设置key的存储方式为字符串
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //设置为value的存储方式为JDK二进制序列化方式，还有jackson序列化方式（Jackson2JsonRedisSerialize）
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        //设置连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

    /**
     * Redis消息监听器容器（实现方式一）
     *
     * @param redisConnectionFactory    连接工厂
     * @param listener                  消息监听器
     * @return Redis消息监听容器
     */
    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory redisConnectionFactory,
                                                   RedisMessageListener listener) {
        // 订阅主题
        final String TOPIC_NAME_1 = "TEST_TOPIC_1";
        final String TOPIC_NAME_2 = "TEST_TOPIC_2";
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        // 设置连接工厂
        container.setConnectionFactory(redisConnectionFactory);
        // 订阅频道（可以添加多个）
        container.addMessageListener(listener, new PatternTopic(TOPIC_NAME_1));
        container.addMessageListener(listener, new PatternTopic((TOPIC_NAME_2)));

        return container;
    }

    /**
     * Redis消息监听器容器（实现方式二）
     *
     * @param redisConnectionFactory    连接工厂
     * @param adapter                   消息监听器
     * @return Redis消息监听容器
     */
    @Bean
    public RedisMessageListenerContainer container2(RedisConnectionFactory redisConnectionFactory,
                                                    MessageListenerAdapter adapter) {
        // 订阅主题
        final String TOPIC_NAME_1 = "TEST_TOPIC_1";
        final String TOPIC_NAME_2 = "TEST_TOPIC_2";
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        // 设置连接工厂
        container.setConnectionFactory(redisConnectionFactory);
        // 订阅频道（可以添加多个）
        container.addMessageListener(adapter, new PatternTopic(TOPIC_NAME_1));
        container.addMessageListener(adapter, new PatternTopic((TOPIC_NAME_2)));

        return container;
    }

    /**
     * 用于接收消息的消息接收器
     * @param receiver
     * @return
     */
    @Bean
    public MessageListenerAdapter listenerAdapter(RedisMessageReceiver receiver) {
        // receiveMessage 为反射调用，用于接收消息的方法名
        MessageListenerAdapter receiveMessage = new MessageListenerAdapter(receiver, "receiveMessage");
        receiveMessage.setSerializer(new JdkSerializationRedisSerializer());
        return receiveMessage;
    }

}
