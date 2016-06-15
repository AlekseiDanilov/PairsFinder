package ru.danilov.st.timeseries;

import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;
import ru.danilov.st.data.Serializer;
import ru.danilov.st.utils.UtilForR;

public class ADFTest {
    private double pValue;
    private double statistic;

    public ADFTest(TimeSeries series) {
        String vectorR = new String();
        vectorR = UtilForR.createRVector(series);

        RConnection connection = null;

        try {

            connection = new RConnection();
            connection.eval("library(tseries)");
            connection.eval("t = adf.test(" + vectorR +")");
            pValue = connection.eval("t$p.value").asDouble();
            statistic = connection.eval("t$statistic").asDouble();
        } catch (RserveException e) {
            e.printStackTrace();
        } catch (REXPMismatchException e) {
            e.printStackTrace();
        }finally{
            connection.close();
        }

    }

    public double getPValue() {
        return ((double)((int) (pValue * 100))) / ((double)100.0);
    }

    public double getStatistic() {
        return ((double)((int) (statistic * 100))) / ((double)100.0);
    }
}
