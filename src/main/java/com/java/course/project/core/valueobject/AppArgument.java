package com.java.course.project.core.valueobject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;

public class AppArgument {
    public static final int DEFAULT_TIMEOUT = 30 * 1000;
    private final URL url;
    private final int totalRequest;
    private final int concurrency;
    private final int timeout;

    public AppArgument(String urlString, int num, int concurrency, int timeout) throws MalformedURLException {
        this.url = new URL(urlString);
        this.totalRequest = num;
        this.concurrency = concurrency;
        this.timeout = timeout;
    }

    public URL getUrl() {
        return url;
    }

    public int getTotalRequest() {
        return totalRequest;
    }

    public int getConcurrency() {
        return concurrency;
    }

    public int getTimeout() {
        return timeout;
    }

    @Override
    public String toString() {
        return "AppArgument{" +
                "url=" + url +
                ", totalRequest=" + totalRequest +
                ", concurrency=" + concurrency +
                ", timeout=" + timeout +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppArgument that = (AppArgument) o;
        return totalRequest == that.totalRequest &&
                concurrency == that.concurrency &&
                timeout == that.timeout &&
                Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, totalRequest, concurrency, timeout);
    }
}
