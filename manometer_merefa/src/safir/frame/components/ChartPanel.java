package safir.frame.components;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: anton
 */
public class ChartPanel extends JPanel {

    private static final int LEFT_GAP = 20;
    private static final int RIGHT_GAP = 20;
    private static final int TOP_GAP = 20;
    private static final int BOTTOM_GAP = 40;
    private static final Color AXES_COLOR = Color.green;
    private static final Color GRAPH_COLOR = Color.red;
    private static final Stroke GRAPH_STROKE = new BasicStroke(2f);
    private static final Stroke AXES_STROKE = new BasicStroke(2f);
    private static final int GRAPH_POINT_WIDTH = 4;

    private static final int CHART_PANEL_WIDTH = 380;  //450
    private static final int CHART_PANEL_HEIGHT = 250;   //350

    private static final int GRAPH_AREA_WIDTH = CHART_PANEL_WIDTH - (RIGHT_GAP + LEFT_GAP);
    private static final int GRAPH_AREA_HEIGHT = CHART_PANEL_HEIGHT - (TOP_GAP + BOTTOM_GAP);

    private List<Double> pressures;
    private float[] adcCodes;

    public ChartPanel(List<Double> pressures, float[] adcCodes) {

        this.pressures = pressures;
        this.adcCodes = adcCodes;

//        this.pressures = new LinkedList<Double>();
//        this.pressures.add(0d);
//        this.pressures.add(1d);
//        this.pressures.add(2d);
//        this.pressures.add(3d);
//        this.pressures.add(4d);
//        this.pressures.add(5d);
//
//        this.adcCodes = new float[6];
//        this.adcCodes[0] = 95;
//        this.adcCodes[1] = 205;
//        this.adcCodes[2] = 310;
//        this.adcCodes[3] = 410;
//        this.adcCodes[4] = 505;
//        this.adcCodes[5] = 595;


        setSize(CHART_PANEL_WIDTH, CHART_PANEL_HEIGHT);
        setBackground(Color.gray);
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);

        float[] ar = compute(pressures, adcCodes);

