package safir;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;

import java.util.Enumeration;

/**
 * Created with IntelliJ IDEA.
 * User: anton
 * Date: 08.09.14
 * Time: 22:55
 * To change this template use File | Settings | File Templates.
 */
public class CommTest {

    public static void main(String[] args) {
        Enumeration portIdentifiers = CommPortIdentifier.getPortIdentifiers();
        while (portIdentifiers.hasMoreElements()) {
            CommPortIdentifier o = (CommPortIdentifier) portIdentifiers.nextElement();
            System.out.println(o.getName());
        }

//        try {
//            CommPortIdentifier com2 = CommPortIdentifier.getPortIdentifier("COM2");
//        } catch (NoSuchPortException e) {
//            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
//        }

    }
}
