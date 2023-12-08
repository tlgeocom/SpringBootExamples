package com.demo.feign.fallback;

import com.demo.feign.HystrixTestFeignClient;
import com.demo.common.Result;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * <p> @Title HystrixTestFeignClientFallBack
 * <p> @Description Hystrix测试Feign熔断类
 *
 * @author ACGkaka
 * @date 2023/4/23 13:55
 */
@Component
public class HystrixTestFeignClientFallBack implements FallbackFactory<HystrixTestFeignClient> {

    @Override
    public HystrixTestFeignClient create(Throwable cause) {
        return new HystrixTestFeignClient() {
            @Override
            public Result<Object> test1() {
                return Result.failed("调用失败，原因：" + cause.getMessage());
            }

            @Override
            public Result<Object> test() {
                return Result.failed("调用失败，原因：" + cause.getMessage());
            }
        };
    }
}
