package com.demo.feign;

import com.demo.common.Result;
import com.demo.feign.fallback.HystrixTestFeignClientFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <p> @Title HystrixTestFeign
 * <p> @Description Hystrix测试Feign
 *
 * @author ACGkaka
 * @date 2023/4/23 13:42
 */
@FeignClient(name = "hystrix-demo", fallbackFactory = HystrixTestFeignClientFallBack.class)
public interface HystrixTestFeignClient {

    @GetMapping("/hystrixFeign/test1")
    Result<Object> test1();

    @GetMapping("/hystrixFeign/test")
    Result<Object> test();

}
