package com.demo.config;


import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Order(-1)
public class AuthorizeFilter implements GlobalFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //获取请求参数
        ServerHttpRequest request = exchange.getRequest();
        MultiValueMap<String, String> queryParams = request.getQueryParams();
        //获取参数中的authorization参数
        String authorization = queryParams.getFirst("authorization");
        //判断参数值是否等于admin
        if ("admin".equals(authorization)) {
            //是就放行
            return chain.filter(exchange);
        }
        //设置响应状态码401表示用户未登录
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        //拦截
        return exchange.getResponse().setComplete();

    }
}
