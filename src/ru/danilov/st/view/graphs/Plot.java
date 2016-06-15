package ru.danilov.st.view.graphs;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.*;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RectangleInsets;
import org.jfree.ui.RefineryUtilities;
import ru.danilov.st.trading.Asset;
import ru.danilov.st.trading.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.*;

public class Plot extends JFrame {

    public Plot(String title) {
        super(title);

        //ChartPanel chartPanel = createDemoPanel();
        //chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        //chartPanel.setMouseZoomable(true, false);
        //setContentPane(chartPanel);
    }

    private static JFreeChart createChart(XYDataset dataset) {
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "", // title
                "Дата", // x-axis label
                "у.е.", // y-axis label
                dataset, // data
                true, // create legend?
                true, // generate tooltips?
                false // generate URLs?
        );
        chart.setBackgroundPaint(Color.white);
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.lightGray);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setAxisOffset(new RectangleInsets(2, 2, 2, 2));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
        XYItemRenderer r = plot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            //renderer.setBaseShapesVisible(true);
           // renderer.setBaseShapesFilled(true);
        }
        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("dd-MMM-yyyy"));
        return chart;
    }

    private static TimePeriodValuesCollection createDataset(Asset... assets) {
        java.util.List<TimePeriodValues> plots = new LinkedList<>();
        for (int i = 0; i < assets.length; i++) {
            plots.add(new TimePeriodValues(assets[i].getTicker()));
            for (int j = 0; j < assets[i].size(); j++) {
                plots.get(i).add(new Day(assets[i].get(j).getDate().getDate(), assets[i].get(j).getDate().getMonth() + 1,  assets[i].get(j).getDate().getYear() + 1900), assets[i].get(j).getValue());
            }
        }
        TimePeriodValuesCollection dataset = new TimePeriodValuesCollection();
        for (int i = 0; i < plots.size(); i++) {
            dataset.addSeries(plots.get(i));
        }
        dataset.setDomainIsPointsInTime(true);
        return dataset;
    }

    public static ChartPanel createPanel(String name, Asset... assets) {
        TimePeriodValuesCollection dataset = createDataset(assets);
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);

        JButton addLine = new JButton("Добавить линию");
        chartPanel.add(addLine);

        JTextField line = new JTextField();
        line.setPreferredSize(new Dimension(30, 22));
        chartPanel.add(line);

        addLine.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TimePeriodValues values = new TimePeriodValues("line " + line.getText());
                for (int i = 0; i < assets[0].size(); i++) {
                    values.add(new Day(assets[0].get(i).getDate().getDate(), assets[0].get(i).getDate().getMonth() + 1,  assets[0].get(i).getDate().getYear() + 1900), Double.parseDouble(line.getText()));
                }
                dataset.addSeries(values);
            }
        });
        return chartPanel;
    }

}