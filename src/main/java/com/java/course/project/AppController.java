package com.java.course.project;

import com.java.course.project.core.domainmodel.TestStatistic;
import com.java.course.project.core.valueobject.AppArgument;
import com.java.course.project.service.AppArgumentParser;
import com.java.course.project.service.RequestService;
import com.java.course.project.service.presenter.ReportPresenter;
import com.java.course.project.service.presenter.StreamReportPresenter;

public class AppController {
    private RequestService requestService;
    private ReportPresenter presenter;

    public AppController(AppArgumentParser argumentParser, StreamReportPresenter presenter) {
        AppArgument argument = argumentParser.getApplicationArgument();
        this.requestService = new RequestService(argument.getUrl(), argument.getConcurrency(), argument.getTotalRequest(), argument.getTimeout());
        this.presenter = presenter;
    }

    public void execute() {
        try {
            TestStatistic testStatistic = requestService.execute();
            presenter.present(Main.PERCENTS, testStatistic);
        } catch (InterruptedException e) {
            Main.LOGGER.warning(e.getMessage());
        }
    }
}
