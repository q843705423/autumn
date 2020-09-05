package io.github.q843705423.autumn;

import io.github.q843705423.autumn.entity.Invocation;

public interface ObjectProvider<T> {
    T provider(Invocation invocation);

}
