package io.github.q843705423.autumn.request.facotory;

import io.github.q843705423.autumn.AbstractRequester;
import io.github.q843705423.autumn.AutumnException;
import io.github.q843705423.autumn.entity.Invocation;
import io.github.q843705423.autumn.request.handler.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DefaultRequestHandlerFactory extends AbstractRequester.AbstractRequestHandlerFactory {


    private List<IRequestInvocationHandler> requestInvocationHandlerList;

    protected Invocation deal(Invocation invocation) throws AutumnException {
        for (IRequestInvocationHandler iRequestInvocationHandler : requestInvocationHandlerList) {
            invocation = iRequestInvocationHandler.invocationHandler(invocation);
        }
        return invocation;

    }

    public DefaultRequestHandlerFactory() {
        this.requestInvocationHandlerList = new ArrayList<>();
        this.requestInvocationHandlerList.add(new NoParameterHandler());
        this.requestInvocationHandlerList.add(new AllBasicTypeAndBasicPackageHandler());
        this.requestInvocationHandlerList.add(new BaseBodyHandler());
        this.requestInvocationHandlerList.add(new RequestBodyHandler());
        this.requestInvocationHandlerList.add(new MultipartFileHandler());

        this.requestInvocationHandlerList.sort(Comparator.comparingInt(IRequestInvocationHandler::order));
    }



    public void add(IRequestInvocationHandler invocationHandler) {
        this.requestInvocationHandlerList.add(invocationHandler);
        this.requestInvocationHandlerList.sort(Comparator.comparingInt(IRequestInvocationHandler::order));
    }
}
