package io.github.q843705423.autumn.response.handler;

import com.alibaba.fastjson.JSON;
import io.github.q843705423.autumn.entity.Invocation;
import io.github.q843705423.autumn.entity.ResponseReturn;
import io.github.q843705423.autumn.entity.ResponseWrapper;
import org.springframework.http.ResponseEntity;

public class BaseObjectResponseHandler implements IResponseInvocationHandler {
    @Override
    public ResponseReturn deal(Invocation invocation, ResponseWrapper responseWrapper) {
        Class<?> returnType = invocation.getMethod().getReturnType();
        ResponseEntity<String> responseEntity = responseWrapper.getResponseEntity();
        return ResponseReturn.toReturn(JSON.parseObject(responseEntity.getBody(), returnType));
    }


    @Override
    public int order() {
        return 300;
    }
}
