package io.github.q843705423.autumn;


import org.springframework.cglib.proxy.Callback;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractContextFactory {


    public <T> T getBean(Class<T> clazz) {
        return getBean(clazz, getEnhancer(clazz));
    }

    /**
     * enhancer
     *
     * @param clazz clazz
     * @param <T>   return value type
     * @return Enhancer
     */
    private <T> Enhancer getEnhancer(Class<T> clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setClassLoader(clazz.getClassLoader());
        enhancer.setCallback(getMethodControllerInterceptor(clazz));
        return enhancer;
    }

    protected <T> Callback getMethodControllerInterceptor(Class<T> controllerClazz) {
        return new DefaultControllerMethodInterceptor(controllerClazz,
                getUrlPrefixProvider(), getAbstractRequester(), getAbstractResponseProcessor());
    }

    private <T> T getBean(Class<T> clazz, Enhancer enhancer) {
        Constructor<?>[] constructors = clazz.getConstructors();
        Constructor<?> constructor = constructors[0];
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        List<Object> collect = Arrays.stream(parameterTypes).map(s -> null).collect(Collectors.toList());
        if (parameterTypes.length == 0) {
            return (T) enhancer.create();
        } else {
            return (T) enhancer.create(parameterTypes, collect.toArray(new Object[]{collect.size()}));
        }
    }


    protected abstract ObjectProvider<String> getUrlPrefixProvider();

    protected abstract AbstractRequester getAbstractRequester();

    protected abstract AbstractResponseProcessor getAbstractResponseProcessor();
    protected abstract ObjectProvider<RestTemplate> getRestTemplateProvider();

}
