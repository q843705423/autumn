package io.github.q843705423.autumn;

import io.github.q843705423.autumn.entity.Invocation;
import io.github.q843705423.autumn.entity.ResponseWrapper;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.List;

import static io.github.q843705423.autumn.util.RequestMappingUtil.calculateUrl;
import static io.github.q843705423.autumn.util.RequestMappingUtil.getUrl;


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

        RequestMapping annotation = superclass.getAnnotation(RequestMapping.class);
        if (annotation == null) {
            throw new AutumnException(String.format("%s need @RequestMapping annotation, please add this annotation to this class", superclass.getName()));
        }

        List<String> urls = calculateUrl(annotation.value(), getUrl(method));
        assert !urls.isEmpty();
        Invocation invocation = new Invocation(o.getClass(), method, params);
        invocation.setUri(urls.get(0));
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
