package io.github.q843705423.autumn.template;

import io.github.q843705423.autumn.AbstractRequester;
import io.github.q843705423.autumn.AutumnException;
import io.github.q843705423.autumn.entity.Invocation;
import io.github.q843705423.autumn.entity.ResponseWrapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author qiqi.chen
 */
public class RestTemplateGetCall implements RestTemplateCall {
    @Override
    public ResponseWrapper call(Invocation invocation,
                                AbstractRequester.AbstractRequestHandlerFactory abstractRequestHandlerFactory,
                                RestTemplate restTemplate,
                                AbstractRequester abstractRequester) throws AutumnException, UnsupportedEncodingException {
        invocation.setHttpHeaders(new HttpHeaders());
        invocation.setHttpBody(null);
        invocation = abstractRequestHandlerFactory.deal(invocation);
        HttpEntity<?> requestEntity = new HttpEntity<>(invocation.getHttpBody(), invocation.getHttpHeaders());
        String url = invocation.getUrl();
        Map<String, String> paramList = invocation.getParamList();
        Set<String> paramNameList = paramList.keySet();
        String urlParamList = paramNameList.stream().map(name -> {
            String value = paramList.get(name);
            if (value == null) {
                return "";
            }
            return name + "=" + value;

        }).collect(Collectors.joining("&"));
        if (!urlParamList.isEmpty()) {
            url += "?" + urlParamList;
        }


        StringBuilder finalUrl = new StringBuilder(abstractRequester.getFinalUrl(url, invocation.getMethodParam(), invocation.getMethod()));
        ResponseEntity<String> responseEntity = restTemplate.exchange(finalUrl.toString(), HttpMethod.GET, requestEntity, String.class);
        return new ResponseWrapper(responseEntity);
    }

    @Override
    public RequestMethod getRequestMethod() {
        return RequestMethod.GET;
    }
}
