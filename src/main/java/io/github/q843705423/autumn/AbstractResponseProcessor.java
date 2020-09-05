package io.github.q843705423.autumn;

import io.github.q843705423.autumn.entity.ResponseWrapper;

import java.lang.reflect.Method;

public abstract class AbstractResponseProcessor {
    public abstract Object processingResponse(String url, Object[] params, Method method, ResponseWrapper response) throws Exception;
}
