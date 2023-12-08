package com.demo.common.feign;

import com.demo.common.exception.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

import java.io.IOException;

@Slf4j
public class FeignExceptionConfig {
    @Bean
    public ErrorDecoder errorDecoder() {
        return new UserErrorDecoder();
    }

    /**
     * 重新实现feign的异常处理，捕捉restful接口返回的json格式的异常信息
     */
    public class UserErrorDecoder implements ErrorDecoder {

        @Override
        public Exception decode(String methodKey, Response response) {
            Exception exception = null;
            ObjectMapper mapper = new ObjectMapper();
            //空属性处理
            mapper.setSerializationInclusion(JsonSerialize.Inclusion.NON_EMPTY);
            //设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
            mapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            //禁止使用int代表enum的order来反序列化enum
            mapper.configure(DeserializationConfig.Feature.FAIL_ON_NUMBERS_FOR_ENUMS, true);
            try {
                String json = Util.toString(response.body().asReader());
                // 非业务异常包装成自定义异常类ServiceException
                if (json != null && json.length() > 0) {

                    log.error(">>>>>>>>>> 【ERROR】Feign调用程序异常 methodKey: {} response: {}", methodKey, json);
                    if (json.contains("code")) {
                        FeignFailedResult result = mapper.readValue(json, FeignFailedResult.class);
                        result.setCode(response.status());
                        result.setStatus(response.status());
                        // 连接异常包装成自定义熔断异常类HystrixException
                        if (result.getStatus() != HttpStatus.OK.value()) {
                            exception = new FeignException(result.getMessage());
                        } else {
                            exception = feign.FeignException.errorStatus(methodKey, response);
                        }
                    } else {
                        exception = new RuntimeException("程序异常");
                    }

                } else {
                    exception = feign.FeignException.errorStatus(methodKey, response);
                }

            } catch (IOException ex) {
                log.error(ex.getMessage(), ex);
            }
            return exception;
        }


    }
}