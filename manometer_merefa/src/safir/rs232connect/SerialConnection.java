package safir.rs232connect;

import gnu.io.*;
import org.apache.log4j.Logger;
import safir.constants.SensorConst;
import safir.data.Message;
import safir.data.Sensor;
import safir.exceptions.NoDataException;
import safir.exceptions.SerialConnectionException;
import safir.utils.SafUtil;
import safir.utils.SafirProperties;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

public class SerialConnection implements SerialPortEventListener,
        CommPortOwnershipListener {
    // private JTextArea messageAreaIn;
    private SerialParameters parameters;
    private OutputStream os;
    private InputStream is;
    private CommPortIdentifier portId;
    private SerialPort sPort;
    private boolean open;
    private boolean writeToConsol = false;
    // private boolean dataAvailable = false;
    private volatile Message message;
    // private Properties props = null;
    public static final SerialConnection INSTANSE = new SerialConnection();
    private Logger log;


    private SerialConnection() {
        parameters = new SerialParameters();
        log = Logger.getLogger(this.getClass());
        String commPortName = SafirProperties.getCommPortName();
        parameters.setPortName(commPortName);
        open = false;
    }

    // private void loadParams() {
    // parameters.setPortName(props.getProperty("portName"));
    // parameters.setBaudRate(props.getProperty("baudRate"));
    // parameters.setFlowControlIn(props.getProperty("flowControlIn"));
    // parameters.setFlowControlOut(props.getProperty("flowControlOut"));
    // parameters.setParity(props.getProperty("parity"));
    // parameters.setDatabits(props.getProperty("databits"));
    // parameters.setStopbits(props.getProperty("stopbits"));
    //
    // }

    public void openConnection() throws SerialConnectionException {
        try {
            Enumeration portIdentifiers = CommPortIdentifier.getPortIdentifiers();
            while (portIdentifiers.hasMoreElements()) {
                Object o = portIdentifiers.nextElement();
                System.out.println("port = " + o);
            }


            String portName = parameters.getPortName();
            System.out.println("portName = " + portName);
            portId = CommPortIdentifier.getPortIdentifier(portName);
        } catch (NoSuchPortException e) {
            e.printStackTrace();
            throw new SerialConnectionException(e.getMessage());
        }

        // Open the port represented by the CommPortIdentifier object. Give
        // the open call a relatively long timeout of 30 seconds to allow
        // a different application to reliquish the port if the user
        // wants to.
        try {
            sPort = (SerialPort) portId.open("Safir technological program",
                    40000);
        } catch (PortInUseException e) {
            e.printStackTrace();
            throw new SerialConnectionException(e.getMessage());
        }

        // Set the parameters of the connection. If they won't set, close the
        // port before throwing an exception.
        try {
            setConnectionParameters();
        } catch (SerialConnectionException e) {
            e.printStackTrace();
            sPort.close();
            throw e;
        }

        // Open the input and output streams for the connection. If they won't
        // open, close the port before throwing an exception.
        try {
            os = sPort.getOutputStream();
            is = sPort.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            sPort.close();
            throw new SerialConnectionException("Error opening i/o streams");
        }

        // Create a new KeyHandler to respond to key strokes in the
        // messageAreaOut. Add the KeyHandler as a keyListener to the
        // messageAreaOut.
        // keyHandler = new KeyHandler(os);
        // messageAreaOut.addKeyListener(keyHandler);

        // Add this object as an event listener for the serial port.
        try {
            sPort.addEventListener(this);
        } catch (TooManyListenersException e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
    public void setConnectionParameters() throws SerialConnectionException {

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

        // Remove the key listener.
        // messageAreaOut.removeKeyListener(keyHandler);

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

        open = false;
    }

    /**
     * Send a one second break signal.
     */
    // public void sendBreak() {
    // sPort.sendBreak(1000);
    // }

    /**
     * Reports the open status of the port.
     *
     * @return true if port is open, false if port is closed.
     */
    public boolean isOpen() {
        return open;
    }

    /**
     * Handles SerialPortEvents. The two types of SerialPortEvents that this
     * program is registered to listen for are DATA_AVAILABLE and BI. During
     * DATA_AVAILABLE the port buffer is read until it is drained, when no more
     * data is availble and 30ms has passed the method returns. When a BI event
     * occurs the words BREAK RECEIVED are written to the messageAreaIn.
     */

    public void serialEvent(SerialPortEvent e) {
        // Create a StringBuffer and int to receive input data.
        StringBuffer inputBuffer = new StringBuffer();
        Vector<Integer> v = new Vector<Integer>();
        int newData = 0;

        // Determine type of event.
        switch (e.getEventType()) {

            case SerialPortEvent.DATA_AVAILABLE:
                while (newData != -1) {
                    try {
                        newData = is.read();
                        if (newData == -1)
                            break;
                        inputBuffer.append("0x" + (newData > 15 ? Integer
                                .toHexString(newData) : "0"
                                + Integer.toHexString(newData))
                                + ",  ");
                        v.add(new Integer(newData));

                    } catch (IOException ex) {
                        System.err.println(ex);
                        return;
                    }
                }

                try {
                    log.info("Читаем  " + new String(inputBuffer));
                    if (writeToConsol)
                        System.out.println("Читаем  " + new String(inputBuffer));

                    for (int i = 1; i < v.size(); i++) {
                        if ((v.get(i - 1) == 0x55) & (v.get(i) == 0x55))
                            v.removeElementAt(i);

                    }


                    message = new Message(v);

                } catch (Exception e1) {

                    log.error(e1);
                    // TODO выкинуть окошко, что мол херня со связью какая то
                    e1.printStackTrace();
                }
                // dataAvailable = true;
                // inputBuffer.append('\n');

                // if (writeFlag)
                // messageAreaIn.append(new String(inputBuffer));

                // System.out.println("mm"+new String(inputBuffer));
                break;

        }

    }

    /**
     * Handles ownership events. If a PORT_OWNERSHIP_REQUESTED event is received
     * a dialog box is created asking the user if they are willing to give up
     * the port. No action is taken on other types of ownership events.
     */
    public void ownershipChange(int type) {
        // if (type == CommPortOwnershipListener.PORT_OWNERSHIP_REQUESTED) {
        // new PortRequestedDialog(parent);
        // }
    }

    /**
     * A class to handle <code>KeyEvent</code>s generated by the
     * messageAreaOut. When a <code>KeyEvent</code> occurs the
     * <code>char</code> that is generated by the event is read, converted to
     * an <code>int</code> and writen to the <code>OutputStream</code> for
     * the port.
     *
     * @throws InterruptedException
     * @throws NoDataException
     */
    // class KeyHandler extends KeyAdapter {
    // OutputStream os;
    //
    // /**
    // * Creates the KeyHandler.
    // * @param os The OutputStream for the port.
    // */
    public synchronized Message readVar(int[] var, int address)
            throws InterruptedException, NoDataException {
        return writeMessage(new Message(address, var[0], var[1], 0x20, var[2]));
    }

    public synchronized Message writeVar(int[] var, int address, int data[])
            throws NoDataException, InterruptedException {

        return writeMessage(new Message(address, var[0], var[1], 0x60, var[2], data));
    }

    public synchronized Message writeMessage(Message m) throws NoDataException,
            InterruptedException {
        message = null;
        for (int i = 0; i < 3; i++) {
            try {
                writeToCom(m);
            } catch (IOException e1) {

                e1.printStackTrace();
                log.error(e1);
            }

            Thread.sleep(100);
            for (int j = 0; j < 100; j++) {
                Thread.sleep(10);
                if (message != null) {
                    break;
                }
            }

            if (message != null) {
                break;
            } else {
                System.out.println("Пишем еще раз.");
            }
        }
        if (message == null)
            throw new NoDataException("Message почемуто налл. :(");
        return message;

    }

    public synchronized Message writeData(byte[] mm) throws NoDataException,
            InterruptedException {
        message = null;
        for (int i = 0; i < 3; i++) {
            try {

                log.info("пишем   " + SafUtil.printHexArray(mm, false));
                if (writeToConsol)
                    System.out.println("пишем   " + SafUtil.printHexArray(mm, false));
                os.write(mm);
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                log.error(e1);
            }

            Thread.sleep(100);
            for (int j = 0; j < 100; j++) {
                Thread.sleep(10);
                if (message != null) {
                    break;
                }
            }

            if (message != null) {
                break;
            } else {
                System.out.println("Пишем еще раз.");
            }
        }
        if (message == null)
            throw new NoDataException("Хер его. Message почемуто налл. :(");
        return message;

    }

    private synchronized void writeToCom(Message m) throws IOException {

        byte[] mm = m.getTxDat();
        log.info("пишем    " + SafUtil.printHexArray(mm, false)); //!!!!!!!!
        if (writeToConsol)
            System.out.println("пишем  " + SafUtil.printHexArray(mm, false));
        os.write(mm);

    }

    ;

    public void resetSensor(int address) {
        byte[] txDat = new byte[8];
        txDat[0] = Integer.valueOf("85").byteValue();// 0x55
        txDat[1] = Integer.valueOf(address).byteValue();
        txDat[2] = 0;
        txDat[3] = Integer.valueOf("128").byteValue();// 0x80
        txDat[4] = 0;
        txDat[5] = Integer.valueOf((-(address + 128)) & 255).byteValue();
        txDat[6] = Integer.valueOf("85").byteValue();// 0x55
        txDat[7] = Integer.valueOf("170").byteValue();// 0xaa
        for (int i = 0; i < 3; i++) {
            writeToCom(txDat);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
        }
    }

    // 55 04 00 00 04 f8 fc 7f 00 04 81 55 aa
    public String getCheckSum(int address) {
        //int address = sensor.getAddress();
        byte[] txDat = new byte[13];

        txDat[0] = (byte) 0x55;
        txDat[1] = (byte) address;// ).byteValue();
        txDat[2] = 0;
        txDat[3] = 0;
        txDat[4] = 4;
        txDat[5] = (byte) ((-(address + 4)) & 255);// .byteValue();
        txDat[6] = (byte) 0xfc;
        txDat[7] = (byte) 0x7f;
        txDat[8] = 0;
        txDat[9] = 4;
        txDat[10] = (byte) 0x81;
        txDat[11] = (byte) 0x55;
        txDat[12] = (byte) 0xaa;

        try {
            Message res = writeData(txDat);
            StringBuffer buffer = new StringBuffer();
            for (int i = res.getData().length - 1; i >= 0; i--)
                buffer.append((res.getData()[i] > 15 ? Integer.toHexString(res
                        .getData()[i]) : "0"
                        + Integer.toHexString(res.getData()[i]))
                        + "  ");
            return buffer.toString();
        } catch (NoDataException e) {
            log.error("Возникли ошибки при чтении контрольной суммы  "
                    + e.getMessage());
            ///sensor.setAvailable(false);
            return "Возникла ошибка";

        } catch (InterruptedException e) {
            log.error("Возникли ошибки при чтении контрольной суммы  "
                    + e.getMessage());
            return "Возникла ошибка";
        }


    }

    public synchronized void zeroizeSensor(int address) {
        byte[] txDat = new byte[8];
        txDat[0] = (byte) 0x55;
        txDat[1] = (byte) address;
        txDat[2] = 0;
        txDat[3] = (byte) 0x85;
        txDat[4] = 0;
        txDat[5] = (byte) ((-(address + 133)) & 255);
        txDat[6] = (byte) 0x55;
        txDat[7] = (byte) 0xaa;
        // SafUtil.printHexArray(txDat);
        writeToCom(txDat);
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
        }

    }

    public void spanSensor(int address) {
        byte[] txDat = new byte[8];
        txDat[0] = Integer.valueOf("85").byteValue();// 0x55
        txDat[1] = Integer.valueOf(address).byteValue();
        txDat[2] = 0;
        txDat[3] = Integer.valueOf("134").byteValue();// 0x86
        txDat[4] = 0;
        txDat[5] = Integer.valueOf((-(address + 134)) & 255).byteValue();
        txDat[6] = Integer.valueOf("85").byteValue();// 0x55
        txDat[7] = Integer.valueOf("170").byteValue();// 0xaa
        // SafUtil.printHexArray(txDat);
        writeToCom(txDat);
        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
        }

    }

    //55  04  00  00  04  f8  e5  07  00  27  ed  55  aa
    public void getTuneArray(int address, float lim) {
        byte[] txDat = new byte[13];
        txDat[0] = (byte) 0x55;
        txDat[1] = (byte) address;
        txDat[2] = 0;
        txDat[3] = 0;
        txDat[4] = 4;
        txDat[5] = (byte) ((-(address + 4)) & 255);
        txDat[6] = (byte) 0xe5;
        txDat[7] = (byte) 0x07;
        txDat[8] = (byte) 0x00;
        txDat[9] = (byte) 0x27;
        txDat[10] = (byte) 0xed;
        txDat[11] = (byte) 0x55;
        txDat[12] = (byte) 0xaa;
        // SafUtil.printHexArray(txDat);
        //writeToCom(txDat);
        try {
            Message mm = writeData(txDat);
            mm.getData();
            int[] mas = mm.getData();
//			SafUtil.printHexArray(mas, true);
            byte[] limMas = SafUtil.floatToByteArray(lim);
//			SafUtil.printHexArray(limMas, true);
            for (int i = 0; i < 4; i++)
                mas[i + 15] = limMas[i];
//			SafUtil.printHexArray(mas, true);
            int sum = 0;
            for (int i = 0; i < mas.length - 1; i++) {
                sum += mas[i];
            }
            sum = sum & 255;
            mas[mas.length - 1] = sum;
//			SafUtil.printHexArray(mas, true);


            Message txMess = new Message(address, 0xe5, 0x07, 0x40, 0x27, mas);

            writeMessage(txMess);

            txMess = new Message(address, 0x27, 0x00, 0xc1, 0x27, mas);
            writeMessage(txMess);
            txMess = new Message(address, 0x00, 0x00, 0xc1, 0x27, mas);

            writeMessage(txMess);


        } catch (NoDataException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        } catch (InterruptedException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        try {
            Thread.sleep(700);
        } catch (InterruptedException e) {
        }
        resetSensor(address);
    }


    public synchronized void writeToCom(byte[] TxDat) {

        // SafUtil.printHexArray(TxDat);
        try {
            log.info("пишем  " + SafUtil.printHexArray(TxDat, false));
            os.write(TxDat);
        } catch (IOException e) {
            System.err.println("При передаче сообщения произошла ошибка.");

        }
    }

    public boolean writeDefAddress(int address) {
        int[] addr = {2};
        try {
            writeVar(SensorConst.ADDRESS, address, addr);
        } catch (Exception e) {
            System.err.println("Датчик с адресом " + address + " не отвечает.");// e.printStackTrace();
            return false;
        }
        // записываем в датчик новый адрес
        resetSensor(address);
        return true;
    }

    public int readPressure(int address) throws InterruptedException,
            NoDataException {

        int[] data = readVar(SensorConst.PRS1DAT, address).getData();
        return SafUtil.ByteToInt(data[0], data[1]);
    }

    public int readTemperature(int address) throws InterruptedException,
            NoDataException {
        // ;
        int[] data = readVar(SensorConst.TMPDAT, address).getData();
        return SafUtil.ByteToInt(data[0], data[1]);
    }

    public boolean writeKa(int address, int KA) throws Exception {
        /*
         * Коэффициент усилинеия ацп лежит в пределах 0..7 и хранится в 3..5
		 * битах переменной с адресом SensorConst.coefAmplification
		 */
        if ((KA > 7) | (KA < 0)) // проверяем корректность входных данных
            throw new Exception("Неверный коэффициент усиления --" + KA);
        KA = KA << 3;
        // int[] data = { KA };
        writeVar(SensorConst.coefAmplification, address, new int[]{KA});
        resetSensor(address);// после записи ку датчик необходимо
        // перезагрузить
        return true;
    }

    public boolean writeNewAddress(int newAddress) {
        int[] addr = {newAddress};
        int ad = 2;
        boolean res = true;
        try {
            writeVar(SensorConst.ADDRESS, ad, addr);
        } catch (Exception e) {
            res = false;
            System.err.println("Датчик с адресом 0x02 не отвечает.");
        }
        // записываем в датчик новый адрес
        resetSensor(ad);
        return res;
    }

    public void writeTask(List<Sensor> data) throws NoDataException {
        // boolean res = true;
        Message m = new Message();
        m.setToAddress(0xff);
        m.setFromAddres(0x00);
        m.setCommand(0x87); // команда на измерения
        m.setAddressVar1(0x32);
        List<Integer> addresses = new LinkedList<Integer>();
        for (Sensor s : data)
            addresses.add(s.getAddress());

        m.setData(addresses);

        try {
            writeMessage(m);
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
        // try {
        // writeToCom(m);
        // // ждем
        //
        // } catch (IOException e) {
        // res = false;
        // e.printStackTrace();
        // }
        // return res;

    }

    public String enabledFixedCurrtntOutput(Sensor sensor) {
        int address = sensor.getAddress();
        byte[] txDat = new byte[14];

        txDat[0] = Integer.valueOf("85").byteValue();// 0x55
        txDat[1] = Integer.valueOf(address).byteValue();
        txDat[2] = 0;
        txDat[3] = 0;
        txDat[4] = 5;
        txDat[5] = Integer.valueOf((-(address + 5)) & 255).byteValue();
        txDat[6] = (byte) 0x0d;
        txDat[7] = 0;//
        txDat[8] = Integer.valueOf("96").byteValue();// 0x60
        txDat[9] = 1;
        txDat[10] = 8;
        txDat[11] = Integer.valueOf("138").byteValue();// 0x8a
        txDat[12] = Integer.valueOf("85").byteValue();// 0x55
        txDat[13] = Integer.valueOf("170").byteValue();// 0xaa

        try {
            Message res = writeData(txDat);

            return SafUtil.printHexArray(res.getData(), false);
        } catch (NoDataException e) {
            log
                    .error("Возникли ошибки при включении режима выдачи фиксированного тока  "
                            + e.getMessage());
            sensor.setAvailable(false);
            return "Возникла ошибка";

        } catch (InterruptedException e) {
            log
                    .error("Возникли ошибки при включении режима выдачи фиксированного тока  "
                            + e.getMessage());
            return "Возникла ошибка";

        }

    }

    // 55 04 00 00 05 f7 00 60 01 08 8a 55 aa
    // 55 04 00 00 05 f7 00 60 01 00 92 55 aa
    public String disabledFixedCurrtntOutput(Sensor sensor) {
        int address = sensor.getAddress();
        byte[] txDat = new byte[14];

        txDat[0] = Integer.valueOf("85").byteValue();// 0x55
        txDat[1] = Integer.valueOf(address).byteValue();
        txDat[2] = 0;
        txDat[3] = 0;
        txDat[4] = 5;
        txDat[5] = Integer.valueOf((-(address + 5)) & 255).byteValue();
        txDat[6] = (byte) 0x0D;//
        txDat[7] = 0;//
        txDat[8] = Integer.valueOf("96").byteValue();// 0x60
        txDat[9] = 1;
        txDat[10] = 0;
        txDat[11] = Integer.valueOf("146").byteValue();// 0x92
        txDat[12] = Integer.valueOf("85").byteValue();// 0x55
        txDat[13] = Integer.valueOf("170").byteValue();// 0xaa

        try {
            Message res = writeData(txDat);

            return SafUtil.printHexArray(res.getData(), false);
        } catch (NoDataException e) {
            log
                    .error("Возникли ошибки при выключении режима выдачи фиксированного тока  "
                            + e.getMessage());
            sensor.setAvailable(false);
            return "Возникла ошибка";

        } catch (InterruptedException e) {
            log
                    .error("Возникли ошибки при выключении режима выдачи фиксированного тока  "
                            + e.getMessage());
            return "Возникла ошибка";
        }

    }

}
