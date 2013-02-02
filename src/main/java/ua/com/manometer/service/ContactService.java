package ua.com.manometer.service;

import ua.com.manometer.model.Contact;

import java.util.List;

public interface ContactService {

    public Contact getContact(Long userId);

    public void addContact(Contact contact);

    public List<Contact> listContact();

    public void removeContact(Long id);

}
