package io.github.q843705423.autumn;

import java.lang.reflect.Method;

public class DefaultFixUrlPrefixProvider implements ObjectProvider<String> {


    private String urlPrefix;

    @Override
    public String provider(Class<?> clazz, Method method, Object[] objects) {
        return urlPrefix;
    }


    public DefaultFixUrlPrefixProvider(String urlPrefix) {
        this.urlPrefix = urlPrefix;
    }
}
