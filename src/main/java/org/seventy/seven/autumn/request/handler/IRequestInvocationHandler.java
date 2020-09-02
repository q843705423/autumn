package org.seventy.seven.autumn.request.handler;


import org.seventy.seven.autumn.AutumnException;
import org.seventy.seven.autumn.entity.Invocation;

public interface IRequestInvocationHandler {

    Invocation invocationHandler(Invocation invocation) throws AutumnException;

    default int order() {
        return 0;
    }
}
