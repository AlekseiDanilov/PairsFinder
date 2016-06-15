package ru.danilov.st.simulator;

import ru.danilov.st.trading.Pair;

import java.util.Date;

public class Trade {

    private Pair pair;
    private String message;
    private double sum;
    private double balance;
    private double priceY;
    private double priceX;
    private int numberY;
    private int numberX;

    public Trade(Pair pair, double sum) {
        this.pair = pair;
        this.sum = sum;
        this.balance = sum;
    }

    public void shortAlongB(Date date) {
        priceY = pair.getY().getValue(date);
        priceX = pair.getX().getValue(date);
        if (priceX == -1 || priceY == -1) {
            message = "Не удалось, проверьте дату";
            return;
        }
        numberY = (int) ((balance / 2) / priceY);
        numberX = (int) ((balance / 2) / priceX);

        balance += (priceY * numberY);
        balance -= (priceX * numberX);

        message = "Успешно";
    }

    public void closeShort(Date date) {
        priceY = pair.getY().getValue(date);
        priceX = pair.getX().getValue(date);
        if (priceX == -1 || priceY == -1) {
            message = "Не удалось, проверьте дату";
            return;
        }

        balance -= (priceY * numberY);
        balance += (priceX * numberX);
        double profit = (balance / sum * 100 - 100);
        message = "Прибыль составила " + ((double)((int) (profit * 100))) / ((double)100.0) + " %";
    }

    public void longAshortB(Date date) {
        priceY = pair.getY().getValue(date);
        priceX = pair.getX().getValue(date);
        if (priceX == -1 || priceY == -1) {
            message = "Не удалось, проверьте дату";
            return;
        }
        numberY = (int) ((balance / 2) / priceY);
        numberX = (int) ((balance / 2) / priceX);
        balance -= (priceY * numberY);
        balance += (priceX * numberX);
        message = "Успешно";
    }

    public void closeLong(Date date) {
        priceY = pair.getY().getValue(date);
        priceX = pair.getX().getValue(date);
        if (priceX == -1 || priceY == -1) {
            message = "Не удалось, проверьте дату";
            return;
        }
        balance += (priceY * numberY);
        balance -= (priceX * numberX);
        double profit = (balance / sum * 100 - 100);
        message = "Прибыль составила " + ((double)((int) (profit * 100))) / ((double)100.0) + " %";
    }

    public double getBalance() {
        return balance;
    }

    public String getMessage() {
        return message;
    }
}
