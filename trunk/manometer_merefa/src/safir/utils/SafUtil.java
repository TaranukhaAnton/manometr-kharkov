package safir.utils;

import safir.bdUtils.DAO;
import safir.constants.CommonConstants;
import safir.data.Group;
import safir.data.Sensor;
import safir.data.SensorTypes;
import safir.frame.SafirFrame;

import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SafUtil {
    // static int levelUser;
    // static String nameUser;
    public static void main(String Args[]) {
////		 int[] m = {0x00  ,0x01  ,0x00  ,0x0c };
//		 int[] m = {0x00  ,0x11  ,0x00  ,0x0c  ,0xe8  ,0x04  ,0x80  ,0x20  ,0xd4  ,0x03  ,0x80  ,0x60  ,0xac  ,0x0a  ,0x43 ,0xd4  ,0x24  ,0x35  ,0x3f ,0x19  ,0x00  ,0x01  ,0x01  ,0x00  ,0x20  ,0x00  ,0xa0  ,0x00  ,0x00  ,0x80  ,0x3f  ,0x00  ,0x00  ,0x80  ,0x3f  ,0xaa  ,0x55   ,0xff };//,0x8c 
//
//		 int sum = 0;
//		 
//		 
//		 for (int i = 0; i < m.length; i++) {
//			 sum+=m[i];
//			
//		}
//		 int checkSum = (sum)&255;
//		 System.out.println(Integer.toHexString(checkSum));
//		byte[] b = {(byte)0xc0,(byte)0x94,0x3e,0};
//		long n1 = ByteToInt(b[0], b[1]), n2 = ByteToInt(b[2], b[3]);
//		n1 = n1 | (n2 << 16);
//		System.out.println(n1);


        ////		 d4  24  35  3f
//		 // 0xb3, 0x2c, 0xcb, 0x3f
//		 Float i = ByteToFloat(m);
//	 System.out.println(" fl= " +i );
//		 // Float fl = new Float("");
//	// baseContD(new Float(16.), new Float(0.));
//		
////		float[] r = baseContD(-40f, 0f);
////		
////		for (int i = 0; i < r.length; i++) {
////			System.out.println(r[i]);
////			
//		}

        //
        //
        //
        //
        long l = 4101312;
        int[] b = getSerNumMas(l);
        printHexArray(b, true);


    }

    public static int[] getSerNumMas(long L) {
        int[] res = new int[3];
        res[0] = (byte) L;
        res[1] = (byte) (L >> 8);
        res[2] = (byte) (L >> 16);
        return res;
    }


    public static float LongToFloat(long L) {
        float res;
        int p = (int) ((L >> 23) & 0xff);
        int m = (int) ((L & 0x7fffffL) | 0x800000);
        res = (float) m * (float) Math.pow(2, p - 150);
        if ((L & 0x80000000L) == 0x80000000L)
            res = -res;
        return res;
    }

    public static byte[] floatToByteArray(float f) {
        byte[] bArray = new byte[4];
        ByteBuffer bBuffer = ByteBuffer.wrap(bArray);
        bBuffer.order(ByteOrder.LITTLE_ENDIAN);
        FloatBuffer fBuffer = bBuffer.asFloatBuffer();
        fBuffer.put(0, f);
        return bArray;
    }

    public static float ByteToFloat(int[] b) {
        // printHexArray(b);
        long n1 = ByteToInt(b[0], b[1]), n2 = ByteToInt(b[2], b[3]);
        n1 = n1 | (n2 << 16);
        return LongToFloat(n1);
    }

    public static float ByteToFloat(byte[] b) {
        long n1 = ByteToInt(b[0], b[1]), n2 = ByteToInt(b[2], b[3]);
        n1 = n1 | (n2 << 16);
        return LongToFloat(n1);
    }

    public static int ByteToInt(int n1, int n2) {
        // int n1=b0, n2=b1;
        if (n1 < 0)
            n1 += 256;
        if (n2 < 0)
            n2 += 256;
        n1 = n1 | (n2 << 8);
        return n1;
    }

    public static String printHexArray(Integer[] data) {
        StringBuffer inputBuffer = new StringBuffer();
        for (Integer newData : data)
            inputBuffer.append((newData > 15 ? Integer.toHexString(newData)
                    : "0" + Integer.toHexString(newData))
                    + "  ");

        System.out.println(inputBuffer);
        return inputBuffer.toString();

    }

    public static String printHexArray(int[] data, boolean print) {
        StringBuffer inputBuffer = new StringBuffer();
        for (Integer newData : data)
            inputBuffer.append((newData > 15 ? Integer.toHexString(newData)
                    : "0" + Integer.toHexString(newData))
                    + "  ");
        if (print)
            System.out.println(inputBuffer);
        return inputBuffer.toString();
    }

    public static String printHexArray(byte[] data, boolean print) {
        StringBuffer inputBuffer = new StringBuffer();
        for (byte newData : data) {
            // if (newData<0)
            // inputBuffer.append(Integer.toHexString(newData+256)+ ", ");
            // else
            // newData = (newData<0)?newData+256:newData;
            int r = new Integer(newData);
            r = (r < 0) ? r + 256 : r;

            inputBuffer.append("0x"
                    + (r > 15 ? Integer.toHexString(r) : "0"
                    + Integer.toHexString(r)) + ",  ");

        }
        if (print)
            System.out.println(inputBuffer);
        return inputBuffer.toString();

    }

    public static Icon createIcon(boolean isAvailable,
                                  Integer koefAmplificftion, Integer pos23, Integer[] tempFlags,
                                  Integer dataIsWrote, Integer k0diapIsWrote) {
        return createIcon(true, isAvailable, koefAmplificftion, pos23, tempFlags, dataIsWrote, k0diapIsWrote);
    }


    public static Icon createIcon(boolean isEnabled, boolean isAvailable,
                                  Integer koefAmplificftion, Integer pos23, Integer[] tempFlags,
                                  Integer dataIsWrote, Integer k0diapIsWrote) {
        BufferedImage image = new BufferedImage(24, 24,
                BufferedImage.TYPE_INT_ARGB);
        int[] black = {0, 0, 0, 255};
        int[] white = {255, 255, 255, 255};
        int[] red = {255, 0, 0, 255};
        int[] green = {0, 255, 0, 255};
        int[] yellow = {255, 200, 16, 255};
        byte[][] ico;
        ico = (tempFlags.length == 4) ? CommonConstants.ico4 : CommonConstants.ico3;
        WritableRaster raster = image.getRaster();
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 24; j++)
                switch (ico[j][i]) {
                    case 0:
                        raster.setPixel(i, j, black);
                        break;
                    case 1:
                        if (isEnabled) {
                            if (isAvailable)
                                raster.setPixel(i, j, white);
                            else
                                raster.setPixel(i, j, red);
                        } else {

                            raster.setPixel(i, j, yellow);

                        }
                            break;
                    case 2:
                        if (koefAmplificftion == 0)
                            raster.setPixel(i, j, white);
                        else if (koefAmplificftion == 1)
                            raster.setPixel(i, j, green);
                        else if (koefAmplificftion == 2)
                            raster.setPixel(i, j, red);
                        break;

                    case 3:
                        if (tempFlags[pos23] == 0)
                            raster.setPixel(i, j, white);
                        else if (tempFlags[pos23] == 1)
                            raster.setPixel(i, j, green);
                        else if (tempFlags[pos23] == 2)
                            raster.setPixel(i, j, red);
                        break;
                    case 4:
                        if (tempFlags[0] == 0)
                            raster.setPixel(i, j, white);
                        else if (tempFlags[0] == 1)
                            raster.setPixel(i, j, green);
                        else if (tempFlags[0] == 2)
                            raster.setPixel(i, j, red);
                        break;
                    case 5:
                        int pos = (pos23 == 1) ? 2 : 1;
                        if (tempFlags[pos] == 0)
                            raster.setPixel(i, j, white);
                        else if (tempFlags[pos] == 1)
                            raster.setPixel(i, j, green);
                        else if (tempFlags[pos] == 2)
                            raster.setPixel(i, j, red);
                        break;
                    case 6:
                        if (tempFlags[3] == 0)
                            raster.setPixel(i, j, white);
                        else if (tempFlags[3] == 1)
                            raster.setPixel(i, j, green);
                        else if (tempFlags[3] == 2)
                            raster.setPixel(i, j, red);
                        break;
                    case 7:
                        if (dataIsWrote == 0)
                            raster.setPixel(i, j, white);
                        else if (dataIsWrote == 1)
                            raster.setPixel(i, j, green);
                        else if (dataIsWrote == 2)
                            raster.setPixel(i, j, red);
                        break;
                    case 8:
                        if (k0diapIsWrote == 0)
                            raster.setPixel(i, j, white);
                        else if (k0diapIsWrote == 1)
                            raster.setPixel(i, j, green);
                        else if (k0diapIsWrote == 2)
                            raster.setPixel(i, j, red);
                        break;

                    default:
                        break;
                }

        }
        return new ImageIcon(image);
    }

    public static Icon createImptIcon() {
        BufferedImage image = new BufferedImage(24, 24,
                BufferedImage.TYPE_INT_ARGB);
        int[] black = {0, 0, 0, 255};
        int[] white = {255, 255, 255, 255};

        WritableRaster raster = image.getRaster();
        for (int i = 0; i < 24; i++) {
            for (int j = 0; j < 24; j++)
                switch (CommonConstants.imptIco[j][i]) {
                    case 0:
                        raster.setPixel(i, j, black);
                        break;
                    case 1:

                        raster.setPixel(i, j, white);
                        break;

                }

        }

        return new ImageIcon(image);
    }

    public static void log(Sensor sensor, String text, boolean time) {
        OutputStream file;
        String curTime = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"))
                .format(new Date(System.currentTimeMillis()));

        String type = SensorTypes.INSTANSE.sensorTypes.get(((Group) sensor
                .getParent()).getType()).name;

        try {

            String path = "C:\\log\\" + sensor.getYear() + "\\" + type + "\\";
            File myFile = new File(path);
            if (!myFile.exists())
                myFile.mkdirs();
            file = new FileOutputStream("C:\\log\\" + sensor.getYear() + "\\"
                    + type + "\\" + sensor.getSerNum() + ".log", true);
            PrintStream out = new PrintStream(file);
            if (time)
                out.print(curTime + "  ");
            out.println(text);
            out.flush();
            out.close();

        } catch (Exception e) {
            System.err.println("5" + e.getMessage());
        }

    }

    /*
     * Процедура вычисляющая по min max значению 8 констант диапазона.
     */
    public static float[] baseContD(Float Pmax, Float Pmin) {
        float[] res = new float[8];
        float[] tmp = new float[8];
        float d = Math.abs(Pmax - Pmin);

        while (d >= 1) {
            d = d / 10;
        }
        if ((d == 0.65) | (d == 0.7)) {

            d = (float) 0.63;
        }
        float[] standRow = {(float) 0.1, (float) 0.16, (float) 0.25,
                (float) 0.4, (float) 0.63};
        int indx = 0;
        for (int i = 0; i < standRow.length; i++)
            if (d == standRow[i]) {
                indx = i;
            }
        Float m = new Float(1.);
        for (int n = 0; n < 8; n++) {
            tmp[n] = standRow[indx] / m;
            if (indx == 0) {
                indx = 4;
                m = m * 10;
            } else {
                indx--;
            }
        }
        for (int i = 0; i < tmp.length; i++) {
            res[i] = tmp[0] / tmp[i];
        }
        return res;
    }

    public static float[] CalkAprox(Float[][] tbl, Float[] iout, Float[] tmp,
                                    int baseStr0, int baseStrD, int baseCol) throws Exception {
        int ColTNorm, p, m, i;
        double tt = 0, span = 0;

        // if((config & 4)==4) ColTNorm=baseCol;
        // else ColTNorm=0;
        // baseCol1=ColTNorm;

        ColTNorm = baseCol; // Выбор колонки для расчёта нелинейности

        p = tbl.length;
        double[][] MpI = new double[p][2];
        for (i = 0; i < p; i++) {
            MpI[i][0] = tbl[i][ColTNorm]; // заполнение по давлению из 23
            // первого столбца MPI
            MpI[i][1] = iout[i]; // заполнение током, соответствующим
            // давлению, второго столбца MPI
            if (i == 0)
                tt = MpI[i][0]; // нормализация значений в MPI
            MpI[i][0] = MpI[i][0] - tt;
        }
        double[] ApI = Aprox_2_3(MpI); // вычисление коэффициентов нелинейности
        m = tbl[0].length;
        double[][] Mt0 = new double[m][2];
        double[][] MtSp = new double[m][2];
        for (i = 0; i < m; i++) {
            Mt0[i][0] = tmp[i];
            MtSp[i][0] = tmp[i]; // заполнение значениями температуры первого
            // столбца MT0 и MTSp
            Mt0[i][1] = tbl[baseStr0][i]; // * заполнение давлением
            // соответствующим 0 давлению,
            // второго столбца MT0
            MtSp[i][1] = tbl[baseStrD][i] - tbl[baseStr0][i];// * заполнение
            // давлением
            // соответствующим
            // диапазону,
            // второго
            // столбца MTSp
            if (i == ColTNorm)
                span = MtSp[i][1];
        }
        for (i = 0; i < m; i++) {
            if (MtSp[i][1] == 0) {
                throw new Exception("SafException.Calc, SafException.DevZero");
            }
            MtSp[i][1] = span / MtSp[i][1]; // нормализация диапазона давлений
            // во втором столбце MTSp
        }
        double[] At0 = Aprox_2_3(Mt0);// вычисление коэффициентов ухода 0 от t
        double[] AtSp = Aprox_2_3(MtSp);// вычисление коэффициентов ухода
        // диапазона от t
        float[] A = new float[24];
        for (i = 0; i < 4; i++)
            A[23 - i] = new Float(ApI[i]);
        for (i = 0; i < 4; i++)
            A[7 - i] = new Float(At0[i]);
        for (i = 0; i < 4; i++)
            A[15 - i] = new Float(AtSp[i]);
        for (i = 0; i < 4; i++)
            A[i] = 0;
        for (i = 8; i < 12; i++)
            A[i] = 0;
        for (i = 0; i < 4; i++)
            A[i] = 0;
        return A;
    }

    public static void Koef3StrGr(double[][] Mpb) {
        int i, k, j;
        double Sum_y, Sum_yx, Sum_yx2, Sum_yx3, Sum_x, Sum_x2, Sum_x3, Sum_x4, Sum_x5, Sum_x6, x, x1, y, D;
        double[][] Massiv = new double[5][5];// 1..4
        double[][] New_mas = new double[5][5];// 1..4
        double[] Mas_y = new double[5];// 1..4
        double[] A = new double[4];

        int p = Mpb.length;

        Sum_y = 0;
        Sum_yx = 0;
        Sum_yx2 = 0;
        Sum_yx3 = 0;
        Sum_x = 0;
        Sum_x2 = 0;
        Sum_x3 = 0;
        Sum_x4 = 0;
        Sum_x5 = 0;
        Sum_x6 = 0;
        k = 1;
        for (i = 0; i < p; i++) {
            y = Mpb[i][1];
            x = Mpb[i][0];
            Sum_y += y;
            Sum_yx += x * y;
            x1 = x * x;
            Sum_yx2 += x1 * y;
            Sum_yx3 += x * x1 * y;
            Sum_x += x;
            Sum_x2 += x1;
            Sum_x3 += x1 * x;
            x1 = x1 * x1;
            Sum_x4 += x1;
            Sum_x5 += x * x1;
            Sum_x6 += x * x * x1;
            k++;
        }
        Mas_y[1] = Sum_y;
        Mas_y[2] = Sum_yx;
        Mas_y[3] = Sum_yx2;
        Mas_y[4] = Sum_yx3;
        Massiv[1][1] = k - 1;
        Massiv[1][2] = Sum_x;
        Massiv[1][3] = Sum_x2;
        Massiv[1][4] = Sum_x3;
        Massiv[2][1] = Sum_x;
        Massiv[2][2] = Sum_x2;
        Massiv[2][3] = Sum_x3;
        Massiv[2][4] = Sum_x4;
        Massiv[3][1] = Sum_x2;
        Massiv[3][2] = Sum_x3;
        Massiv[3][3] = Sum_x4;
        Massiv[3][4] = Sum_x5;
        Massiv[4][1] = Sum_x3;
        Massiv[4][2] = Sum_x4;
        Massiv[4][3] = Sum_x5;
        Massiv[4][4] = Sum_x6;
        D = opred(Massiv);
        for (i = 0; i < 4; i++) {
            New_mas = Massiv;
            for (j = 1; j < 5; j++)
                New_mas[j][i + 1] = Mas_y[j];
            A[i] = opred(New_mas) / D;
        }

    }

    public static double[] Aprox_2_3(double[][] Mpb) throws Exception {
        int i, j;
        int k;
        double Sum_y, Sum_yx, Sum_yx2, Sum_yx3, Sum_x, Sum_x2, Sum_x3, Sum_x4, Sum_x5, Sum_x6, x, x1, y, D, D0, D1, D2;
        double[][] Massiv = new double[5][5];// 1..4
        double[][] New_mas = new double[5][5];// 1..4
        double[] Mas_y = new double[5];// 1..4
        double[] A = new double[4];

        int p = Mpb.length;

        Sum_y = 0;
        Sum_yx = 0;
        Sum_yx2 = 0;
        Sum_yx3 = 0;
        Sum_x = 0;
        Sum_x2 = 0;
        Sum_x3 = 0;
        Sum_x4 = 0;
        Sum_x5 = 0;
        Sum_x6 = 0;
        k = 0;

        for (i = 0; i < p; i++) {
            y = Mpb[i][1];
            x = Mpb[i][0];
            Sum_y += y;
            Sum_yx += x * y;
            x1 = x * x;
            Sum_yx2 += x1 * y;
            Sum_yx3 += x * x1 * y;
            Sum_x += x;
            Sum_x2 += x1;
            Sum_x3 += x1 * x;
            x1 = x1 * x1;
            Sum_x4 += x1;
            Sum_x5 += x * x1;
            Sum_x6 += x * x * x1;
            k++;
        }

        if (p > 3) {
            k++;
            Mas_y[1] = Sum_y;
            Mas_y[2] = Sum_yx;
            Mas_y[3] = Sum_yx2;
            Mas_y[4] = Sum_yx3;

            Massiv[1][1] = k - 1;
            Massiv[1][2] = Sum_x;
            Massiv[1][3] = Sum_x2;
            Massiv[1][4] = Sum_x3;

            Massiv[2][1] = Sum_x;
            Massiv[2][2] = Sum_x2;
            Massiv[2][3] = Sum_x3;
            Massiv[2][4] = Sum_x4;

            Massiv[3][1] = Sum_x2;
            Massiv[3][2] = Sum_x3;
            Massiv[3][3] = Sum_x4;
            Massiv[3][4] = Sum_x5;

            Massiv[4][1] = Sum_x3;
            Massiv[4][2] = Sum_x4;
            Massiv[4][3] = Sum_x5;
            Massiv[4][4] = Sum_x6;
            D = opred(Massiv);
            for (i = 0; i < 4; i++) {
                for (int l = 0; l < 5; l++)
                    for (int m = 0; m < 5; m++)
                        New_mas[l][m] = Massiv[l][m];

                for (j = 1; j < 5; j++)
                    New_mas[j][i + 1] = Mas_y[j];
                if (D != 0)
                    A[i] = opred(New_mas) / D;
                else
                    throw new Exception(
                            "SafException.Calc, SafException.DecZero");
            }
        } else {

            D = k * Sum_x2 * Sum_x4 + 2 * Sum_x * Sum_x2 * Sum_x3 - Sum_x2
                    * Sum_x2 * Sum_x2 - k * Sum_x3 * Sum_x3 - Sum_x * Sum_x
                    * Sum_x4;
            D0 = Sum_y * Sum_x2 * Sum_x4 + Sum_yx * Sum_x3 * Sum_x2 + Sum_x
                    * Sum_x3 * Sum_yx2 - Sum_yx2 * Sum_x2 * Sum_x2 - Sum_x3
                    * Sum_x3 * Sum_y - Sum_yx * Sum_x * Sum_x4;
            D1 = k * Sum_yx * Sum_x4 + Sum_x * Sum_x2 * Sum_yx2 + Sum_y
                    * Sum_x3 * Sum_x2 - Sum_x2 * Sum_x2 * Sum_yx - Sum_yx2 * k
                    * Sum_x3 - Sum_x * Sum_y * Sum_x4;
            D2 = k * Sum_x2 * Sum_yx2 + Sum_x * Sum_yx * Sum_x2 + Sum_x * Sum_y
                    * Sum_x3 - Sum_x2 * Sum_x2 * Sum_y - Sum_x3 * Sum_yx * k
                    - Sum_yx2 * Sum_x * Sum_x;
            if (D != 0) {
                A[0] = D0 / D;
                A[1] = D1 / D;
                A[2] = D2 / D;
                A[3] = 0;
            } else
                throw new Exception("SafException.Calc, SafException.DecZero");
        }
        return A;
    }

    public static double opred(double[][] v) {
        double res;
        res = (v[1][1] * v[2][2] * v[3][3] * v[4][4] + v[1][1] * v[3][2]
                * v[2][4] * v[4][3] + v[1][1] * v[2][3] * v[4][2] * v[3][4]
                + v[2][1] * v[1][4] * v[3][3] * v[4][2] + v[2][1] * v[1][3]
                * v[3][2] * v[4][4] + v[1][2] * v[2][1] * v[3][4] * v[4][3]
                + v[1][2] * v[3][1] * v[2][3] * v[4][4] + v[2][2] * v[3][1]
                * v[1][4] * v[4][3] + v[1][3] * v[3][1] * v[2][4] * v[4][2]
                + v[1][4] * v[4][1] * v[2][3] * v[3][2] + v[1][3] * v[2][2]
                * v[4][1] * v[3][4] + v[1][2] * v[4][1] * v[2][4] * v[3][3])
                - (v[1][1] * v[2][4] * v[3][3] * v[4][2] + v[1][1] * v[2][3]
                * v[3][2] * v[4][4] + v[1][1] * v[2][2] * v[3][4]
                * v[4][3] + v[1][2] * v[2][1] * v[3][3] * v[4][4]
                + v[2][1] * v[1][4] * v[3][2] * v[4][3] + v[2][1]
                * v[1][3] * v[4][2] * v[3][4] + v[3][1] * v[1][4]
                * v[2][3] * v[4][2] + v[1][3] * v[3][1] * v[2][2]
                * v[4][4] + v[1][2] * v[3][1] * v[2][4] * v[4][3]
                + v[1][2] * v[2][3] * v[4][1] * v[3][4] + v[1][3]
                * v[4][1] * v[3][2] * v[2][4] + v[2][2] * v[1][4]
                * v[4][1] * v[3][3]);
        return res;
    }

    public static void simulateDataSensor(SafirFrame p, Sensor sensor,
                                          Group group)

    {
        final Map<Double, Float> map = new HashMap<Double, Float>();
        map.put(-40d, 3000f);
        map.put(-30d, 4000f);
        map.put(-10d, 6000f);
        map.put(-5d, 9000f);
        map.put(-4d, 9000f);
        map.put(5d, 12000f);
        map.put(13d, 12000f);
        map.put(23d, 17000f);
        map.put(35d, 21000f);
        map.put(50d, 26000f);
        map.put(51d, 26000f);
        map.put(55d, 26000f);
        map.put(80d, 33000f);

        for (int i = 0; i < group.getTemperatures().size(); i++) {
            if (i == group.getT23())
                continue;
            sensor.getTemperatures()[i] = map.get(group.getTemperatures()
                    .get(i));
            for (int j = 0; j < group.getPressures().size(); j++) {
                sensor.getTable()[j][i] = sensor.getTable()[j][group.getT23()];
            }

        }
        if (sensor.getNote() != null)
            sensor.setNote(sensor.getNote() + " Данные симулированы");
        else
            sensor.setNote("Данные симулированы");

        // sensor.setNote(note.getText());
        JTree tree = p.getTree();
        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
        model.nodeChanged(sensor);
        // tree.gettr
        // p.getTree().
        // /
        // sensor.
        Integer[] mas = sensor.getTempFlags();
        for (int j = 0; j < mas.length; j++) {
            mas[j] = 1;

        }

        sensor.setTempFlags(mas);

        try {
            DAO.INSTANSE.updateSensor(sensor);
        } catch (SQLException e) {
            JOptionPane.showConfirmDialog(null,
                    "Нет связи с базой данных. \n Обратитесь к сисадмину.",
                    "База данных недоступна.", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.WARNING_MESSAGE);
        }

    }

}
