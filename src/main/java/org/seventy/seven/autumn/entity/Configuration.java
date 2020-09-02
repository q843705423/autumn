package org.seventy.seven.autumn.entity;

public class Configuration {

    public Configuration(String defaultUrlPrefix) {
        this.defaultUrlPrefix = defaultUrlPrefix;
    }

    private String defaultUrlPrefix;

    public String getDefaultUrlPrefix() {
        return defaultUrlPrefix;
    }

    public void setDefaultUrlPrefix(String defaultUrlPrefix) {
        this.defaultUrlPrefix = defaultUrlPrefix;
    }
}
