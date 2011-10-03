package br.com.javacoder.contact.service;

import br.com.javacoder.contact.model.Contact;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;


@ContextConfiguration(locations = {"classpath:spring-servlet.xml"})
public class ContactServiceImplTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private ContactService contactService;

    @Test
    public void testAddContact() throws Exception {
        contactService.addContact(new Contact());
    }

    @Test
    public void testListContact() throws Exception {

    }

    @Test
    public void testRemoveContact() throws Exception {

    }
}
