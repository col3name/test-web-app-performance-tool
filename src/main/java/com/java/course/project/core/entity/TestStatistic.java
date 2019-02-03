package com.java.course.project.core.entity;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestStatistic {
    private final Integer totalRequest;
    private final Integer concurrencyLevel;
    private final Long totalTestTime;
    private final List<Response> reports;
    private Integer countFailedRequest;
    private Integer totalTransferBytes;
    private Long totalResponseTime;
    private final List<Response> responses;

    public TestStatistic(Integer totalRequest, Integer concurrencyLevel, Long totalTestTime, List<Response> responses) {
        this.totalRequest = totalRequest;
        this.concurrencyLevel = concurrencyLevel;
        this.totalTestTime = totalTestTime;
        this.reports = responses;
        this.responses = responses;

        this.countFailedRequest = 0;
        this.totalTransferBytes= 0;
        this.totalResponseTime= 0L;

        responses.sort(Comparator.comparing(Response::getResponseTime));
        for (Response response : responses) {
            if (response.getResponseCode() >= 300) {
                countFailedRequest++;
            }

            totalTransferBytes += response.getTransmittedBytes();
            totalResponseTime += response.getResponseTime();
        }
    }

    public Map<Integer, Long> getPercentiles(List<Integer> percents) {
        Map<Integer, Long> result = new HashMap<>();
        percents.forEach(percentile -> result.put(percentile, calculateOrdinaryRank(responses, percentile)));
        return result;
    }

    public Integer getConcurrencyLevel() {
        return concurrencyLevel;
    }

    public Long getTotalTestTime() {
        return totalTestTime;
    }

    public Integer getTotalRequest() {
        return totalRequest;
    }

    public List<Response> getReports() {
        return reports;
    }

    public Integer getCountFailedRequest() {
        return countFailedRequest;
    }

    public Integer getTotalTransferBytes() {
        return totalTransferBytes;
    }

    public Long getTotalResponseTime() {
        return totalResponseTime;
    }

    public Long getRequestPerSecond() {
        return totalTestTime / reports.size() * 1000;
    }

    private Long calculateOrdinaryRank(List<Response> responses, double percentile) {
        int index = (int) (percentile / 100 * responses.size()) - 1;
        return responses.get(index).getResponseTime();
    }

    @Override
    public String toString() {
        return "TestStatistic{" +
                "concurrencyLevel=" + concurrencyLevel +
                ", totalRequest=" + totalRequest +
                ", totalTestTime=" + totalTestTime +
                ", countFailedRequest=" + countFailedRequest +
                ", totalTransferBytes=" + totalTransferBytes +
                ", totalResponseTime=" + totalResponseTime +
                ", requestPerSecond=" + getRequestPerSecond() +
                '}';
    }
}
