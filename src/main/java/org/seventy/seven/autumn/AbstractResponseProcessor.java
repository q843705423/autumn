package org.seventy.seven.autumn;

import org.seventy.seven.autumn.entity.ResponseWrapper;

import java.lang.reflect.Method;

public abstract class AbstractResponseProcessor {
    public abstract Object processingResponse(String url, Object[] params, Method method, ResponseWrapper response) throws Exception;
}
