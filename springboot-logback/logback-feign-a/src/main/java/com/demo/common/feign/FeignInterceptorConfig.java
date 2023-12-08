package com.demo.common.feign;

import com.demo.constant.TraceConstant;
import feign.RequestInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p> @Title DemoFeignClient
 * <p> @Description Feign调用配置
 *
 * @author ACGkaka
 * @date 2023/7/2 10:59
 */
@Configuration
public class FeignInterceptorConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        RequestInterceptor requestInterceptor = template -> {
            // 传递traceId
            String traceId = MDC.get(TraceConstant.LOG_TRACE_ID);
            if (StringUtils.isNotBlank(traceId)) {
                template.header(TraceConstant.LOG_B3_TRACE_ID, traceId);
            }
        };

        return requestInterceptor;
    }
}
