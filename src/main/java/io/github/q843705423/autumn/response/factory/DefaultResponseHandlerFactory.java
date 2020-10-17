package io.github.q843705423.autumn.response.factory;

import io.github.q843705423.autumn.entity.Invocation;
import io.github.q843705423.autumn.entity.ResponseReturn;
import io.github.q843705423.autumn.entity.ResponseWrapper;
import io.github.q843705423.autumn.response.handler.BaseObjectResponseHandler;
import io.github.q843705423.autumn.response.handler.IResponseInvocationHandler;
import io.github.q843705423.autumn.response.handler.ListResponseHandler;
import io.github.q843705423.autumn.response.handler.StringReturnResponseHandler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class DefaultResponseHandlerFactory {


    private List<IResponseInvocationHandler> responseInvocationHandlers;

    public DefaultResponseHandlerFactory() {
        responseInvocationHandlers = new ArrayList<>();
        responseInvocationHandlers.add(new StringReturnResponseHandler());
        responseInvocationHandlers.add(new ListResponseHandler());
        responseInvocationHandlers.add(new BaseObjectResponseHandler());
        responseInvocationHandlers.sort(Comparator.comparing(IResponseInvocationHandler::order));

    }

    public Object deal(Invocation invocation, ResponseWrapper responseWrapper) throws Exception {
        if (responseWrapper.hasError()) {
            throw responseWrapper.error();
        }
        for (IResponseInvocationHandler responseInvocationHandler : responseInvocationHandlers) {
            ResponseReturn deal = responseInvocationHandler.deal(invocation, responseWrapper);
            if (deal.isReturn()) {
                return deal.getReturnValue();
            }
        }
        return null;
    }

    public void add(IResponseInvocationHandler responseInvocationHandler) {
        responseInvocationHandlers.add(responseInvocationHandler);
        responseInvocationHandlers.sort(Comparator.comparing(IResponseInvocationHandler::order));
    }
}
