package io.github.q843705423.autumn.request.handler;


import io.github.q843705423.autumn.AutumnException;
import io.github.q843705423.autumn.entity.Invocation;

public interface IRequestInvocationHandler {

    Invocation invocationHandler(Invocation invocation) throws AutumnException;

    default int order() {
        return 0;
    }
}
