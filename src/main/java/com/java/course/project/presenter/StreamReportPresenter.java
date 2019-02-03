package com.java.course.project.presenter;

import com.java.course.project.core.entity.TestStatistic;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class StreamReportPresenter implements ReportPresenter {
    private static final Logger LOG = Logger.getLogger(StreamReportPresenter.class.getName());

    private OutputStream outputStream;

    public StreamReportPresenter(OutputStream outputStream) {
        this.outputStream = outputStream;
    }

    @Override
    public void present(List<Integer> percents, TestStatistic statistic) {
        writeln(statistic.toString());
        Map<Integer, Long> percentiles = statistic.getPercentiles(percents);

        for (Map.Entry<Integer, Long> entry : percentiles.entrySet()) {
            String string = entry.getKey() + " : " + entry.getValue();
            writeln(string);
        }
    }

    private void writeln(String string) {
        write(string + "\n");
    }

    private void write(String string) {
        try {
            outputStream.write(string.getBytes());
        } catch (IOException e) {
            LOG.warning(Arrays.toString(e.getStackTrace()));
        }
    }
}
