package io.github.q843705423.autumn;

import io.github.q843705423.autumn.entity.Invocation;
import io.github.q843705423.autumn.entity.ResponseWrapper;

public abstract class AbstractResponseProcessor {
    public abstract Object processingResponse(Invocation invocation, ResponseWrapper response) throws Exception;
}
