package ua.com.manometer.service;

import ua.com.manometer.model.Contact;

import java.util.List;

public interface ContactService {

    public Contact getContact(Integer userId);

    public void addContact(Contact contact);

    public List<Contact> listContact();

    public void removeContact(Integer id);

}
