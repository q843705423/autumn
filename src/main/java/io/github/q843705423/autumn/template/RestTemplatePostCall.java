package io.github.q843705423.autumn.template;

import io.github.q843705423.autumn.AbstractRequester;
import io.github.q843705423.autumn.AutumnException;
import io.github.q843705423.autumn.entity.Invocation;
import io.github.q843705423.autumn.entity.ResponseWrapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

/**
 * post接口调用
 *
 * @author qiqi.chen
 */
public class RestTemplatePostCall implements RestTemplateCall {
    @Override
    public ResponseWrapper call(Invocation invocation,
                                AbstractRequester.AbstractRequestHandlerFactory abstractRequestHandlerFactory,
                                RestTemplate restTemplate,
                                AbstractRequester abstractRequester
    ) throws AutumnException {
        invocation.setHttpHeaders(new HttpHeaders());
        invocation.setHttpBody(null);
        invocation = abstractRequestHandlerFactory.deal(invocation);
        HttpEntity<?> requestEntity = new HttpEntity<>(invocation.getHttpBody(), invocation.getHttpHeaders());
        String finalUrl = abstractRequester.getFinalUrl(invocation.getUrl(), invocation.getMethodParam(), invocation.getMethod());
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(finalUrl, requestEntity, String.class);
        return new ResponseWrapper(responseEntity);
    }

    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.POST;
    }
}
