package com.java.course.project.service.presenter;

import com.java.course.project.core.domainmodel.TestStatistic;

import java.util.List;

public interface ReportPresenter {
    void present(List<Integer> percents, TestStatistic statistic);
}
