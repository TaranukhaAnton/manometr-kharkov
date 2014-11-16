/*
 * Message.java
 *
 * Created on 20 лютого 2008, 10:31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package safir.data;

import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

/**
 * @author Taranukha Anton
 */
public class Message {
    private int toAddress; // ---1
    private int fromAddres; // --2
    private int command = 0; // -3
    private int length1; // -----4
    private int checkSum1; // ---5
    private Integer addressVar1; // -6
    private Integer addressVar2; // -7
    private Integer tuningByte; // --8
    private Integer length2; // -----9
    private List<Integer> data;
    private int checkSum2;

    public Message() {

    }

    public Message(Vector<Integer> v) throws Exception {
        // Конструктор используется для создани объекта
        // из вектора который получен из порта

        if (v.size() < 9) return;

        if ((v.elementAt(0) == 0x55) & (v.elementAt(v.size() - 2) == 0x55)
                & (v.elementAt(v.size() - 1) == 0xaa)) {
            toAddress = v.elementAt(1);
            fromAddres = v.elementAt(2);
            command = v.elementAt(3);
            length1 = v.elementAt(4);

            if (v.elementAt(6) != 0x32)// написать коментарий
            {
                addressVar1 = v.elementAt(6);
                addressVar2 = v.elementAt(7);
                tuningByte = v.elementAt(8);
                length2 = v.elementAt(9);
            /*
			 * boolean lastItemIs0x55 = false; for (int i = 10; i < size - 3;
			 * i++) { if (lastItemIs0x55) { lastItemIs0x55 = false; continue; }
			 * lastItemIs0x55 = v.elementAt(i) == 0x55;
			 * dataVector.add(v.elementAt(i)); }
			 */
                data = v.subList(10, v.size() - 3);
                for (int i = 1; i < data.size(); i++) {
                    if ((data.get(i - 1) == 0x55) && (data.get(i) == 0x55))
                        data.remove(i);
                }
            } else {
                data = v.subList(6, v.size() - 3);

                for (int i = 1; i < data.size(); i++) {
                    if ((data.get(i - 1) == 0x55) && (data.get(i) == 0x55))
                        data.remove(i);
                }

            }

            computeCheckSum();

            if ((v.elementAt(5) != checkSum1)
                    | (v.elementAt(v.size() - 3) != checkSum2))
                throw new Exception("Неверная контрольная сумма. " + data);


        } else
            throw new Exception("Неверный конец/начало пакета");
    }


    public Message(int toAddress,// - 1
                   int addressVar1,// ------ 6
                   int addressVar2,//--------7
                   int tuningByte,// ------- 8
                   int length2,// ---------- 9
                   int[] arrData// ------------ 10 ...
    ) {
        // конструктор используется для создания объекта
        // для записи переменной
        this.addressVar1 = addressVar1;
        this.addressVar2 = addressVar2;
        this.command = 0;
        data = new LinkedList<Integer>();
        for (int i : arrData)

            data.add(i);


        this.fromAddres = 0;
        this.length2 = length2;
        this.toAddress = toAddress;
        this.tuningByte = tuningByte;
    }

    public Message(int toAddress,// - 1
                   int addressVar1,// ------ 6
                   int addressVar2,//--------7
                   int tuningByte,// ------- 8
                   int length2// ----------- 9
    ) {

        this.toAddress = toAddress; // -----1
        this.fromAddres = 0; // ------------2
        this.command = 0; // ---------------3
        this.addressVar1 = addressVar1; // -6
        this.addressVar2 = addressVar2; //--7
        this.tuningByte = tuningByte; // ---8
        this.length2 = length2; // ---------9
        this.data = new LinkedList<Integer>();
    }

    public void computeCheckSum() {
        int tmp = toAddress + fromAddres + command + length1;
        checkSum1 = (-tmp) & 255;


        tmp = (addressVar1 != null) ? addressVar1 : 0;
        tmp += (addressVar2 != null) ? addressVar2 : 0;
        tmp += (tuningByte != null) ? tuningByte : 0;
        tmp += (length2 != null) ? length2 : 0;


        for (int i : data)
            tmp += i;
        checkSum2 = (-tmp) & 255;


    }

