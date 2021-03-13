package io.github.q843705423.autumn.entity;

import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author qiqi.chen
 */
public class RequestInfo {
    private String url;
    private RequestMethod requestMethod;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }
}
