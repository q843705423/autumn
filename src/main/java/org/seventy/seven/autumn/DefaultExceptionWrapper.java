package org.seventy.seven.autumn;

import org.seventy.seven.autumn.entity.ResponseWrapper;

public class DefaultExceptionWrapper extends AbstractExceptionWrapper {
    @Override
    public ResponseWrapper wrap(Exception e) {
        return new ResponseWrapper(e);
    }
}
