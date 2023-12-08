package com.demo.common.feign;

import com.demo.common.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <p> @Title DemoFeignClient
 * <p> @Description DemoFeignClient
 *
 * @author ACGkaka
 * @date 2023/7/2 10:59
 */
@FeignClient(value = "logback-feign-sleuth-a")
public interface FeignSleuthAFeignClient {

    @GetMapping("/demo/test")
    Result<Object> test();
}
