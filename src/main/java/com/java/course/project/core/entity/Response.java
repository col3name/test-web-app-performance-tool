package com.java.course.project.core.entity;

import com.java.course.project.core.InvalidEntityException;

import java.util.Objects;

public class Response {
    private final Integer responseCode;
    private final Integer transmittedBytes;
    private final Long responseTime;

    public Response(Integer responseCode, Integer transmittedBytes, Long responseTime) throws InvalidEntityException {
        if (responseCode < 100 || responseCode > 600) {
            throw new InvalidEntityException("Invalid response code '" + responseCode + "'");
        }

        if (transmittedBytes < 0) {
            throw new InvalidEntityException("transmittedBytes must be more than 0");
        }

        if (responseTime < 0) {
            throw new InvalidEntityException("responseTime must be more than 0");
        }

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
