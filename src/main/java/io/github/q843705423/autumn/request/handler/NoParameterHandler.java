package io.github.q843705423.autumn.request.handler;

import com.alibaba.fastjson.JSONObject;
import io.github.q843705423.autumn.entity.Invocation;

public class NoParameterHandler implements IRequestInvocationHandler {
    @Override
    public Invocation invocationHandler(Invocation invocation) {
        if (invocation.getMethodParam().length == 0) {
            invocation.setHttpBody(new JSONObject());
            return invocation;
        }
        return invocation;
    }

    @Override
    public int order() {
        return 100;
    }
}
