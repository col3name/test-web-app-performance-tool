package com.java.course.project;

import com.java.course.project.controller.AppController;
import com.java.course.project.core.InvalidEntityException;
import com.java.course.project.core.entity.Response;
import com.java.course.project.core.valueobject.AppArgument;
import com.java.course.project.presenter.StreamReportPresenter;
import com.java.course.project.service.AppArgumentParserImpl;
import com.java.course.project.service.RequestService;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import static java.util.Arrays.asList;

public class Main {

    static {
        try {
            List<Response> RESPONSES = asList(
                    new Response(200, 20, 40L),
                    new Response(200, 20, 45L),
                    new Response(200, 20, 34L),
                    new Response(200, 20, 34L),
                    new Response(200, 20, 35L),
                    new Response(200, 20, 77L),
                    new Response(200, 20, 72L),
                    new Response(300, 20, 73L),
                    new Response(301, 20, 3063L),
                    new Response(200, 20, 5000L)
            );
        } catch (InvalidEntityException e) {
            e.printStackTrace();
        }
    }

    public static final Logger LOG = Logger.getLogger(Main.class.getName());

    public static final List<Integer> PERCENTS = asList(50, 80, 90, 95, 99, 100);

    public static void main(String[] args) {
        try {
//            Properties properties = new Properties();
//            try (InputStream input = new FileInputStream("config.properties")) {
//                properties.load(input);
//                String percentiles = properties.getProperty("percentiles");
//                LOG.info(percentiles);
//                List<String> percentilesList = asList(percentiles.split("\\s*,\\s*"));
//                percentilesList.forEach(System.out::println);
//            }

           AppArgumentParserImpl argumentParser = new AppArgumentParserImpl(args);

            CommandLineParser parser = new DefaultParser();
            Options options = new Options();
            options.addOption("--url", "testing site url");
            options.addOption("--num", "total number of requests");
            options.addOption("--concurrency", "number of threads");
            options.addOption(Option.builder()
                    .optionalArg(true)
                    .argName("--timeout")
                    .desc("The maximum number of milliseconds that is expected before the request is considered unsuccessful is, by default, 30 seconds.")
                    .valueSeparator(' ')
                    .type(Number.class)
                    .build());

            StreamReportPresenter presenter = new StreamReportPresenter(System.out);
            AppArgument applicationArgument = argumentParser.getApplicationArgument();
            RequestService requestService = new RequestService(applicationArgument, Executors.newFixedThreadPool(applicationArgument.getConcurrency()));

            AppController controller = new AppController(presenter, requestService);
            controller.execute();
        } catch (Exception e) {
            LOG.warning(e.getMessage());
        }
    }
}
