package com.avrora.GUI;

/**
 * Created by IntelliJ IDEA.
 * User: Антон
 * Date: 30.10.11
 * Time: 21:26
 * To change this template use File | Settings | File Templates.
 */
public class Tmp {
    private static final int alias1[] = {110, 109, 108, 76, 94, 102, 101, 100, 166, 221, 167, 168,
            169, 170, 171, 111, 112, 113, 114, 115, 127, 119, 172, 173,
            275, 174, 298, 176, 391, 264, 143, 96, 151, 152, 150, 142,
            149, 141, 131, 153, 154, 14, 180, 160, 159, 158, 165, 164};
    private static final int alias[] = {165, 158, 160, 180, 14, 153, 149, 142, 151, 96, 264, 391,
            176, 119, 127, 115, 112, 168, 100, 101, 102, 94, 108, 110,
            150, 109, 76, 166, 221, 167, 169, 170, 171, 111, 113, 114,
            172, 173, 275, 174, 298, 143, 152, 141, 131, 154, 159, 164};
    private static final int crossMap[] = {46, 45, 43, 42, 41, 39, 36, 35, 32, 31, 29, 28, 27, 21, 20, 19, 16, 11, 7, 6, 5, 4, 2, 0, 34};

    public static void main(String[] args) {
        for (int i = 0; i < 25; i++) {
            System.out.println("вход ацп "+(i+1)+" -> " +alias1[crossMap[i]]);

        }
    }
}
