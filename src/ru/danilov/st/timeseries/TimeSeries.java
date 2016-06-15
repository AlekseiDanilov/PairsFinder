package ru.danilov.st.timeseries;

import org.apache.commons.math3.stat.inference.TTest;
import ru.danilov.st.utils.TimeSeriesUtil;

import java.util.LinkedList;
import java.util.List;

public class TimeSeries {

    private List<ItemTimeSeries> elements;
    private ADFTest test;

    public TimeSeries() {
        elements = new LinkedList<>();

    }

    public void add(ItemTimeSeries item) {
        elements.add(item);
    }

    public ItemTimeSeries get(int i) {
        return elements.get(i);
    }

    public int size() {
        return elements.size();
    }

    public boolean isStationary() {
        return test.getPValue() < 0.08;
    }

    public void testerADF() {
        test = new ADFTest(this);
    }

    public double getPValue() {
        return test.getPValue();
    }

    public double getStatistic() {
        return test.getStatistic();
    }

    public void reverse() {
        for (int i = 0; i < elements.size() / 2; i++) {
            swap(i, size() - i - 1);
        }
    }

    private void swap(int i, int j) {
        ItemTimeSeries tmp = new ItemTimeSeries();
        tmp.set(get(i));
        get(i).set(get(j));
        get(j).set(tmp);
    }
}
