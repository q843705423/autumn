package io.github.q843705423.autumn;

import io.github.q843705423.autumn.entity.Invocation;

public class DefaultFixUrlPrefixProvider implements ObjectProvider<String> {


    private String urlPrefix;


    public DefaultFixUrlPrefixProvider(String urlPrefix) {
        this.urlPrefix = urlPrefix;
    }

    @Override
    public String provider(Invocation invocation) {
        return urlPrefix;
    }
}
