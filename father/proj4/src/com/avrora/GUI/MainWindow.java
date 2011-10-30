package com.avrora.GUI;


import com.avrora.SerConn.SerialConnection;
import com.avrora.SerConn.SerialConnectionMock;
import com.avrora.bdutils.DAO;
import com.avrora.exceptions.SerialConnectionException;
import com.avrora.util.WorkThread;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MainWindow extends JFrame {
    private static final Logger log = Logger.getLogger(MainWindow.class);
    private static final int CHANNEL_COUNT = 48;
    static ExtendedButton[] buttons;
    StatusBar statusBar;
    MyChartPanel chartPane;
    TechSchema techSchema;
    List<LinkedList<Float>> l;
    JLabel skvNumLabel;
        private static final int alias[] = {110, 109, 108,  76,  94, 102, 101, 100, 166, 221, 167, 168,
                                        169, 170, 171, 111, 112, 113, 114, 115, 127, 119, 172, 173,
                                        275, 174, 298, 176, 391, 264, 143,  96, 151, 152, 150, 142,
                                        149, 141, 131, 153, 154,  14, 180, 160, 159, 158, 165, 164};
//    private static final int alias[] = {165, 158, 160, 180, 14, 153, 149, 142, 151, 96, 264, 391,
//            176, 119, 127, 115, 112, 168, 100, 101, 102, 94, 108, 110,
//            150, 109, 76, 166, 221, 167, 169, 170, 171, 111, 113, 114,
//            172, 173, 275, 174, 298, 143, 152, 141, 131, 154, 159, 164};
    private static final int crossMap[] ={46, 45, 43, 42, 41, 39, 36, 35, 32, 31, 29, 28, 27, 21, 20, 19, 16, 11, 7, 6, 5, 4, 2, 0, 34};




    int channelSelected;

    public MainWindow() {
        channelSelected = 0;
        buttons = new ExtendedButton[CHANNEL_COUNT];
        getContentPane().setLayout(new BorderLayout());
        JPanel skvNumberPane = new JPanel();

        skvNumLabel = new JLabel("Свердловина № " + alias[0]);

        skvNumLabel.setFont(new Font("Verdana", Font.BOLD, 20));
        skvNumberPane.add(skvNumLabel);


        techSchema = new TechSchema();
        chartPane = new MyChartPanel();
        JPanel jPane3 = new JPanel();

        //  jPanel.setSize(new Dimension(400, 400));

        GridLayout experimentLayout = new GridLayout(4, 12);
        jPane3.setLayout(experimentLayout);
        l = new LinkedList<LinkedList<Float>>();
        List<ChannelState> channelStates = DAO.getChannelStates();
        for (int i = 0; i < CHANNEL_COUNT; i++) {
//            final int num = i;

            buttons[i] = new ExtendedButton(String.valueOf(alias[i]), this, i, channelStates.get(i));

            jPane3.add(buttons[i]);
            l.add(new LinkedList<Float>());
        }


        JSplitPane splitPaneV = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, techSchema, chartPane);

        splitPaneV.setDividerLocation(600);

//        splitPaneV.setResizeWeight(0.5);
        //  splitPaneV.setDividerLocation(0.5);


        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, splitPaneV, jPane3);


        setPreferredSize(new Dimension(1200, 800));
        getContentPane().add(skvNumberPane, BorderLayout.NORTH);
        getContentPane().add(splitPane, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        statusBar = new StatusBar();
        getContentPane().add(statusBar, BorderLayout.SOUTH);
        //Display the window.
        pack();

        setExtendedState(JFrame.MAXIMIZED_BOTH);

        setVisible(true);
        WorkThread.setWin(this);
        WorkThread.getInstanse().StartWork();
    }


    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainWindow();
            }
        });


    }

    public void selectChannel(int channel) {
        //  System.out.println("channel = " + channel);
        chartPane.changeChannel(channel);
        techSchema.changeChannel(channel);
        channelSelected = channel;
        skvNumLabel.setText("Свердловина №" + alias[channel]);
    }

    public void addData() {
        try {
//            Float[] data = SerialConnectionMock.getCurrents();
            Float[] data = SerialConnection.getCurrents();
            //  Float[] data = SerConn.getCurrents();
//            for (Float aFloat : data) {
//                System.out.print(aFloat * 1000 + "  ");
//            }
//            for (Integer integer : crossMap.keySet()) {
//
//            }

            for (int i = 0; i < crossMap.length; i++) {
                int channel = crossMap[i];
                Float current = data[channel] * 1000;
                if ((current >= 4) && (current <= 20)) {
                    Float temperature = (current - 4) * 100 / 16 - 50;
                    addsShot(temperature, channel);
                    Float av = getAverage(channel);
                    if (channel == channelSelected) {
                        techSchema.addData(av.toString() + " °C");
                    }
                    chartPane.addData(av, channel);
                    buttons[channel].setData1(av.toString() + " °C");


                } else {
                    clear(channel);
                    buttons[i].setData1("--");
                    if (channel == channelSelected) {
                        techSchema.addData("");
                    }
                }
            }
            statusBar.setText("");
        } catch (SerialConnectionException e) {
            buttons[channelSelected].setData1("--");
            techSchema.addData("");


            statusBar.setText("      Встановлення зв'язку з контролером ...");
            log.error("ZW don't response.");
        }

    }


    void addsShot(Float f, int channel) {
        LinkedList<Float> list = l.get(channel);
        if (list.size() > 10) {
            list.removeFirst();
        }
        list.add(f);
    }

    Float getAverage(int channel) {
        LinkedList<Float> list = l.get(channel);
        int size = list.size();
        Float tmp = 0f;
        for (Float aFloat : list) {
            tmp += aFloat;
        }
        tmp = tmp / size;
        return new BigDecimal(tmp).setScale(2, RoundingMode.UP).floatValue();
    }

    void clear(int channel) {
        LinkedList<Float> list = l.get(channel);
        list.clear();
    }


}

