package org.seventy.seven.autumn;

import com.alibaba.fastjson.JSON;
import org.seventy.seven.autumn.entity.ResponseWrapper;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.Method;

public class DefaultResponseProcessor extends AbstractResponseProcessor {

    @Override
    public Object processingResponse(String url, Object[] params, Method method, ResponseWrapper response) throws Exception {
        ResponseEntity<String> responseEntity = response.getResponseEntity();
        if (response.hasError()) {
            throw response.error();
        }
        String body = responseEntity.getBody();
        Class<?> returnType = method.getReturnType();
        if (returnType.equals(String.class)) {
            return body;
        } else {
            return JSON.parseObject(body, returnType);
        }
    }
}
