package safir.frame.components;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: anton
 * Date: 01.11.14
 * Time: 16:44
 * To change this template use File | Settings | File Templates.
 */
public class Test {
    public static void main(String[] args) {


        List<Double> pressures = new LinkedList<Double>();
        pressures.add(0d);
        pressures.add(1d);
        pressures.add(2d);
        pressures.add(3d);
        pressures.add(4d);
        pressures.add(5d);
        float[] adcCodes = new float[6];
        adcCodes[0] = 95;
        adcCodes[1] = 205;
        adcCodes[2] = 310;
        adcCodes[3] = 410;
        adcCodes[4] = 505;
        adcCodes[5] = 595;
        compute(pressures, adcCodes);

    }


    static float[] compute(List<Double> pressures, float[] adcCodes) {
        int n = pressures.size() - 1;
        float[] result = new float[n + 1];
        float k1 = (float) ((adcCodes[n] - adcCodes[0]) / (pressures.get(n) - pressures.get(0)));
        float k2 = 100 / (adcCodes[n] - adcCodes[0]);
        for (int i = 1; i < adcCodes.length - 1; i++) {
            float y = (float) (k1 * pressures.get(i) + adcCodes[0]);
            result[i] = (adcCodes[i] - y) *k2;
        }
        return result;
    }


}
