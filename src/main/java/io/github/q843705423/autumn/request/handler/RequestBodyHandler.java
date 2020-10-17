package io.github.q843705423.autumn.request.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.q843705423.autumn.AutumnException;
import io.github.q843705423.autumn.AutumnObjectUtil;
import io.github.q843705423.autumn.entity.Invocation;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

public class RequestBodyHandler implements IRequestInvocationHandler {

    @Override
    public Invocation invocationHandler(Invocation invocation) throws AutumnException {
        List<Integer> requestBodyParamIndex = AutumnObjectUtil.getRequestBodyParamIndex(invocation.getMethod());
        if (requestBodyParamIndex.size() > 1) {
            throw new AutumnException("@RequestBody only one parameter can be given");
        }
        if (requestBodyParamIndex.isEmpty()) {
            return invocation;
        }

        //TODO Check whether there is a File field in other Objects, if there is, throw an exception
        return dealWithUrl(dealWithHttpBody(invocation));

    }

    private Invocation dealWithHttpBody(Invocation invocation) {
        Object[] params = invocation.getMethodParam();
        List<Integer> requestBodyParamIndex = AutumnObjectUtil.getRequestBodyParamIndex(invocation.getMethod());
        Integer index = requestBodyParamIndex.get(0);
        Object requestBodyParam = params[index];
        JSONObject jsonObject;
        if (requestBodyParam instanceof String) {
            jsonObject = JSON.parseObject((String) requestBodyParam);
        } else {
            jsonObject = JSON.parseObject(JSON.toJSONString(requestBodyParam));
        }
        invocation.setHttpBody(jsonObject);
        return invocation;
    }

    private Invocation dealWithUrl(Invocation invocation) {
        Method method = invocation.getMethod();
        Object[] params = invocation.getMethodParam();
        List<Integer> requestBodyParamIndex = AutumnObjectUtil.getRequestBodyParamIndex(method);
        Integer index = requestBodyParamIndex.get(0);
        StringBuilder suffix = new StringBuilder();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < params.length; i++) {
            if (i >= index && i <= index) {
                continue;
            }
            boolean simpleType = AutumnObjectUtil.isSimpleType(parameters[i].getType());
            if (simpleType) {
                addSign(suffix);
                suffix.append(parameters[i].getName())
                        .append("=")
                        .append(params[i]);

            } else {
                JSONObject root = JSON.parseObject(JSON.toJSONString(params[i]));
                root.forEach((key, object) -> {
                    addSign(suffix);
                    String str = object instanceof String ? (String) object : JSON.toJSONString(object);
                    suffix.append(key)
                            .append("=")
                            .append(str);
                });
            }


        }
        invocation.setUri(invocation.getUri() + suffix);
        return invocation;
    }

    private void addSign(StringBuilder suffix) {
        if (suffix.length() == 0) {
            suffix.append("?");
        } else {
            suffix.append("&");
        }
    }

    @Override
    public int order() {
        return 400;
    }
}
