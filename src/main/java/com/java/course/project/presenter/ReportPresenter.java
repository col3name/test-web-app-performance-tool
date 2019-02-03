package com.java.course.project.presenter;

import com.java.course.project.core.entity.TestStatistic;

import java.util.List;

public interface ReportPresenter {
    void present(List<Integer> percents, TestStatistic statistic);
}
