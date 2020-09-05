package io.github.q843705423.autumn;

import java.lang.reflect.Method;

public interface ObjectProvider<T> {
    T provider(Class<?> clazz, Method method, Object[] objects);

}
