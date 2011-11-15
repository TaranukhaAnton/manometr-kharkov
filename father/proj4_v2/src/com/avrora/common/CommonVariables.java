package com.avrora.common;

import java.io.IOException;
import java.util.Properties;
import java.util.prefs.Preferences;

public class CommonVariables {

    private long NEXT_TASK_TIME;

    private String CommPortNum, baudRate;
    private String DATABITS, STOPBITS, PARITY;

    private Properties props = new Properties();
    public static final CommonVariables INSTANSE = new CommonVariables();

    private CommonVariables() {
        try {
            props.load(getClass().getResourceAsStream("/AVRORA.properties"));
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        //  count[0] = node.getInt("c0", 0);
        // count[1] = node.getInt("c1", 0);
        // count[2] = node.getInt("c2", 0);
        //  Format = node.getInt("Format", 0);
        //CompileReports = node.getBoolean("CompileReports", true);
        NEXT_TASK_TIME = System.currentTimeMillis();
        CommPortNum = props.getProperty("CommPortNum", "COM1");
        System.out.println("CommPortNum = " + CommPortNum);
        //Driver = props.getProperty("Driver", "com.mysql.jdbc.Driver");
        // Host = props.getProperty("Host", "localhost");
        // Login = props.getProperty("Login", "root");
        //Pass = props.getProperty("Pass", "2405");
        //Alias = props.getProperty("Alias", "Avrora");
        baudRate = props.getProperty("baudRate", "9600");
        DATABITS = props.getProperty("DATABITS", "8");
        STOPBITS = props.getProperty("STOPBITS", "2");
        PARITY = props.getProperty("PARITY", "0");

    }


    public long getNEXT_TASK_TIME() {
        return NEXT_TASK_TIME;
    }

    public void setNEXT_TASK_TIME(long next_task_time) {
        NEXT_TASK_TIME = next_task_time;
    }

    public String getCommPortNum() {
        return CommPortNum;
    }


    public String getBaudRate() {
        return baudRate;
    }


}
