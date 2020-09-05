package io.github.q843705423.autumn;


import io.github.q843705423.autumn.entity.Invocation;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Method;

public class DefaultRequester extends AbstractRequester {


    private AbstractHandlerFactory abstractHandlerFactory;
    private ObjectProvider<RestTemplate> restTemplateObjectProvider;

    public DefaultRequester(AbstractHandlerFactory abstractHandlerFactory, ObjectProvider<RestTemplate> restTemplateObjectProvider) {
        this.abstractHandlerFactory = abstractHandlerFactory;
        this.restTemplateObjectProvider = restTemplateObjectProvider;
    }

    @Override
    protected AbstractHandlerFactory getHandlerFactory() {
        return abstractHandlerFactory;
    }

    @Override
    public RestTemplate getRestTemplate(Invocation invocation) {
        return restTemplateObjectProvider.provider(invocation);
    }

    @Override
    protected String getFinalUrl(String url, Object[] params, Method method) {
        return url;
    }


}
