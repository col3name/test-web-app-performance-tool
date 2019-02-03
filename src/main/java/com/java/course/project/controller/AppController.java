package com.java.course.project.controller;

import com.java.course.project.Main;
import com.java.course.project.core.entity.TestStatistic;
import com.java.course.project.service.RequestService;
import com.java.course.project.presenter.ReportPresenter;
import com.java.course.project.presenter.StreamReportPresenter;

public class AppController {
    private final RequestService requestService;
    private final ReportPresenter presenter;

    public AppController(StreamReportPresenter presenter, RequestService requestService) {
        this.requestService = requestService;
        this.presenter = presenter;
    }

    public void execute() {
        try {
            TestStatistic testStatistic = requestService.execute();
            presenter.present(Main.PERCENTS, testStatistic);
        } catch (InterruptedException e) {
            Main.LOG.warning(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
