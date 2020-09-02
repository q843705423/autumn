package org.seventy.seven.autumn;

import org.seventy.seven.autumn.entity.Invocation;
import org.seventy.seven.autumn.entity.ResponseWrapper;
import org.seventy.seven.autumn.request.handler.IRequestInvocationHandler;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Method;
import java.util.List;

public abstract class AbstractRequester {


    public ResponseWrapper request(String url, Object[] params, Method method) {
        try {
            final RestTemplate restTemplate = getRestTemplate();
            Invocation invocation = new Invocation(null, method, params);
            invocation.setUrl(url);
            invocation.setHttpHeaders(new HttpHeaders());
            invocation.setHttpBody(null);
            AbstractHandlerFactory abstractHandlerFactory = getHandlerFactory();

            invocation = abstractHandlerFactory.deal(invocation);
            HttpEntity<?> requestEntity = new HttpEntity<>(invocation.getHttpBody(), invocation.getHttpHeaders());

            String finalUrl = getFinalUrl(invocation.getUrl(), invocation.getMethodParam(), invocation.getMethod());

            ResponseEntity<String> responseEntity = restTemplate.postForEntity(finalUrl, requestEntity, String.class);
            return new ResponseWrapper(responseEntity);
        } catch (Exception e) {
            return new ResponseWrapper(e);
        }
    }

    protected abstract AbstractHandlerFactory getHandlerFactory();

    protected RestTemplate getRestTemplate() {
        return new RestTemplate();
    }


    public static abstract class AbstractHandlerFactory {


        protected abstract void init(List<IRequestInvocationHandler> requestInvocationHandlerList);

        protected abstract Invocation deal(Invocation invocation) throws AutumnException;
    }

    protected abstract String getFinalUrl(String url, Object[] params, Method method) throws AutumnException;
}
