package com.demo.filter;

import com.demo.constant.TraceConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * <p> @Title TraceContextFilter
 * <p> @Description Trace跟踪过滤器
 *
 * @author ACGkaka
 * @date 2023/7/23 0:34
 */
@Slf4j
public class TraceContextFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            log.info("<><><><><> 【INFO】uri:{}", httpServletRequest.getRequestURI());

            // 传递traceId
            String traceId = httpServletRequest.getHeader(TraceConstant.LOG_B3_TRACE_ID);
            if (StringUtils.isNotEmpty(traceId)) {
                MDC.put(TraceConstant.LOG_TRACE_ID, traceId);
            } else {
                // 填充数据（适用logback、log4j 1.x）
                MDC.put(TraceConstant.LOG_TRACE_ID, UUID.randomUUID().toString());
                // 填充数据（适用log4j 2.x）
                // ThreadContext.put(Contents.REQUEST_ID, UUID.randomUUID().toString());
            }

            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } finally {
            // 请求结束时清除数据，否则会造成内存泄露问题
            MDC.remove("traceId");
        }
    }

}
