package com.demo.config;

import com.demo.filter.TraceContextFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * <p> @Title TraceFilterConfig
 * <p> @Description Trace过滤器配置
 *
 * @author ACGkaka
 * @date 2023/7/23 0:41
 */
@Configuration
public class TraceFilterConfig {

    @Bean
    public FilterRegistrationBean<TraceContextFilter> traceContextFilterRegistrationBean() {
        FilterRegistrationBean<TraceContextFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new TraceContextFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }
}
