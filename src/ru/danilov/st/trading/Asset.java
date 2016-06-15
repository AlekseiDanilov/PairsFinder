package ru.danilov.st.trading;

import ru.danilov.st.timeseries.TimeSeries;

import java.util.Date;

public class Asset extends TimeSeries {

    private String ticker;

    public Asset(String ticker) {
        super();
        this.ticker = ticker;
    }

    public String getTicker() {
        return ticker;
    }

    public double getValue(Date date) {
        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).getDate().getDate() == date.getDate() &&
                    this.get(i).getDate().getMonth() == date.getMonth() &&
                    this.get(i).getDate().getYear() == date.getYear()) {
                return this.get(i).getValue();
            }
        }
        return -1;
    }
}
