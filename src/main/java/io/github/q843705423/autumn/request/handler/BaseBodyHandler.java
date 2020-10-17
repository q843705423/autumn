package io.github.q843705423.autumn.request.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.q843705423.autumn.AutumnObjectUtil;
import io.github.q843705423.autumn.entity.Invocation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Parameter;
import java.util.List;

import static io.github.q843705423.autumn.AutumnObjectUtil.findParamContainTypeFieldIndex;

public class BaseBodyHandler implements IRequestInvocationHandler {
    @Override
    public Invocation invocationHandler(Invocation invocation) {
        List<Integer> requestBodyParamIndex = AutumnObjectUtil.getRequestBodyParamIndex(invocation.getMethod());
        if (!requestBodyParamIndex.isEmpty()) {
            return invocation;
        }
        List<Integer> paramContainTypeFieldIndex = findParamContainTypeFieldIndex(invocation.getMethodParam(), MultipartFile.class);
        if (!paramContainTypeFieldIndex.isEmpty()) {
            return invocation;

        }
        return deal(invocation);
    }

    private Invocation deal(Invocation invocation) {
        Parameter[] parameters = invocation.getMethod().getParameters();
        Object[] params = invocation.getMethodParam();
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];
            Class<?> parameterType = parameter.getType();
            if (AutumnObjectUtil.isSimpleType(parameterType)) {
                multiValueMap.add(parameter.getName(), String.valueOf(params[i]));
            } else {
                JSONObject res = JSON.parseObject(JSON.toJSONString(params[i]));
                res.forEach((key, v) -> {
                    if (AutumnObjectUtil.isSimpleType(v.getClass())) {
                        multiValueMap.add(key, String.valueOf(v));
                    } else {
                        multiValueMap.add(key, JSON.toJSONString(v));
                    }
                });
            }
        }
        HttpHeaders headers = invocation.getHttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        invocation.setHttpBody(multiValueMap);
        return invocation;
    }
}
