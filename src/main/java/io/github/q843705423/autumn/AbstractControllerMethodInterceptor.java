package io.github.q843705423.autumn;

import io.github.q843705423.autumn.entity.ResponseWrapper;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.List;

import static io.github.q843705423.autumn.util.RequestMappingUtil.calculateUrl;
import static io.github.q843705423.autumn.util.RequestMappingUtil.getUrl;


/**
 * 抽象 控制器 方法 拦截器
 */
public abstract class AbstractControllerMethodInterceptor implements MethodInterceptor {

    public abstract String getUrlPrefix(Class<?> clazz, Method method, Object[] params);

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


        return call(getUrlPrefix(superclass, method, params), urls.get(0), params, method);
    }

    private Object call(String urlPrefix, String uri, Object[] params, Method method) throws Exception {
        AbstractRequester abstractRequester = getAbstractRequester();
        final String url = urlPrefix + uri;
        ResponseWrapper response = abstractRequester.request(url, params, method);
        return getAbstractResponseProcessor().processingResponse(url, params, method, response);
    }

    protected abstract AbstractResponseProcessor getAbstractResponseProcessor();

    protected abstract AbstractRequester getAbstractRequester();

}
