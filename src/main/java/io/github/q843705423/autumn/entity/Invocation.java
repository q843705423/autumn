package io.github.q843705423.autumn.entity;

import io.github.q843705423.autumn.util.RequestMappingUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Invocation {


    private String urlPrefix;

    private HttpHeaders httpHeaders;

    private Object httpBody;

    private final Class<?> proxyClass;

    private final Method method;

    private final Object[] methodParam;

    private final Class<?>[] methodParamType;

    private String uri;

    private Map<String, String> paramList = new HashMap<>();

    public Map<String, String> getParamList() {
        return paramList;
    }

    public void setParamList(Map<String, String> paramList) {
        this.paramList = paramList;
    }

    private RequestMethod requestMethod;

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    public String getUrl() {
        return RequestMappingUtil.join(urlPrefix, uri);
    }

    public Class<?>[] getMethodParamType() {
        return methodParamType;
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

    public Invocation(Class<?> proxyClass, Method method, Object[] methodParam, Class<?>[] methodParamType) {
        this.proxyClass = proxyClass;
        this.method = method;
        this.methodParam = methodParam;
        this.methodParamType = methodParamType;
    }
}

