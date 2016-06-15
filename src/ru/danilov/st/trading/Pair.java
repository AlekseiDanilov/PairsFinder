package ru.danilov.st.trading;

import ru.danilov.st.timeseries.ItemTimeSeries;
import ru.danilov.st.timeseries.Regression;
import ru.danilov.st.timeseries.TimeSeries;
import ru.danilov.st.utils.TimeSeriesUtil;

public class Pair {

    private Asset x;
    private Asset y;
    private Asset u;
    private boolean cointegrate;
    private Regression regression;

    public Pair(Asset x, Asset y) {
        this.x = x;
        this.y = y;
        this.regression = new Regression(y, x);
        this.u = regression.getResidue();
        this.x.testerADF();
        this.y.testerADF();
        this.u.testerADF();
        cointegrate = !x.isStationary() && !y.isStationary() && u.isStationary();
    }

    public String getName() {
        return x.getTicker() + "/" + y.getTicker();
    }

    public boolean isCointegrated() {
        return cointegrate;
    }

    public double calcCorrelation() {
        double sumCh = 0, sumZnOne = 0, sumZnTwo = 0;
        if (x.size() == y.size()) {
            for (int i = 0; i < x.size(); i++) {
                sumCh = sumCh + ((x.get(i).getValue() - TimeSeriesUtil.avg(x))) * (y.get(i).getValue() - TimeSeriesUtil.avg(y));
                sumZnOne = sumZnOne + (x.get(i).getValue() - TimeSeriesUtil.avg(x)) * (x.get(i).getValue() - TimeSeriesUtil.avg(x));
                sumZnTwo = sumZnTwo + (y.get(i).getValue() - TimeSeriesUtil.avg(y)) * (y.get(i).getValue() - TimeSeriesUtil.avg(y));
            }
        }
        else return 0;
        return ((double)((int) ((sumCh / Math.sqrt(sumZnOne * sumZnTwo)) * 100))) / ((double)100.0);
    }

    public Asset getResidue() {
        return u;
    }

    public Regression getRegression() {
        return regression;
    }

    public Asset getX() {
        return x;
    }

    public Asset getY() {
        return y;
    }
}
