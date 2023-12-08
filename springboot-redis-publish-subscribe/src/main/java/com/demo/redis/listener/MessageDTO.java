package com.demo.redis.listener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p> @Title MessageDTO
 * <p> @Description 消息传输对象
 *
 * @author ACGkaka
 * @date 2023/10/7 16:03
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO implements Serializable {

    /**
     * 消息标题
     */
    private String title;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 消息内容
     */
    private LocalDateTime createTime;
}
