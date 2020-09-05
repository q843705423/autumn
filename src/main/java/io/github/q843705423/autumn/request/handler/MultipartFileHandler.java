package io.github.q843705423.autumn.request.handler;

import com.alibaba.fastjson.JSON;
import io.github.q843705423.autumn.AutumnException;
import io.github.q843705423.autumn.AutumnObjectUtil;
import io.github.q843705423.autumn.entity.Invocation;
import io.github.q843705423.autumn.file.CommonInputStreamResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

import static io.github.q843705423.autumn.AutumnObjectUtil.findParamContainTypeFieldIndex;

public class MultipartFileHandler implements IRequestInvocationHandler {

    @Override
    public Invocation invocationHandler(Invocation invocation) throws AutumnException {
        List<Integer> paramContainTypeFieldIndex = findParamContainTypeFieldIndex(invocation.getMethodParam(), MultipartFile.class);
        if (paramContainTypeFieldIndex.isEmpty()) {
            return invocation;
        }
        LinkedMultiValueMap<String, Object> map = new LinkedMultiValueMap<>();
        Parameter[] parameters = invocation.getMethod().getParameters();
        Object[] methodParam = invocation.getMethodParam();
        for (int i = 0; i < methodParam.length; ++i) {
            Object o = methodParam[i];
            Parameter parameter = parameters[i];
            if (o instanceof MultipartFile) {
                addFile(map, parameter.getName(), (MultipartFile) o);
            } else if (AutumnObjectUtil.isSimpleType(parameter.getType())) {
                map.add(parameter.getName(), String.valueOf(methodParam[i]));
            } else {

                addBody(map, parameter, methodParam[i]);
            }

        }

        invocation.setHttpBody(map);
        HttpHeaders httpHeaders = invocation.getHttpHeaders();
        httpHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        return invocation;

    }

    private void addBody(LinkedMultiValueMap<String, Object> stringStringLinkedMultiValueMap, Parameter parameter, Object s) {
        Class<?> aClass = s.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            String name = declaredField.getName();
            String getMethodName = "get" + upperFirstLetter(name);
            try {
                Method method = aClass.getMethod(getMethodName);
                Object invoke = method.invoke(s);
                if (invoke instanceof MultipartFile) {
                    addFile(stringStringLinkedMultiValueMap, name, (MultipartFile) invoke);
                } else if (AutumnObjectUtil.isSimpleType(invoke.getClass())) {
                    stringStringLinkedMultiValueMap.add(name, String.valueOf(invoke));
                } else {
                    stringStringLinkedMultiValueMap.add(name, JSON.toJSONString(invoke));
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ignored) {

            }
        }
    }

    private void addFile(LinkedMultiValueMap<String, Object> stringStringLinkedMultiValueMap, String name, MultipartFile file) {
        try {
            InputStreamResource commonInputStreamResource = new CommonInputStreamResource(file);
            stringStringLinkedMultiValueMap.add(name, commonInputStreamResource);
        } catch (IOException e) {

        }
    }

    private String upperFirstLetter(String name) {
        return name == null ? null : name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
