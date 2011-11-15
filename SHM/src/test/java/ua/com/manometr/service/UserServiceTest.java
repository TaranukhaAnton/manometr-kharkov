package ua.com.manometr.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import ua.com.manometr.model.invoice.InvoiceFilter;
import ua.com.manometr.service.invoice.InvoiceFilterService;
import ua.com.manometr.webbeans.User;

@ContextConfiguration(locations = {"classpath:spring-servlet.xml"})
public class UserServiceTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    UserService userService;
    @Autowired
    InvoiceFilterService invoiceFilterService;

    @Test
    public void testGetUser() throws Exception {
//        User user = userService.getUser(1L);
//        Assert.isNotNull(user);
    }
    @Test
    public void testAddUser() throws Exception {
        userService.addUser(new User());
        userService.addUser(new User());
        userService.addUser(new User());

    }

    @Test
    public void testListUser() throws Exception {
        InvoiceFilter invoiceFilter = new InvoiceFilter();
        invoiceFilter.setF0(2);
        invoiceFilter.setF2(4);
        invoiceFilter.setCurrencyFilter(new Long[]{2L, 3L, 4L});
        invoiceFilterService.addInvoiceFilter(invoiceFilter);
    }

    @Test
    public void testRemoveUser() throws Exception {
//        User user = userService.listUser().get(0);
//        InvoiceFilter invoiceFilter = invoiceFilterService.listInvoiceFilter().get(0);
//        user.setInvoiceFilter(invoiceFilter);
        userService.removeUser(7L);


    }
}
