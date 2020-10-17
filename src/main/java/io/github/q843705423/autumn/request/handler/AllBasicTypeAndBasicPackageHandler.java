package io.github.q843705423.autumn.request.handler;

import io.github.q843705423.autumn.entity.Invocation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import static io.github.q843705423.autumn.util.RequestMappingUtil.methodParamContainAnnotation;

public class AllBasicTypeAndBasicPackageHandler implements IRequestInvocationHandler {


    @Override
    public Invocation invocationHandler(Invocation invocation) {
        boolean containAnnotation = methodParamContainAnnotation(invocation.getMethod(), RequestBody.class);
        if (containAnnotation) {
            return invocation;
        }
        if (invocation.getHttpBody() == null) {
            return invocation;
        }
        return deal(invocation);
    }


    private Invocation deal(Invocation invocation) {
        Method method = invocation.getMethod();
        Object[] params = invocation.getMethodParam();
        MultiValueMap<String, String> paramObject = new LinkedMultiValueMap<>();
        HttpHeaders headers = invocation.getHttpHeaders();
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            if (params[i] == null) {
                continue;
            }
            paramObject.add(parameter.getName(), String.valueOf(params[i]));
        }
        invocation.setHttpBody(paramObject);

        //将header设置为url模式
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return invocation;
    }


    @Override
    public int order() {
        return 200;
    }
}
