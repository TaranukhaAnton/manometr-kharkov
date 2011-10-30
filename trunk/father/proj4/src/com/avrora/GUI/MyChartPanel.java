package com.avrora.GUI;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.data.time.Millisecond;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;


public class MyChartPanel extends JPanel {
    private TimeSeriesCollection datasetT;
    TimeSeries[] serieses;

    public MyChartPanel() {
        serieses = new TimeSeries[48];

        for (int i = 0; i < 48; i++) {
            serieses[i] = new TimeSeries("");
            serieses[i].setMaximumItemCount(100000);

        }


        datasetT = new TimeSeriesCollection();
        datasetT.addSeries(serieses[0]);
        NumberAxis rangeAxis = new NumberAxis("");
        rangeAxis.setAutoRangeIncludesZero(false);
        rangeAxis.setNumberFormatOverride(new DecimalFormat("#0.00"));
        rangeAxis.setAutoRangeMinimumSize(3d);
        XYPlot plotT = new XYPlot(datasetT, null, rangeAxis, new StandardXYItemRenderer());
        plotT.setDomainAxis(new DateAxis("Час"));
        plotT.setBackgroundPaint(Color.lightGray);
        plotT.setDomainGridlinePaint(Color.white);
        plotT.setRangeGridlinePaint(Color.white);
        JFreeChart chartT = new JFreeChart("Температура, C°", plotT);
        chartT.setBorderPaint(Color.black);
        chartT.setBorderVisible(true);
        chartT.setBackgroundPaint(Color.white);
        ValueAxis axis1 = plotT.getDomainAxis();
        axis1.setAutoRange(true);
//        axis1.setFixedAutoRange(60000.0);
        axis1.setFixedAutoRange(1000 * 60 * 60 * 12);
        chartT.getLegend().setVisible(false);
        ChartPanel comp = new ChartPanel(chartT);
        comp.setMouseWheelEnabled(true);
        setLayout(new BorderLayout());

        add(comp);
    }

    public void addData(Float data, int chanel) {
        serieses[chanel].addOrUpdate(new Second(), data);
    }

    public void changeChannel(int num) {
        datasetT.removeAllSeries();
        datasetT.addSeries(serieses[num]);
    }


}
