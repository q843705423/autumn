package io.github.q843705423.autumn.request.handler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.github.q843705423.autumn.AutumnObjectUtil;
import io.github.q843705423.autumn.entity.Invocation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMethod;
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
        List<Integer> paramContainTypeFieldIndex = findParamContainTypeFieldIndex(invocation.getMethodParamType(), MultipartFile.class);
        if (!paramContainTypeFieldIndex.isEmpty()) {
            return invocation;

        }
        return deal(invocation);
    }

    private Invocation deal(Invocation invocation) {
        if (invocation.getRequestMethod() == RequestMethod.GET) {

            Parameter[] parameters = invocation.getMethod().getParameters();
            Object[] params = invocation.getMethodParam();
            MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>();
            for (int i = 0; i < parameters.length; i++) {
                Parameter parameter = parameters[i];
                Class<?> parameterType = parameter.getType();
                if (AutumnObjectUtil.isSimpleType(parameterType)) {
                    invocation.getParamList().put(parameter.getName(), String.valueOf(params[i]));
                } else {
                    JSONObject res = JSON.parseObject(JSON.toJSONString(params[i]));
                    res.forEach((key, v) -> {
                        if (AutumnObjectUtil.isSimpleType(v.getClass())) {
                            invocation.getParamList().put(key, String.valueOf(v));

                        } else {
                            invocation.getParamList().put(key, JSON.toJSONString(v));
                        }
                    });
                }
            }
            HttpHeaders headers = invocation.getHttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            invocation.setHttpBody(multiValueMap);

        } else {

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
        }
        return invocation;
    }


    @Override
    public int order() {
        return 300;
    }
}
