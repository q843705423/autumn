package org.seventy.seven.autumn;

import org.seventy.seven.autumn.entity.ResponseWrapper;

public abstract class AbstractExceptionWrapper {
    public abstract ResponseWrapper wrap(Exception e);

}
