package br.com.javacoder.contact.service;

import br.com.javacoder.contact.model.ContactBr;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;


@ContextConfiguration(locations = {"classpath:spring-servlet.xml"})
public class ContactServiceTest extends AbstractJUnit4SpringContextTests {

    @Autowired
    private ContactServiceBr contactServiceBr;

    @Test
    public void testAddContact() throws Exception {
        contactServiceBr.addContact(new ContactBr());
    }

    @Test
    public void testListContact() throws Exception {

    }

    @Test
    public void testRemoveContact() throws Exception {

    }
}
