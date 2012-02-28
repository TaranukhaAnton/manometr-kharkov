package ua.com.manometr.dao;

import ua.com.manometr.model.Contact;

import java.util.List;

public interface ContactDAO {

    public Contact getContact(Long id);

    public void addContact(Contact contact);

    public List<Contact> listContact();

    public void removeContact(Long id);

}