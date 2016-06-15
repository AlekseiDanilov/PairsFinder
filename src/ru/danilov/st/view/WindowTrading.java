package ru.danilov.st.view;

import ru.danilov.st.simulator.Trade;
import ru.danilov.st.trading.Pair;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class WindowTrading extends JFrame{

    private JPanel rootPanel;
    private JTextField tfSum;

    private JTextField tfDay;
    private JTextField tfMonth;
    private JTextField tfYear;

    private JButton shortAlongB;
    private JButton closeShort;
    private JButton longAshortB;
    private JButton closeLong;

    private JLabel status;
    private JLabel lBalance;

    private Trade trade;

    public WindowTrading(Pair pair) {
        super("Simulator Trading");
        setContentPane(rootPanel);
        setSize(500, 300);
        setResizable(false);

        shortAlongB.setText("Short " + pair.getY().getTicker() + " Long " + pair.getX().getTicker());
        closeShort.setText("Закрыть сделку");
        longAshortB.setText("Long " + pair.getY().getTicker() + " Short " + pair.getX().getTicker());
        closeLong.setText("Закрыть сделку");

        setVisible(true);

        initComponent(pair);

    }

    private void initComponent(Pair pair) {
        shortAlongB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date date = new Date();
                date.setYear(Integer.parseInt(tfYear.getText()) - 1900);
                date.setMonth(Integer.parseInt(tfMonth.getText()) - 1);
                date.setDate(Integer.parseInt(tfDay.getText()));

                if (trade == null) {
                    trade = new Trade(pair, Integer.parseInt(tfSum.getText()));
                }

                trade.shortAlongB(date);
                status.setText(trade.getMessage());
                lBalance.setText(String.valueOf(Math.round(trade.getBalance())));
            }
        });

        closeShort.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date date = new Date();
                date.setYear(Integer.parseInt(tfYear.getText()) - 1900);
                date.setMonth(Integer.parseInt(tfMonth.getText()) - 1);
                date.setDate(Integer.parseInt(tfDay.getText()));

                trade.closeShort(date);
                status.setText(trade.getMessage());
                lBalance.setText(String.valueOf(Math.round(trade.getBalance())));
            }
        });

        longAshortB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date date = new Date();
                date.setYear(Integer.parseInt(tfYear.getText()) - 1900);
                date.setMonth(Integer.parseInt(tfMonth.getText()) - 1);
                date.setDate(Integer.parseInt(tfDay.getText()));

                if (trade == null) {
                    trade = new Trade(pair, Integer.parseInt(tfSum.getText()));
                }

                trade.longAshortB(date);
                status.setText(trade.getMessage());
                lBalance.setText(String.valueOf(Math.round(trade.getBalance())));
            }
        });

        closeLong.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date date = new Date();
                date.setYear(Integer.parseInt(tfYear.getText()) - 1900);
                date.setMonth(Integer.parseInt(tfMonth.getText()) - 1);
                date.setDate(Integer.parseInt(tfDay.getText()));

                trade.closeLong(date);
                status.setText(trade.getMessage());
                lBalance.setText(String.valueOf(Math.round(trade.getBalance())));
            }
        });
    }
}
