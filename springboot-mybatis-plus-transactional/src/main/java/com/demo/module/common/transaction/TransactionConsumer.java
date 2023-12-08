package com.demo.module.common.transaction;

import org.springframework.transaction.annotation.Transactional;

/**
 * <p> @Title TransactionConsumer
 * <p> @Description 事务处理器
 *
 * @author ACGkaka
 * @date 2023/4/28 12:06
 */
@FunctionalInterface
public interface TransactionConsumer {

    /**
     * 事务中处理
     */
    @Transactional
    void doInTransaction();
}
