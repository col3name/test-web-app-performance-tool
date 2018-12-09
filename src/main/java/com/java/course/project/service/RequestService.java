package com.java.course.project.service;

import com.java.course.project.core.domainmodel.Response;
import com.java.course.project.core.domainmodel.TestStatistic;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class RequestService {
    public static final Logger LOGGER = Logger.getLogger(RequestService.class.getName());

    private final URL url;
    private final Integer concurrency;
    private final Integer totalRequest;
    private final int timeout;
    private final ExecutorService service;

    public RequestService(URL url, Integer concurrency, Integer totalRequest, int timeout) {
        this.url = url;
        this.concurrency = concurrency;
        this.totalRequest = totalRequest;
        this.timeout = timeout;
        this.service = Executors.newFixedThreadPool(concurrency);
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
            LOGGER.warning(e.getMessage());
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
            LOGGER.warning(Arrays.toString(e.getStackTrace()));
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
                LOGGER.info(e.getMessage());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
