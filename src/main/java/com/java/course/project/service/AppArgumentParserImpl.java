package com.java.course.project.service;

import com.java.course.project.core.IllegalAppParameterException;
import com.java.course.project.core.valueobject.AppArgument;
import com.java.course.project.core.valueobject.AppArgumentBuilder;

import java.net.MalformedURLException;

public class AppArgumentParserImpl implements AppArgumentParser {
    public static final String MESSAGE = "invalid argument\n" +
            "Usage: java.exe -jar target/benchmark.jar --url <url> --num <number or request> --concurrency <thread number> --timeout <request timeout>";
    public static final String URL_PARAMETER = "--url";
    public static final String CONCURRENCY_PARAMETER = "--concurrency";
    public static final String TOTAL_REQUEST_PARAMETER = "--num";
    public static final String TIMEOUT_PARAMETER = "--timeout";

    private AppArgument applicationArgument;

    public AppArgumentParserImpl(String[] args) throws IllegalAppParameterException {
        if (((args.length == 6) && checkRequiredParameter(args)) || (args.length == 8 && checkRequiredParameter(args) && args[6].equals(TIMEOUT_PARAMETER))) {
            String urlString = args[1];
            Integer totalRequest = toInt(args[3]);
            Integer concurrency = toInt(args[5]);
            AppArgumentBuilder builder = new AppArgumentBuilder();
            builder.setUrlString(urlString).setTotalRequest(totalRequest).setConcurrency(concurrency);
            if (args.length == 8) {
                builder.setTimeout(toInt(args[7]));
            }
            try {
                applicationArgument = builder.getResult();
            } catch (MalformedURLException e) {
                throwIllegalArgumentException();
            }
        } else {
            throwIllegalArgumentException();
        }
    }

    private void throwIllegalArgumentException() throws IllegalAppParameterException {
        throw new IllegalAppParameterException(AppArgumentParserImpl.MESSAGE);
    }

    public AppArgument getApplicationArgument() {
        return applicationArgument;
    }

    private boolean checkRequiredParameter(String[] args) {
        return args[0].equals(URL_PARAMETER) && args[2].equals(TOTAL_REQUEST_PARAMETER)
                && args[4].equals(CONCURRENCY_PARAMETER);
    }

    private Integer toInt(String arg) {
        return Integer.valueOf(arg);
    }
}
