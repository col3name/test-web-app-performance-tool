package com.java.course.project.service;

import com.java.course.project.core.entity.Response;
import com.java.course.project.core.entity.TestStatistic;
import com.java.course.project.core.valueobject.AppArgument;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class RequestService {
    private static final Logger LOG = Logger.getLogger(RequestService.class.getName());

    private final URL url;
    private final Integer concurrency;
    private final Integer totalRequest;
    private final int timeout;
    private final ExecutorService service;

    public RequestService(AppArgument argument, ExecutorService service) {
        this.url = argument.getUrl();
        this.concurrency = argument.getConcurrency();
        this.totalRequest = argument.getTotalRequest();
        this.timeout = argument.getTimeout();
        this.service = service;
    }

    public TestStatistic execute() throws InterruptedException {
        List<Callable<Response>> callableList = new ArrayList<>(totalRequest);

        long start = System.currentTimeMillis();
        connect(callableList);
        long totalTestTime = System.currentTimeMillis() - start;
        try {
            List<Future<Response>> futures = service.invokeAll(callableList);
            service.shutdown();
            return generateTestStatistic(futures, totalTestTime);
        } catch (InterruptedException e) {
            LOG.warning(e.getMessage());
            Thread.currentThread().interrupt();
            throw new InterruptedException(e.getMessage());
        }
    }

    private void connect(List<Callable<Response>> callableList) {
        for (int i = 0; i < totalRequest; i++) {
            newRequest(callableList);
        }
    }

    private void newRequest(List<Callable<Response>> callableList) {
        try {
            RequestCallable requestCallable = new RequestCallable(url, timeout);
            callableList.add(requestCallable);
        } catch (IOException e) {
            LOG.warning(Arrays.toString(e.getStackTrace()));
        }
    }

    private TestStatistic generateTestStatistic(List<Future<Response>> futures, long totalTestTime) {
        List<Response> responses = new ArrayList<>();
        if (futures == null) {
            return new TestStatistic(totalRequest, concurrency, totalTestTime, responses);
        }

        initResponses(futures, responses);

        return new TestStatistic(totalRequest, concurrency, totalTestTime, responses);
    }

    private void initResponses(List<Future<Response>> futures, List<Response> responses) {
        for (Future<Response> responseFuture : futures) {
            try {
                Response response = responseFuture.get();
                responses.add(response);
            } catch (ExecutionException e) {
                LOG.info(e.getMessage());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                LOG.warning(e.getMessage());
            }
        }
    }
}
