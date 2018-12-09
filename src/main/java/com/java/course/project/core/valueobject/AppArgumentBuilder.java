package com.java.course.project.core.valueobject;

import java.net.MalformedURLException;

import static com.java.course.project.core.valueobject.AppArgument.DEFAULT_TIMEOUT;

public class AppArgumentBuilder {
    private String urlString;
    private int concurrency;
    private int timeout = DEFAULT_TIMEOUT;
    private int totalRequest;

    public AppArgumentBuilder setUrlString(String urlString) {
        this.urlString = urlString;
        return this;
    }

    public AppArgumentBuilder setConcurrency(int concurrency) {
        this.concurrency = concurrency;
        return this;
    }

    public AppArgumentBuilder setTimeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public AppArgumentBuilder setTotalRequest(int totalRequest) {
        this.totalRequest = totalRequest;
        return this;
    }

    public AppArgument getResult() throws MalformedURLException {
        return new AppArgument(urlString, totalRequest, concurrency, timeout);
    }
}