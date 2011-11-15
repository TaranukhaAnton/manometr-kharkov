package com.avrora.util;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


public class AvroraUtils {

    public static short computeCheckSum(byte[] in) {
        short CRC = (short) 0xffff;
        boolean f = false;
        for (int j = 0; j < in.length; j++) {
            int item = in[j];
            item &= 0xff;
            CRC ^= item;
            for (int i = 0; i < 8; i++) {
                if ((CRC & 0x0001) == 1)
                    f = true;
                CRC >>= 1;
                CRC &= 0x7FFF;
                if (f) {
                    CRC ^= 0xa001;
                    f = false;
                }
            }
        }
        return CRC;
    }


    public static byte[] addComputedCheckSum(byte[] in) {
        short CRC = (short) 0xffff;
        boolean f = false;
        for (int j = 0; j < in.length; j++) {
            int item = in[j];
            item &= 0xff;
            CRC ^= item;
            for (int i = 0; i < 8; i++) {
                if ((CRC & 0x0001) == 1)
                    f = true;
                CRC >>= 1;
                CRC &= 0x7FFF;
                if (f) {
                    CRC ^= 0xa001;
                    f = false;
                }
            }
        }
        byte[] result = Arrays.copyOf(in, in.length + 2);
        // AvroraUtils.printHexArray(arr,true);
        //   System.out.println("посчитали кс "+ Integer.toHexString(crc));
        result[result.length - 2] = (byte) CRC;
        result[result.length - 1] = (byte) (CRC >> 8);


        return result;
    }


    public static String printHexArray(byte[] data, boolean print) {
        StringBuffer inputBuffer = new StringBuffer();
        for (byte newData : data) {
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

    public static String printHexArray(Integer[] data, boolean print) {
        StringBuffer inputBuffer = new StringBuffer();
        for (Integer newData : data)
            inputBuffer.append((newData > 15 ? Integer.toHexString(newData)
                    : "0" + Integer.toHexString(newData))
                    + "  ");
        if (print)
            System.out.println(inputBuffer);
        return inputBuffer.toString();
    }

    public static short bytesToShort(byte b1, byte b2) {
        int tmp1 = b2;
        int tmp2 = b1;
        tmp1 &= 0xFF;
        tmp2 &= 0xFF;
        return (short) ((tmp1 << 8) | tmp2);

    }

    public static float ByteToFloat(byte[] b) {


        long n1 = ByteToIntR(b[0], b[1]), n2 = ByteToIntR(b[2], b[3]);
        n1 = n1 | (n2 << 16);
        return LongToFloat(n1);
    }

    public static Long ByteToLong(byte[] b) {
        long n1 = ByteToInt(b[3], b[2]), n2 = ByteToInt(b[1], b[0]);
        return n1 | (n2 << 16);

    }

    public static Long ByteToLongRevers(byte[] b) {
        long n1 = ByteToInt(b[0], b[1]), n2 = ByteToInt(b[2], b[3]);
        return n1 | (n2 << 16);

    }


    public static int ByteToInt(int n2, int n1) {
        // int n1=b0, n2=b1;
        if (n1 < 0)
            n1 += 256;
        if (n2 < 0)
            n2 += 256;
        n1 = n1 | (n2 << 8);
        return n1;
    }



        public static int ByteToIntR(int n1, int n2) {
        // int n1=b0, n2=b1;
        if (n1 < 0)
            n1 += 256;
        if (n2 < 0)
            n2 += 256;
        n1 = n1 | (n2 << 8);
        return n1;
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

//	public static Timestamp createTimestampFromArray(byte[] in) {
//		Timestamp timestamp;
//		int Sec, Min, Month, Year, Data, Hour;
//		int tmp = AvroraUtils.bytesToShort(in[0], in[1]);
//		Sec = tmp&0x3f;
//		tmp>>=6;
//		Min = tmp&0x3f;
//		tmp>>=6;
//		Month = tmp&0x0f;
//		tmp = AvroraUtils.bytesToShort(in[2], in[3]);
//		Year = tmp&0x3f;
//		tmp>>=6;
//		Data=tmp&0x1f;
//		tmp>>=5;
//		Hour= tmp&0x1f;
//		timestamp = new Timestamp (Year-1900,Month,Data,Hour,Min,Sec,00);
//		
//		
//		
//		
//		
//		
//
//		return timestamp;
//

    //	}
    public static List<Short> create_list(Short begin, Short end, Short Max_value) {

        List<Short> list = new LinkedList<Short>();
        if (begin <= end) {
            for (Integer i = begin + 1; i < end + 1; i++)
                list.add(i.shortValue());

        } else {
            for (Integer i = begin + 1; i < Max_value; i++)
                list.add(i.shortValue());

            for (Integer i = 0; i < end + 1; i++)
                list.add(i.shortValue());
        }

        return list;

    }

}
