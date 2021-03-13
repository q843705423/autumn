package io.github.q843705423.autumn.response.handler;

import io.github.q843705423.autumn.entity.Invocation;
import io.github.q843705423.autumn.entity.ResponseReturn;
import io.github.q843705423.autumn.entity.ResponseWrapper;

public interface IResponseInvocationHandler {
    ResponseReturn deal(Invocation invocation, ResponseWrapper responseWrapper) throws Exception;

    default int order() {
        return 0;
    }

}
