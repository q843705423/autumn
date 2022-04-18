package io.github.q843705423.autumn;

import io.github.q843705423.autumn.entity.Configuration;
import io.github.q843705423.autumn.lib.HttpComponentsClientRestfulHttpRequestFactory;
import io.github.q843705423.autumn.request.factory.DefaultRequestHandlerFactory;
import io.github.q843705423.autumn.response.factory.DefaultResponseHandlerFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

public class DefaultContextFactory extends AbstractContextFactory {

    private Configuration configuration;

    public DefaultContextFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected AbstractResponseProcessor getAbstractResponseProcessor() {
        return new DefaultResponseProcessor(getDefaultResponseHandlerFactory());
    }

    @Override
    protected DefaultResponseHandlerFactory getDefaultResponseHandlerFactory() {
        return new DefaultResponseHandlerFactory();
    }

    @Override
    protected ObjectProvider<RestTemplate> getRestTemplateProvider() {
        return invocation -> {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().forEach(httpMessageConverter -> {
                if (httpMessageConverter instanceof StringHttpMessageConverter) {
                    StringHttpMessageConverter stringHttpMessageConverter = (StringHttpMessageConverter) httpMessageConverter;
                    stringHttpMessageConverter.setDefaultCharset(StandardCharsets.UTF_8);
                }
            });
            restTemplate.setRequestFactory(new HttpComponentsClientRestfulHttpRequestFactory());
            return restTemplate;
        };
    }

    @Override
    public AbstractRequester getAbstractRequester() {
        return new DefaultRequester(getAbstractRequestHandlerFactory(), getRestTemplateProvider());
    }

    @Override
    protected AbstractRequester.AbstractRequestHandlerFactory getAbstractRequestHandlerFactory() {
        return new DefaultRequestHandlerFactory();
    }

    @Override
    public ObjectProvider<String> getUrlPrefixProvider() {
        return new DefaultFixUrlPrefixProvider(configuration.getDefaultUrlPrefix());
    }
}
