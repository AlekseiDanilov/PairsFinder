package ru.danilov.st.utils;

import ru.danilov.st.timeseries.TimeSeries;

public final class TimeSeriesUtil {

    private TimeSeriesUtil() {}

    public static double avg(TimeSeries series) {
        return sum(series) / series.size();
    }

    public static double avgRange(TimeSeries series, int start, int end) {
        return sumRange(series, start, end) / (end - start);
    }

    public static double sum(TimeSeries series) {
        double sum = 0;
        for (int i = 0; i < series.size(); i++) {
            sum += series.get(i).getValue();
        }
        return sum;
    }

    public static double sumRange(TimeSeries series, int start, int end) {
        double sum = 0;
        for (int i = start; i < end; i++) {
            sum += series.get(i).getValue();
        }
        return sum;
    }

}
