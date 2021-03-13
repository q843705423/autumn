package io.github.q843705423.autumn.response.handler;

import com.alibaba.fastjson.JSON;
import io.github.q843705423.autumn.entity.Invocation;
import io.github.q843705423.autumn.entity.ResponseReturn;
import io.github.q843705423.autumn.entity.ResponseWrapper;

import java.lang.reflect.Type;

/**
 * 复杂对象的响应处理器
 * @author qiqi.chen
 */
public class ComplexObjectResponseHandler implements IResponseInvocationHandler {
    @Override
    public ResponseReturn deal(Invocation invocation, ResponseWrapper responseWrapper) throws Exception {
        if (responseWrapper.hasError()) {
            throw responseWrapper.error();
        }
        String body = responseWrapper.getResponseEntity().getBody();
        if (body == null) {
            return ResponseReturn.noReturn();
        }
        Type type = invocation.getMethod().getGenericReturnType();
        return ResponseReturn.toReturn(JSON.parseObject(body, type));
    }

    @Override
    public int order() {
        return 500;
    }
}
