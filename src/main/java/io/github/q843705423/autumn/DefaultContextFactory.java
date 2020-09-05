package io.github.q843705423.autumn;

import io.github.q843705423.autumn.entity.Configuration;
import io.github.q843705423.autumn.request.facotory.DefaultHandlerFactory;
import org.springframework.web.client.RestTemplate;

public class DefaultContextFactory extends AbstractContextFactory {

    private Configuration configuration;

    public DefaultContextFactory(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    protected AbstractResponseProcessor getAbstractResponseProcessor() {
        return new DefaultResponseProcessor();
    }

    @Override
    protected ObjectProvider<RestTemplate> getRestTemplateProvider() {
        return invocation -> new RestTemplate();
    }

    @Override
    public AbstractRequester getAbstractRequester() {
        return new DefaultRequester(new DefaultHandlerFactory(), getRestTemplateProvider());
    }

    @Override
    public ObjectProvider<String> getUrlPrefixProvider() {
        return new DefaultFixUrlPrefixProvider(configuration.getDefaultUrlPrefix());
    }
}
