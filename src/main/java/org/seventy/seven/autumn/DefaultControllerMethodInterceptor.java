package org.seventy.seven.autumn;

import java.lang.reflect.Method;

public class DefaultControllerMethodInterceptor extends AbstractControllerMethodInterceptor {


    private ObjectProvider<String> urlPrefixProvider;
    private AbstractRequester abstractRequester;
    private AbstractResponseProcessor abstractResponseProcessor;

    @Override
    public String getUrlPrefix(Class<?> clazz, Method method, Object[] objects) {
        return providerUrlPrefix(clazz, method, objects);
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
     * @param clazz   被代理的控制器
     * @param method  被调用的方法
     * @param objects 方法的参数
     * @return 调用地址前缀
     */
    protected String providerUrlPrefix(Class<?> clazz, Method method, Object[] objects) {
        return urlPrefixProvider.provider(clazz, method, objects);
    }
}
