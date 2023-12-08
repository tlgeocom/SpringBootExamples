package com.demo.common.exception;

/**
 * feign client 避免熔断异常处理
 */
public class FeignException extends RuntimeException {

    private static final long serialVersionUID = -2437160791033393978L;

    public FeignException(String msg) {
        super(msg);
    }

    public FeignException(Exception e) {
        this(e.getMessage());
    }
}
