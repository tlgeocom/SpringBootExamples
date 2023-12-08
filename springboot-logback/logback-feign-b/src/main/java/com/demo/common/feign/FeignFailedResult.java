package com.demo.common.feign;

import lombok.Data;

@Data
public class FeignFailedResult {

    private int code;
    private int status;
    private String message;
}
