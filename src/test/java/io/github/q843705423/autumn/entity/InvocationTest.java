package io.github.q843705423.autumn.entity;

import org.junit.Test;

public class InvocationTest {

    @Test
    public void getUrl() {
        Invocation invocation = new Invocation(null, null, null, null);
        invocation.setUrlPrefix("http://127.0.0.1/");
        invocation.setUri("");
        String url = invocation.getUrl();
        System.out.println(url);
    }
}