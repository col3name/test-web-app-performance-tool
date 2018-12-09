package com.java.course.project;

import com.java.course.project.core.domainmodel.Response;
import com.java.course.project.service.AppArgumentParserImpl;
import com.java.course.project.service.presenter.StreamReportPresenter;

import java.util.List;
import java.util.logging.Logger;

import static java.util.Arrays.asList;

public class Main {

    public static final List<Response> RESPONSES = asList(
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

    public static final Logger LOGGER = Logger.getLogger(Main.class.getName());


    public static final List<Integer> PERCENTS = asList(50, 80, 90, 95, 99, 100);

    public static void main(String[] args) {
        try {
            AppController controller = new AppController(new AppArgumentParserImpl(args), new StreamReportPresenter(System.out));
            controller.execute();
        } catch (Exception e) {
            LOGGER.warning(e.getMessage());
        }
    }
}
