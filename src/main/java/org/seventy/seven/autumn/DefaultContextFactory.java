package org.seventy.seven.autumn;

import org.seventy.seven.autumn.entity.Configuration;
import org.seventy.seven.autumn.request.facotory.DefaultHandlerFactory;

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
    public AbstractRequester getAbstractRequester() {
        return new DefaultRequester(new DefaultHandlerFactory());
    }

    @Override
    public ObjectProvider<String> getUrlPrefixProvider() {
        return new DefaultFixUrlPrefixProvider(configuration.getDefaultUrlPrefix());
    }
}
