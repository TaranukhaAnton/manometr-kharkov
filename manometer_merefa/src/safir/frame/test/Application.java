package safir.frame.test;

import safir.frame.components.ChartPanel;

import javax.swing.*;
import java.awt.*;

public class Application {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }


        });
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Med Echo test program");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1000, 600));

        frame.setLayout(null);

//        float[] codes = {21726.293f, 23944.098f, 26168.344f, 28398.234f,
//                30627.0f, 32859.0f, 36091.87f, 37326.02f, 39562.117f, 41799.0f, 44036.598f};
//
//        float [] pressures = {-12.5f, -10f, -7.5f, -5f, -2.5f, 0f, 2.5f, 5f, 7.5f, 10f, 12.5f};


        float[] codes = {30.f, 45.f, 80.f, 85.f, 100.f};//, 105.f, 110.f, 130f, 140.f, 160.f, 180.f};
        float [] pressures = {10.f, 20.f, 30.f, 40.f, 50.f};

        ChartPanel chart = new ChartPanel(null, codes);
        frame.add(chart);

        // Показать окно
        frame.pack();
        frame.setVisible(true);
    }


}
