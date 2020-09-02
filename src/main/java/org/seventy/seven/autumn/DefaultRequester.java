package org.seventy.seven.autumn;


import java.lang.reflect.Method;

public class DefaultRequester extends AbstractRequester {


    private AbstractHandlerFactory abstractHandlerFactory;

    public DefaultRequester(AbstractHandlerFactory abstractHandlerFactory) {
        this.abstractHandlerFactory = abstractHandlerFactory;
    }

    @Override
    protected AbstractHandlerFactory getHandlerFactory() {
        return abstractHandlerFactory;
    }

    @Override
    protected String getFinalUrl(String url, Object[] params, Method method) {
        return url;
    }


}
