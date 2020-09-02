package org.seventy.seven.autumn.request.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.seventy.seven.autumn.AutumnObjectUtil;
import org.seventy.seven.autumn.entity.Invocation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.seventy.seven.autumn.AutumnObjectUtil.findParamContainTypeFieldIndex;

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
        Class<?>[] parameterTypes = invocation.getMethod().getParameterTypes();
        Object[] params = invocation.getMethodParam();
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> parameterType = parameterTypes[i];
            if (AutumnObjectUtil.isSimpleType(parameterType)) {
                multiValueMap.add(parameterType.getSimpleName(), String.valueOf(params[i]));
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
        return invocation;
    }
}