        // drawChartBackground((Graphics2D) g);
        drawChart2((Graphics2D) g, ar);


    }

    private void drawChart2(Graphics2D g, float[] ar) {
        int array_size = ar.length;
        float max_k = 0;
        boolean haveNegative = false;
        for (float k : ar) {
            haveNegative |= k < 0;
            max_k = (Math.abs(k) > max_k) ? Math.abs(k) : max_k;
        }

        drawChartBackground(g, max_k, haveNegative);

        float kY = (max_k == 0) ? 1 : (GRAPH_AREA_HEIGHT / (max_k));
        float dX = GRAPH_AREA_WIDTH / (array_size - 1);

        int negativeOffset = 0;
        if (haveNegative) {
            kY = kY / 2;
            negativeOffset = GRAPH_AREA_HEIGHT / 2;
        }

        g.setColor(Color.DARK_GRAY);
        g.setStroke(new BasicStroke(1f));
        for (int i = 0; i < array_size - 1; i++) {
            g.drawLine((int) (i * dX + LEFT_GAP),
                    ((getHeight() - BOTTOM_GAP - (int) (kY * ar[i])) - negativeOffset),
                    (int) ((i + 1) * dX + LEFT_GAP),
                    ((getHeight() - BOTTOM_GAP - (int) (kY * ar[i + 1]))) - negativeOffset);

        }

    }

    private void drawChart(Graphics2D g) {
        int array_size = pressures.size();


        if (array_size != 0) {

            float max_adcCodes = adcCodes[0];
            float min_adcCodes = max_adcCodes;

            for (int i = 0; i < array_size; i++) {
                if (adcCodes[i] > max_adcCodes) {
                    max_adcCodes = adcCodes[i];
                }
                if (adcCodes[i] < min_adcCodes) {
                    min_adcCodes = adcCodes[i];
                }
            }

            float kY = (max_adcCodes == 0) ? 1 : GRAPH_AREA_HEIGHT / (max_adcCodes + 5.f);
            float dX = GRAPH_AREA_WIDTH / (array_size - 1);

            g.setStroke(GRAPH_STROKE);
            g.setColor(GRAPH_COLOR);

            for (int i = 0; i < array_size - 1; i++) {
                g.drawLine((int) (i * dX + LEFT_GAP), (int) ((getHeight() - BOTTOM_GAP - (int) (kY * adcCodes[i]))), (int) ((i + 1) * dX + LEFT_GAP), (int) ((getHeight() - BOTTOM_GAP - (int) (kY * adcCodes[i + 1]))));
                //g.drawLine((int) (i * dX + LEFT_GAP), (int) ((getHeight() - BOTTOM_GAP - (int)(kY * adcCodes[i]))), (int) (LEFT_GAP), (int) ((getHeight() - BOTTOM_GAP - (int)(kY * adcCodes[i]))));
            }
//            for (int i = 0; i < array_size; i++) {
//                int ttt = (int) ( (getHeight() - BOTTOM_GAP - (int)(kY * adcCodes[i])));
////                g.drawString("*", (int) (i * dX + LEFT_GAP), (int) ((getHeight() - BOTTOM_GAP - (int)(kY * adcCodes[i]))));
//////                if (i) {
//////                    g.drawString("", (int) (i * dX + LEFT_GAP), (int) ((getHeight() - kY * adcCodes[i] - BOTTOM_GAP + TOP_GAP)));
//////                }
//            }
        }
    }

    private void drawChartBackground(Graphics2D g2, float max_k, boolean haveNegative) {

        g2.setColor(AXES_COLOR);
        g2.setStroke(AXES_STROKE);


        int negativeOffset = 0;
        if (haveNegative) {
            negativeOffset = GRAPH_AREA_HEIGHT / 2;
        }

        // create x and y axes
        g2.drawLine(LEFT_GAP, getHeight() - BOTTOM_GAP, LEFT_GAP, TOP_GAP); // Y
        g2.drawLine(LEFT_GAP, getHeight() - BOTTOM_GAP - negativeOffset, getWidth() - RIGHT_GAP, getHeight() - BOTTOM_GAP - negativeOffset);  //X


        {

            g2.drawLine(LEFT_GAP, TOP_GAP, GRAPH_POINT_WIDTH + LEFT_GAP, TOP_GAP);
            if (haveNegative) {
                g2.drawLine(LEFT_GAP, getHeight() - BOTTOM_GAP, GRAPH_POINT_WIDTH + LEFT_GAP, getHeight() - BOTTOM_GAP);
            }

        }


        int X_HATCH_CNT = pressures.size() - 1;
        g2.drawString(String.format("%.3f", max_k), LEFT_GAP + 5, 15);
        if (haveNegative) {
            g2.drawString("-" + String.format("%.3f", max_k), LEFT_GAP + 5, getHeight() - BOTTOM_GAP + 10);
        }

        g2.drawString(pressures.get(0) + "", LEFT_GAP + 5, getHeight() - BOTTOM_GAP + 20 - negativeOffset);     //value of first pressure

        // and for x axis
        for (int i = 0; i < X_HATCH_CNT; i++) {
            int x0 = (i + 1) * (getWidth() - (LEFT_GAP + RIGHT_GAP)) / X_HATCH_CNT + LEFT_GAP;
            int y0 = getHeight() - BOTTOM_GAP;
            int y1 = y0 - GRAPH_POINT_WIDTH;
            g2.drawLine(x0, y0 - negativeOffset, x0, y1 - negativeOffset);

            g2.drawString(pressures.get(i + 1) + "", x0 - 10, y0 + 20 - negativeOffset);

            if (i == X_HATCH_CNT - 1) {      //value of pressure
                g2.drawString("P", x0, y0 - 20 - negativeOffset);        //name of axis
            }


        }


    }

    private float[] compute(List<Double> pressures, float[] adcCodes) {
        int n = pressures.size() - 1;
        float[] result = new float[n + 1];
        float k1 = (float) ((adcCodes[n] - adcCodes[0]) / (pressures.get(n) - pressures.get(0)));
        float k2 = 100 / (adcCodes[n] - adcCodes[0]);
        for (int i = 1; i < adcCodes.length - 1; i++) {
            float y = (float) (k1 * pressures.get(i) + adcCodes[0]);
            result[i] = (adcCodes[i] - y) * k2;
        }
        return result;
    }

}
