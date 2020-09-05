package io.github.q843705423.autumn;

import io.github.q843705423.autumn.entity.ResponseWrapper;
import io.github.q843705423.autumn.entity.Invocation;
import io.github.q843705423.autumn.request.handler.IRequestInvocationHandler;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Method;
import java.util.List;

public abstract class AbstractRequester {


    public ResponseWrapper request(Invocation invocation) {
        try {

            invocation.setHttpHeaders(new HttpHeaders());
            invocation.setHttpBody(null);
            AbstractHandlerFactory abstractHandlerFactory = getHandlerFactory();

            invocation = abstractHandlerFactory.deal(invocation);
            final RestTemplate restTemplate = getRestTemplate(invocation);
            HttpEntity<?> requestEntity = new HttpEntity<>(invocation.getHttpBody(), invocation.getHttpHeaders());

            String finalUrl = getFinalUrl(invocation.getUrl(), invocation.getMethodParam(), invocation.getMethod());

            ResponseEntity<String> responseEntity = restTemplate.postForEntity(finalUrl, requestEntity, String.class);
            return new ResponseWrapper(responseEntity);
        } catch (Exception e) {
            return new ResponseWrapper(e);
        }
    }

    protected abstract AbstractHandlerFactory getHandlerFactory();

    public abstract RestTemplate getRestTemplate(Invocation invocation);


    public static abstract class AbstractHandlerFactory {


        protected abstract void init(List<IRequestInvocationHandler> requestInvocationHandlerList);

        protected abstract Invocation deal(Invocation invocation) throws AutumnException;
    }

    protected abstract String getFinalUrl(String url, Object[] params, Method method) throws AutumnException;
}
