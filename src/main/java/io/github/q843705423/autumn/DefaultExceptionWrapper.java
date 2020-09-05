package io.github.q843705423.autumn;

import io.github.q843705423.autumn.entity.ResponseWrapper;

public class DefaultExceptionWrapper extends AbstractExceptionWrapper {
    @Override
    public ResponseWrapper wrap(Exception e) {
        return new ResponseWrapper(e);
    }
}
