package io.github.q843705423.autumn;

import io.github.q843705423.autumn.entity.Invocation;

public class DefaultControllerMethodInterceptor extends AbstractControllerMethodInterceptor {


    private ObjectProvider<String> urlPrefixProvider;
    private AbstractRequester abstractRequester;
    private AbstractResponseProcessor abstractResponseProcessor;

    @Override
    public String getUrlPrefix(Invocation invocation) {
        return providerUrlPrefix(invocation);
    }

    @Override
    protected AbstractResponseProcessor getAbstractResponseProcessor() {
        return abstractResponseProcessor;
    }

    @Override
    protected AbstractRequester getAbstractRequester() {
        return abstractRequester;
    }


    public <T> DefaultControllerMethodInterceptor(Class<T> clazz,
                                                  ObjectProvider<String> urlPrefixProvider,
                                                  AbstractRequester abstractRequester,
                                                  AbstractResponseProcessor abstractResponseProcessor
    ) {
        super(clazz);
        this.urlPrefixProvider = urlPrefixProvider;
        this.abstractRequester = abstractRequester;
        this.abstractResponseProcessor = abstractResponseProcessor;
    }


    /**
     * 客户端用户用于提供前缀
     *
     * @return 调用地址前缀
     */
    protected String providerUrlPrefix(Invocation invocation) {
        return urlPrefixProvider.provider(invocation);
    }
}
