package io.github.q843705423.autumn;

import io.github.q843705423.autumn.entity.Invocation;
import io.github.q843705423.autumn.entity.RequestInfo;
import io.github.q843705423.autumn.entity.ResponseWrapper;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.List;

import static io.github.q843705423.autumn.util.RequestMappingUtil.getTypeAndUrl;


/**
 * abstract controller method interceptor
 */
public abstract class AbstractControllerMethodInterceptor implements MethodInterceptor {

    public abstract String getUrlPrefix(Invocation invocation);

    private Class<?> superclass;


    public <T> AbstractControllerMethodInterceptor(Class<T> clazz) {
        this.superclass = clazz;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] params, MethodProxy methodProxy) throws Throwable {
        if ("equals".equals(method.getName()) && params.length == 1) {
            return params[0] == o;
        }
        if ("hashCode".equals(method.getName())) {
            return superclass.hashCode();
        }
        if ("toString".equals(method.getName())) {
            return superclass.getSimpleName() + "$1";
        }
        Invocation invocation = new Invocation(o.getClass(), method, params, method.getParameterTypes());
        Controller controller = superclass.getAnnotation(Controller.class);
        RestController restController = superclass.getAnnotation(RestController.class);
        if (controller == null && restController == null) {
            throw new AutumnException(String.format("%s need @Controller or @RestController annotation, please add this annotation to this class", superclass.getName()), invocation);
        }
        RequestMapping annotation = superclass.getAnnotation(RequestMapping.class);
        String[] prefix = annotation == null || annotation.value().length == 0 ? new String[]{"/"} : annotation.value();
        List<RequestInfo> typeAndUrl = getTypeAndUrl(prefix, method);
        RequestInfo uri;
        if (!typeAndUrl.isEmpty()) {
            uri = typeAndUrl.get(0);
        } else {
            throw new AutumnException("no url available", invocation);
        }
        invocation.setUri(uri.getUrl());
        invocation.setRequestMethod(uri.getRequestMethod());
        invocation.setUrlPrefix(getUrlPrefix(invocation));

        return call(invocation);
    }

    private Object call(Invocation invocation) throws Exception {
        AbstractRequester abstractRequester = getAbstractRequester();
        ResponseWrapper response = abstractRequester.request(invocation);
        return getAbstractResponseProcessor().processingResponse(invocation, response);
    }

    protected abstract AbstractResponseProcessor getAbstractResponseProcessor();

    protected abstract AbstractRequester getAbstractRequester();

}
