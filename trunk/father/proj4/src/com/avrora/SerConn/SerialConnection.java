package com.avrora.SerConn;

import com.avrora.common.CommonVariables;
import com.avrora.exceptions.SerialConnectionException;
import com.avrora.util.AvroraUtils;
import gnu.io.*;


import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.TooManyListenersException;
import java.util.Vector;

public class SerialConnection implements SerialPortEventListener, CommPortOwnershipListener {
    private SerialParameters parameters;
    private OutputStream os;
    private InputStream is;
    private CommPortIdentifier portId;
    private SerialPort sPort;
    private boolean open;
    private boolean writeToConsol = false;
    private byte[] reciveData;

    public static final SerialConnection INSTANSE = new SerialConnection();

    // private Logger log;

    private SerialConnection() {

        try {
            openConnection();

        } catch (SerialConnectionException e) {
            e.printStackTrace();
            JOptionPane.showConfirmDialog(null, "Неможливо вiдкрити RS232 порт ." + CommonVariables.INSTANSE.getCommPortNum(), "", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.ERROR_MESSAGE);
        }


    }

    public void openConnection() throws SerialConnectionException {
        parameters = new SerialParameters();
        // log = Logger.getLogger(this.getClass());


        parameters.setBaudRate(CommonVariables.INSTANSE.getBaudRate());
        parameters.setPortName(CommonVariables.INSTANSE.getCommPortNum());
//        parameters.setBaudRate(115200);
        //      parameters.setPortName("COM8");
        parameters.setParity("None");
        System.out.println("/////" + parameters.getBaudRate() + " " + CommonVariables.INSTANSE.getCommPortNum());


        open = false;

        try {
            portId = CommPortIdentifier.getPortIdentifier(parameters.getPortName());

            System.out.println(parameters.getPortName());
        } catch (NoSuchPortException e) {
            System.out.println(e);
            throw new SerialConnectionException(e.getMessage());
        }

        // Open the port represented by the CommPortIdentifier object. Give
        // the open call a relatively long timeout of 30 seconds to allow
        // a different application to reliquish the port if the user
        // wants to.
        try {
            sPort = (SerialPort) portId.open("Safir technological program", 40000);
        } catch (PortInUseException e) {
            throw new SerialConnectionException(e.getMessage());
        }

        // Set the parameters of the connection. If they won't set, close the
        // port before throwing an exception.
        try {
            setConnectionParameters();
        } catch (SerialConnectionException e) {
            sPort.close();
            throw e;
        }

        // Open the input and output streams for the connection. If they won't
        // open, close the port before throwing an exception.
        try {
            os = sPort.getOutputStream();
            is = sPort.getInputStream();
        } catch (IOException e) {
            sPort.close();
            throw new SerialConnectionException("Error opening i/o streams");
        }

        try {
            sPort.addEventListener(this);
        } catch (TooManyListenersException e) {
            sPort.close();
            throw new SerialConnectionException("too many listeners added");
        }

        // Set notifyOnDataAvailable to true to allow event driven input.
        sPort.notifyOnDataAvailable(true);

        // Set notifyOnBreakInterrup to allow event driven break handling.
        sPort.notifyOnBreakInterrupt(true);

        // Set receive timeout to allow breaking out of polling loop during
        // input handling.
        try {
            sPort.enableReceiveTimeout(30);
        } catch (UnsupportedCommOperationException e) {
        }

        // Add ownership listener to allow ownership event handling.
        portId.addPortOwnershipListener(this);

        open = true;
    }

    /**
     * Sets the connection parameters to the setting in the parameters object.
     * If set fails return the parameters object to origional settings and throw
     * exception.
     */
    private void setConnectionParameters() throws SerialConnectionException {

        // Save state of parameters before trying a set.
        int oldBaudRate = sPort.getBaudRate();
        int oldDatabits = sPort.getDataBits();
        int oldStopbits = sPort.getStopBits();
        int oldParity = sPort.getParity();
        // Set connection parameters, if set fails return parameters object
        // to original state.
        try {
            sPort.setSerialPortParams(parameters.getBaudRate(), parameters
                    .getDatabits(), parameters.getStopbits(), parameters
                    .getParity());

        } catch (UnsupportedCommOperationException e) {
            parameters.setBaudRate(oldBaudRate);
            parameters.setDatabits(oldDatabits);
            parameters.setStopbits(oldStopbits);
            parameters.setParity(oldParity);
            throw new SerialConnectionException("Unsupported parameter");
        }

        // Set flow control.
        try {
            sPort.setFlowControlMode(parameters.getFlowControlIn()
                    | parameters.getFlowControlOut());
        } catch (UnsupportedCommOperationException e) {
            throw new SerialConnectionException("Unsupported flow control");
        }
    }

