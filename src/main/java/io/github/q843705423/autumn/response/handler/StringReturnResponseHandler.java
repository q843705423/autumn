package io.github.q843705423.autumn.response.handler;

import io.github.q843705423.autumn.entity.Invocation;
import io.github.q843705423.autumn.entity.ResponseReturn;
import io.github.q843705423.autumn.entity.ResponseWrapper;

public class StringReturnResponseHandler implements IResponseInvocationHandler {
    @Override
    public ResponseReturn deal(Invocation invocation, ResponseWrapper responseWrapper) {
        if (invocation.getMethod().getReturnType().equals(String.class)) {
            return ResponseReturn.toReturn(responseWrapper.getResponseEntity().getBody());
        }
        return ResponseReturn.noReturn();
    }


    @Override
    public int order() {
        return 0;
    }
}
