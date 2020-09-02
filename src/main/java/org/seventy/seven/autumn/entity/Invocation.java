package org.seventy.seven.autumn.entity;

import org.springframework.http.HttpHeaders;

import java.lang.reflect.Method;

public class Invocation {

    private String url;

    private HttpHeaders httpHeaders;

    private Object httpBody;

    private final Class<?> proxyClass;

    private final Method method;

    private final Object[] methodParam;


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HttpHeaders getHttpHeaders() {
        return httpHeaders;
    }

    public void setHttpHeaders(HttpHeaders httpHeaders) {
        this.httpHeaders = httpHeaders;
    }

    public Object getHttpBody() {
        return httpBody;
    }

    public void setHttpBody(Object httpBody) {
        this.httpBody = httpBody;
    }

    public Class<?> getProxyClass() {
        return proxyClass;
    }


    public Method getMethod() {
        return method;
    }


    public Object[] getMethodParam() {
        return methodParam;
    }

    public Invocation(Class<?> proxyClass, Method method, Object[] methodParam) {
        this.proxyClass = proxyClass;
        this.method = method;
        this.methodParam = methodParam;
    }
}

