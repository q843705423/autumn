package io.github.q843705423.autumn;

import io.github.q843705423.autumn.entity.Invocation;

public class AutumnException extends Exception {

    public AutumnException(String format, Invocation invocation) {
        super(format);
    }
}
