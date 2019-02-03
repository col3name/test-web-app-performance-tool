package com.java.course.project.core.domainmodel;

import java.util.Objects;

public class Response {
    private final Integer responseCode;
    private final Integer transmittedBytes;
    private final Long responseTime;

    public Response(Integer responseCode, Integer transmittedBytes, Long responseTime) {
        this.responseCode = responseCode;
        this.transmittedBytes = transmittedBytes;
        this.responseTime = responseTime;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public Integer getTransmittedBytes() {
        return transmittedBytes;
    }

    public Long getResponseTime() {
        return responseTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Response report = (Response) o;
        return Objects.equals(responseCode, report.responseCode) &&
                Objects.equals(transmittedBytes, report.transmittedBytes) &&
                Objects.equals(responseTime, report.responseTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(responseCode, transmittedBytes, responseTime);
    }

    @Override
    public String toString() {
        return "Response{" +
                "responseCode=" + responseCode +
                ", transmittedBytes=" + transmittedBytes +
                ", responseTime=" + responseTime +
                '}';
    }
}
