package application.tests;

import ua.com.manometer.model.invoice.Invoice;
import ua.com.manometer.model.invoice.Shipment;
import ua.com.manometer.model.invoice.ShipmentMediator;
import application.hibernate.Factory;

import java.util.Date;

public class ThreadLocal1 {


    public static void main(String args[]) {

        // Each thread increments counter
        // Displays variable info
        // And sleeps for the random amount of time
        // Before displaying info again
        Runnable runner = new Runnable() {
            public void run() {
                for (int i = 0; i < 10; i++) {
                    Invoice inv = Factory.getInvoiceDAO().findById(10L);
                    Shipment shipping = new Shipment();
                    shipping.setInvoice(inv);
                    shipping.setDate(new Date());
                    shipping.setShipmentNum("123 456");
                    shipping.addShippingMediator(new ShipmentMediator(Factory.getInvoiceItemDAO().findById(8L), 1));
                    shipping.addShippingMediator(new ShipmentMediator(Factory.getInvoiceItemDAO().findById(9L), 4));
                    shipping.addShippingMediator(new ShipmentMediator(Factory.getInvoiceItemDAO().findById(10L), 7));
//        shipping.addShippingMediator(new ShipmentMediator(Factory.getInvoiceItemDAO().findById(6L),8));
                    inv.addShipment(shipping);
                    //Factory.getShipmentDAO().makePersistent(shipping);
                    Factory.getInvoiceDAO().makePersistent(inv);
                    System.out.println("#" + inv.getShipments().size());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }


            }

        };


        Runnable runner2 = new Runnable() {
            public void run() {
                for (int i = 0; i < 12; i++) {
                  //  Factory.getInvoiceDAO().flush();
                   // Factory.getInvoiceDAO().clear();

                    Invoice inv = Factory.getInvoiceDAO().findById(10L);
                    inv = Factory.getInvoiceDAO().findById(10L);
                    System.out.println("      *" + inv.getShipments().size());
                    try {
                        Thread.sleep(900);
                    } catch (InterruptedException e) {
                        e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                    }
                }


            }

        };

        Thread t2 = new Thread(runner2);
        t2.start();

        Thread t = new Thread(runner);
        t.start();


    }
}