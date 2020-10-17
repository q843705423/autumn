package io.github.q843705423.autumn.response.handler;

import com.alibaba.fastjson.JSON;
import io.github.q843705423.autumn.entity.Invocation;
import io.github.q843705423.autumn.entity.ResponseReturn;
import io.github.q843705423.autumn.entity.ResponseWrapper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class ListResponseHandler implements IResponseInvocationHandler {
    @Override
    public ResponseReturn deal(Invocation invocation, ResponseWrapper responseWrapper) {
        if (!invocation.getMethod().getReturnType().getTypeName().equals(List.class.getName())) {
            return ResponseReturn.noReturn();
        }

        Type type = invocation.getMethod().getGenericReturnType();
        if (!(type instanceof ParameterizedType)) {
            return ResponseReturn.noReturn();
        }

        ParameterizedType parameterizedType = (ParameterizedType) type;
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        assert actualTypeArguments.length == 1;
        String typeName = actualTypeArguments[0].getTypeName();
        String body = responseWrapper.getResponseEntity().getBody();

        try {
            return ResponseReturn.toReturn(JSON.parseArray(body, Class.forName(typeName)));
        } catch (ClassNotFoundException e) {
            System.out.println(e.toString());
            return ResponseReturn.noReturn();
        }

    }

    @Override
    public int order() {
        return 200;
    }
}
