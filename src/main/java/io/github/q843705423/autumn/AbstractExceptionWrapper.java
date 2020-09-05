package io.github.q843705423.autumn;

import io.github.q843705423.autumn.entity.ResponseWrapper;

public abstract class AbstractExceptionWrapper {
    public abstract ResponseWrapper wrap(Exception e);

}