    public byte[] getTxDat() {
        Vector<Integer> v = new Vector<Integer>();
        v.add(0x55);
        v.add(toAddress);
        v.add(fromAddres);
        v.add(command);
        length1 = data.size();

        if (addressVar1 != null)
            length1++;

        if (addressVar2 != null)
            length1++;

        if (tuningByte != null)
            length1++;

        if (length2 != null)
            length1++;


        v.add(length1);
        if (length1 == 0x55) v.add(0x55);
        int tmp = toAddress + fromAddres + command + length1;
        checkSum1 = (-tmp) & 255;
        v.add(checkSum1);
        if (checkSum1 == 0x55) v.add(0x55);
		/*Конец формирования заголовка
		 * 
		 * Длина пристегнутого сообщения и контрольная сумма могут быть равны 0х55 
		 * поэтому делаем проверку, если равны добавляем после них еще 
		 * один байт 0х55
		 * 
		 * */

        // блок данных
        if (addressVar1 != null)
            v.add(addressVar1);


        if (addressVar2 != null)
            v.add(addressVar2);


        if (tuningByte != null)
            v.add(tuningByte);

        if (length2 != null)
            v.add(length2);

        for (int i : data) {
            v.add(i);
            if (i == 0x55) v.add(i);
        }

        tmp = (addressVar1 != null) ? addressVar1 : 0;
        tmp += (addressVar2 != null) ? addressVar2 : 0;
        tmp += (tuningByte != null) ? tuningByte : 0;
        tmp += (length2 != null) ? length2 : 0;


        for (int i : data)
            tmp += i;
        checkSum2 = (-tmp) & 255;

        v.add(checkSum2);
        if (checkSum2 == 0x55) v.add(0x55);


        v.add(0x55);
        v.add(0xaa);

        byte[] txDat = new byte[v.size()];
        int m = 0;
        for (Integer k : v)
            txDat[m++] = k.byteValue();
        return txDat;
    }

    public int getAddressVar1() {
        return addressVar1;
    }

    public void setTuningByte(int tuningByte) {
        this.tuningByte = tuningByte;
    }

    public void setToAddress(int toAddress) {
        this.toAddress = toAddress;
    }

    public void setLength2(int lenght2) {
        this.length2 = lenght2;
    }

    public void setLength1(int lenght1) {
        this.length1 = lenght1;
    }

    public void setFromAddres(int fromAddres) {
        this.fromAddres = fromAddres;
    }

    public void setCommand(int command) {
        this.command = command;
    }

    public void setCheckSum2(int checkSum2) {
        this.checkSum2 = checkSum2;
    }

    public void setCheckSum1(int checkSum1) {
        this.checkSum1 = checkSum1;
    }

    public void setAddressVar2(int addressVar2) {
        this.addressVar2 = addressVar2;
    }

    public void setAddressVar1(int addressVar1) {
        this.addressVar1 = addressVar1;
    }

    public int getTuningByte() {
        return tuningByte;
    }

    public int getToAddress() {
        return toAddress;
    }

    public int getLength2() {
        return length2;
    }

    public int getLength1() {
        return length1;
    }

    public int getFromAddres() {
        return fromAddres;
    }

    public int[] getData() {
        if (data == null) {
            System.out.println("data == null");
        }

        int[] returnData = new int[data.size()];

        for (int i = 0; i < data.size(); i++)
            returnData[i] = data.get(i);

        return returnData;
    }

    public int getCommand() {
        return command;
    }

    public int getCheckSum2() {
        return checkSum2;
    }

    public int getCheckSum1() {
        return checkSum1;
    }

    public int getAddressVar2() {
        return addressVar2;
    }

    public void setData(List<Integer> data) {
        this.data = data;
    }

}
