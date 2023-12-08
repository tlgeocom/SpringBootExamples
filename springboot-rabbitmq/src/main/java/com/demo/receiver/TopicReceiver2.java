package com.demo.receiver;

import com.demo.config.RabbitTopicConfig;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * <p> @Title TopicReceiver2
 * <p> @Description 主题交换机监听类2
 *
 * @author zhj
 * @date 2023/1/16 5:55
 */
@Component
@RabbitListener(queues = RabbitTopicConfig.TOPIC_QUEUE_NAME_2)
public class TopicReceiver2 {

    @RabbitHandler
    public void process(String message) {
        System.out.println("接收者 TopicReceiver1，消息内容：" + message);
    }
}
