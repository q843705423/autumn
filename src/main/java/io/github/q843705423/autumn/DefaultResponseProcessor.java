package io.github.q843705423.autumn;

import io.github.q843705423.autumn.entity.Invocation;
import io.github.q843705423.autumn.entity.ResponseWrapper;
import io.github.q843705423.autumn.response.factory.DefaultResponseHandlerFactory;

public class DefaultResponseProcessor extends AbstractResponseProcessor {

    private DefaultResponseHandlerFactory defaultResponseHandlerFactory;

    public DefaultResponseProcessor(DefaultResponseHandlerFactory defaultResponseHandlerFactory) {
        this.defaultResponseHandlerFactory = defaultResponseHandlerFactory;
    }

    @Override
    public Object processingResponse(Invocation invocation, ResponseWrapper response) throws Exception {
        return defaultResponseHandlerFactory.deal(invocation, response);
    }
}
