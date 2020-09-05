package io.github.q843705423.autumn.entity;

import org.springframework.http.HttpHeaders;

import java.lang.reflect.Method;

public class Invocation {


    private String urlPrefix;

    private HttpHeaders httpHeaders;

    private Object httpBody;

    private final Class<?> proxyClass;

    private final Method method;

    private final Object[] methodParam;

    private String uri;


    public String getUrl() {
        return urlPrefix + uri;
    }

    public String getUrlPrefix() {
        return urlPrefix;
    }

    public void setUrlPrefix(String urlPrefix) {
        this.urlPrefix = urlPrefix;
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

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
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

