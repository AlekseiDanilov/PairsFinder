package ru.danilov.st.timeseries;

import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;
import ru.danilov.st.data.Serializer;
import ru.danilov.st.trading.Asset;
import ru.danilov.st.utils.UtilForR;

public class Regression {
    private double a;
    private double b;
    private Asset residue;

    public Regression(Asset y, Asset x) {
        String rX = UtilForR.createRVector(x);
        String rY = UtilForR.createRVector(y);
        RConnection connection = null;

        try {
            connection = new RConnection();
            connection.eval("x = " + rX);
            connection.eval("y = " + rY);
            connection.eval("reg = lm(y ~ x)");
            a = connection.eval("reg$coefficients[1]").asDouble();
            b = connection.eval("reg$coefficients[2]").asDouble();

            this.residue = new Asset("U(t) = " + y.getTicker() + " - (" +  ((double)((int) (a * 100))) / ((double)100.0) + ") - (" +  ((double)((int) (b * 100))) / ((double)100.0) + ") * " + x.getTicker());
            for (int i = 0; i < y.size(); i++) {
                residue.add(new ItemTimeSeries((y.get(i).getValue() - a - b * x.get(i).getValue()), y.get(i).getDate()));
            }
        } catch (RserveException e) {
            e.printStackTrace();
        } catch (REXPMismatchException e) {
            e.printStackTrace();
        }finally{
            connection.close();
        }
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public Asset getResidue() {
        return residue;
    }
}
