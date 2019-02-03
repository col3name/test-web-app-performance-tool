package com.java.course.project.service;

import com.java.course.project.core.InvalidEntityException;
import com.java.course.project.core.entity.Response;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

public class RequestCallable implements Callable<Response> {
    private static final Logger LOG = Logger.getLogger(RequestCallable.class.getName());
    private HttpURLConnection connection;

    public RequestCallable(URL url, int timeout) throws IOException {
        connection = (HttpURLConnection) url.openConnection();
        connection.setConnectTimeout(timeout);
    }

    @Override
    public Response call() {
        return connect();
    }

    private Response connect() {
        Response report = null;

        try {
            long start = System.currentTimeMillis();
            connection.connect();
            report = getResponse(System.currentTimeMillis() - start);
        } catch (IOException| InvalidEntityException e) {
            LOG.warning(Arrays.toString(e.getStackTrace()));
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

        return report;
    }

    private Response getResponse(Long responseTime) throws IOException, InvalidEntityException {
        Integer responseCode = connection.getResponseCode();
        Integer transferBytes = connection.getContentLength();
        return new Response(responseCode, transferBytes, responseTime);
    }
}
