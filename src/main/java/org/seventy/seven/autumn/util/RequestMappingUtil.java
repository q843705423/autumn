package org.seventy.seven.autumn.util;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class RequestMappingUtil {

    public static String[] getUrl(Method method) {
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        PostMapping postMapping = method.getAnnotation(PostMapping.class);
        GetMapping getMapping = method.getAnnotation(GetMapping.class);
        String[] a = null;
        if (requestMapping != null) {
            a = requestMapping.value();
        }
        if (postMapping != null) {
            a = postMapping.value();
        }
        if (getMapping != null) {
            a = getMapping.value();
        }
        return a == null ? new String[]{""} : a;
    }

    private static String normalURI(String uri) {
        if (!uri.startsWith("/")) {
            uri = "/" + uri;
        }
        if (uri.endsWith("/")) {
            uri = uri.substring(0, uri.lastIndexOf('/'));
        }
        return uri;
    }

    public static List<String> calculateUrl(String[] prefix, String[] suffix) {
        return Arrays.stream(prefix)
                .map(left -> Arrays.stream(suffix).map(right -> normalURI(left) + normalURI(right)).collect(toList()))
                .reduce(new ArrayList<>(), (l, r) -> {
                    l.addAll(r);
                    return l;
                }).stream()
                .filter(s -> !s.isEmpty())
                .collect(toList());

    }

    public static boolean existContainRequestBody(Annotation[] parameterAnnotation) {
        return Arrays.stream(parameterAnnotation).anyMatch(s -> s.annotationType().getName().equals(RequestBody.class.getName()));
    }

    public static boolean existContainRequestBody(Annotation[] parameterAnnotation, Class clazz) {
        return Arrays.stream(parameterAnnotation).anyMatch(s -> s.annotationType().getName().equals(clazz.getName()));
    }

    public static boolean methodParamContainAnnotation(Method method, Class clazz) {
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (Annotation[] parameterAnnotation : parameterAnnotations) {
            boolean b = existContainRequestBody(parameterAnnotation);
            if (b) {
                return true;
            }
        }
        return false;
    }
}
