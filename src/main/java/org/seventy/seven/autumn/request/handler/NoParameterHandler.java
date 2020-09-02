package org.seventy.seven.autumn.request.handler;

import com.alibaba.fastjson.JSONObject;
import org.seventy.seven.autumn.entity.Invocation;

public class NoParameterHandler implements IRequestInvocationHandler {
    @Override
    public Invocation invocationHandler(Invocation invocation) {
        if (invocation.getMethodParam().length == 0) {
            invocation.setHttpBody(new JSONObject());
            return invocation;
        }
        return invocation;
    }
}
