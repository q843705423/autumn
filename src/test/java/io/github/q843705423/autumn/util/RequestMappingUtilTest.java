package io.github.q843705423.autumn.util;

import org.junit.Assert;
import org.junit.Test;

public class RequestMappingUtilTest {

    @Test
    public void getUrl() {
        Assert.assertEquals("/a/b/c", RequestMappingUtil.join("/a/b", "c"));
    }

    @Test
    public void test2() {

        Assert.assertEquals("/a/b/c", RequestMappingUtil.join("/a/b/", "c"));
    }

    @Test
    public void test3() {

        Assert.assertEquals("/a/b/c", RequestMappingUtil.join("/a/b", "/c"));
    }

    @Test
    public void test4() {
        Assert.assertEquals("/a/b/c", RequestMappingUtil.join("/a/b/", "/c"));

    }
}