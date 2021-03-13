package io.github.q843705423.autumn;


import io.github.q843705423.autumn.entity.Invocation;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Method;

public class DefaultRequester extends AbstractRequester {


    private AbstractRequestHandlerFactory abstractRequestHandlerFactory;
    private ObjectProvider<RestTemplate> restTemplateObjectProvider;

    public DefaultRequester(AbstractRequestHandlerFactory abstractRequestHandlerFactory, ObjectProvider<RestTemplate> restTemplateObjectProvider) {
        this.abstractRequestHandlerFactory = abstractRequestHandlerFactory;
        this.restTemplateObjectProvider = restTemplateObjectProvider;
    }

    @Override
    protected AbstractRequestHandlerFactory getHandlerFactory() {
        return abstractRequestHandlerFactory;
    }

    @Override
    public RestTemplate getRestTemplate(Invocation invocation) {
        return restTemplateObjectProvider.provider(invocation);
    }

    @Override
    public String getFinalUrl(String url, Object[] params, Method method) {
        return url;
    }


}
