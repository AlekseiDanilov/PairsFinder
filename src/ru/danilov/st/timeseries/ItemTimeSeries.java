package ru.danilov.st.timeseries;

import java.util.Date;

public class ItemTimeSeries {
    private double value;
    private Date date;

    public ItemTimeSeries() {
        this.date = new Date();
    }

    public ItemTimeSeries(double value) {
        this.value = value;
        this.date = new Date();
    }

    public ItemTimeSeries(double value, Date date) {
        this.value = value;
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public Date getDate() {
        return date;
    }

    public void set(ItemTimeSeries item) {
        this.value = item.getValue();
        this.date = item.getDate();
    }
}
