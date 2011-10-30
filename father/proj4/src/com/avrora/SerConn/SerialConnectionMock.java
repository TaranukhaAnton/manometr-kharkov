package com.avrora.SerConn;


import com.avrora.exceptions.SerialConnectionException;
import org.jfree.data.time.Millisecond;

import java.util.Arrays;

public class SerialConnectionMock {
    public static final SerialConnectionMock INSTANSE = new SerialConnectionMock();
    private static Float[] lastValue = new Float[48];


    public static Float[] getCurrents() throws SerialConnectionException {
        for (int i = 0; i < 48; i++) {
            if (lastValue[i] == null) {
                lastValue[i] = new Float(10);
            }

            lastValue[i] = (float) (lastValue[i] * (0.90 + 0.2 * Math.random()));
        }

        lastValue[0] =10f;
        return lastValue;
    }


}
