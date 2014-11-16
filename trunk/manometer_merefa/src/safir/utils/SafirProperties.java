package safir.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * Created with IntelliJ IDEA.
 * User: anton
 * Date: 12.09.14
 * Time: 22:32
 * To change this template use File | Settings | File Templates.
 */
public class SafirProperties {
    static Properties properties = new Properties();

    static {
        try {
            FileInputStream input = new FileInputStream("config.properties");
            properties.load(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    public static String getCommPortName() {
        return properties.getProperty("comPort", "COM3");
    }


    public static String getUrl() {
        return properties.getProperty("url", "jdbc:mysql://192.168.0.50/safir");
    }

    public static String getUser() {
        return properties.getProperty("user", "root");
    }

    public static String getPassword() {
        return properties.getProperty("password", "2405");
    }

    public static String getComputerId() {
        return properties.getProperty("id", "0");
    }
}
