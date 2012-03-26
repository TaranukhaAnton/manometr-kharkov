package ua.com.manometer.dao;

import ua.com.manometer.model.Contact;

import java.util.List;

public interface ContactDAO {

    public Contact getContact(Long id);

    public void addContact(Contact contact);

    public List<Contact> listContact();

    public void removeContact(Long id);

}