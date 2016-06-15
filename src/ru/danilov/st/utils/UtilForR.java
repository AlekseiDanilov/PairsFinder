package ru.danilov.st.utils;

import ru.danilov.st.timeseries.TimeSeries;

public final class UtilForR {

    private UtilForR() {}

    public static String createRVector(TimeSeries series) {
        String vector = new String();
        vector += "c(";
        for (int i = 0; i < series.size(); i++) {

            if (i < series.size() - 1) {
                vector +=  series.get(i).getValue() + ", ";
            } else {
                vector +=  series.get(i).getValue() + "";
            }
        }
        vector +=")";
        return vector;
    }

}