    /**
     * Close the port and clean up associated elements.
     */
    public void closeConnection() {
        // If port is already closed just return.
        if (!open) {
            return;
        }
        // Check to make sure sPort has reference to avoid a NPE.
        if (sPort != null) {
            try {
                // close the i/o streams.
                os.close();
                is.close();
            } catch (IOException e) {
                System.err.println(e);
            }

            // Close the port.
            sPort.close();

            // Remove the ownership listener.
            portId.removePortOwnershipListener(this);
        }
        System.out.println("close");
        open = false;
    }


    public boolean isOpen() {
        return open;
    }

    public void serialEvent(SerialPortEvent e) {
        // Create a StringBuffer and int to receive input data.
        // StringBuffer inputBuffer = new StringBuffer();
        Vector<Integer> v = new Vector<Integer>();
        int newData = 0;

        try {
            Thread.sleep(100);
            //	if (parameters.getBaudRate()<9600)
            //			Thread.sleep(1000);
        } catch (InterruptedException ee) {

        }
        if (e.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            while (newData != -1) {
                try {
                    newData = is.read();
                    if (newData == -1)
                        break;
                    v.add(new Integer(newData));

                } catch (IOException ex) {
                    System.err.println(ex);
                    return;
                }
            }
            //byte[] arr = new byte[v.size()-2];
            reciveData = new byte[v.size()];
            for (int i = 0; i < v.size(); i++) {
                reciveData[i] = v.get(i).byteValue(); ///null poiter
            }
            //  Short checkSum = AvroraUtils.computeCheckSum(arr);
            //  System.out.println("��������� �� "+ Integer.toHexString(checkSum));
            //  System.out.println("1 "+ Integer.toHexString(v.get(v.size()-2).byteValue()));
            // System.out.println("2 "+ Integer.toHexString(v.get(v.size()-1).byteValue()));


            //  System.out.println("�������� �� "+Integer.toHexString(AvroraUtils.bytesToShort(v.get(v.size()-2).byteValue(),v.get(v.size()-1).byteValue())));


            if (writeToConsol)
                System.out.println("прочитали " + AvroraUtils.printHexArray(reciveData, false));


//			try {
//				rxMessage = new RxMessage(arr);
//			} catch (incorrectChecksumException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//				System.err.println("�������� ����������� �����");
//			}

        }

    }

    public void ownershipChange(int type) {
    }


    public byte[] writeMessage(byte[] m)
            throws SerialConnectionException {

        if (!open) throw new SerialConnectionException("порт Comm закрыт.");
        for (int i = 0; i < 3; i++) {
            reciveData = null;
            try {
                writeToCom(m);
                Thread.sleep(200);
            } catch (Exception e1) {

                e1.printStackTrace();
                // log.error(e1);
            }


            for (int j = 0; j < 300; j++) {
                try {
                    Thread.sleep(10);

                } catch (InterruptedException e) {
                }
                if (reciveData != null) {
                    //   System.out.println(j+" iteration");
                    break;
                }
            }

            try {
                if (reciveData != null) {
                    short crc = AvroraUtils.computeCheckSum(Arrays.copyOf(reciveData, reciveData.length - 2));//todo
                    int e1 = (reciveData[reciveData.length - 1] < 0) ? reciveData[reciveData.length - 1] + 256 : reciveData[reciveData.length - 1];
                    int e2 = (reciveData[reciveData.length - 2] < 0) ? reciveData[reciveData.length - 2] + 256 : reciveData[reciveData.length - 2];
                    short receivedCRC = (short) ((e1 << 8) | e2);

                    if (crc == receivedCRC)
                        break;
                    else {
                        reciveData = null;
                        System.err.println("Неверная контрольная сумма.");
                    }

                } else {
                    System.err.println("не приняли ничего.");
                }
            } catch (Throwable t) {
                System.err.println("поймали Throwable");
                reciveData = null;

            }
        }

        if (reciveData == null) {
            throw new SerialConnectionException("Не отвечает или неверная контрольная сумма.");
        }
        return reciveData;

    }


    private synchronized void writeToCom(byte[] mm) throws IOException {

        if (writeToConsol)
            System.out.println("пишем " + AvroraUtils.printHexArray(mm, false));
        os.write(mm);

    }


    public static Float[] getCurrents() throws SerialConnectionException {
        byte[] s = {1, 2, 3};
        byte[] bb;
        bb = INSTANSE.writeMessage(s);
        Float[] res = new Float[33];
        for (int i = 0; i < 33; i++) {
            byte[] b = Arrays.copyOfRange(bb, i * 4, i * 4 + 4);
            res[i] = AvroraUtils.ByteToFloat(b);
        }
        return res;
    }

}
