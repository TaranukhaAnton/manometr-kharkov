package test;

import junit.framework.Assert;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.com.manometer.model.Customer;
import ua.com.manometer.model.invoice.Invoice;
import ua.com.manometer.model.test.Test1;
import ua.com.manometer.model.test.Test2;
import ua.com.manometer.service.CustomerService;
import ua.com.manometer.service.invoice.InvoiceService;
import ua.com.manometer.service.test.Test1Service;
import ua.com.manometer.service.test.Test2Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class Test {
    @Autowired
    Test1Service service1;


    @Autowired
    Test2Service service2;
    @Autowired
    InvoiceService invoiceService;

    @Autowired
    CustomerService customerService;


    @org.junit.Test
    public void testCreateData() {
        Test1 entity =  service1.getTest1(1l);
       // entity.setName("VVVVV");
        //entity.setAmount(new BigDecimal("5.456"));
        Set<Test2> set = new HashSet<Test2>();
        for (int i = 0; i < 3; i++) {
            final Test2 e = new Test2("" + i);
            service2.addTest2(e );
            set.add(e);
        }

        entity.setTest2Set(set);
        service1.addTest1(entity);
        Assert.assertEquals(1, 1);
    }


    @org.junit.Test
    public void testGetData() {

        final List<Test1> test1s = service1.listTest1();
        System.out.println("test1s.size() = " + test1s.size());
        for (Test1 test1 : test1s) {
            System.out.println("test1.getTest2Set().size() = " + test1.getTest2Set().size());

        }
        Assert.assertEquals(1, 1);
    }
    @org.junit.Test
    public void tesdd() {


        Assert.assertEquals(1, 1);
    }

    @org.junit.Test
    public void tesssdd() {
        final List<Customer> customers = customerService.listCustomer();
//          customerService.addCustomer(new Customer());
        Assert.assertEquals(1, 1);
    }

}
