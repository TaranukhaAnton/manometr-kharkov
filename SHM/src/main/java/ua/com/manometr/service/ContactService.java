package ua.com.manometr.service;

import ua.com.manometr.model.Contact;
import ua.com.manometr.webbeans.User;

import java.util.List;

public interface ContactService {

    public Contact getContact(Long userId);

    public void addContact(Contact contact);

    public List<Contact> listContact();

    public void removeContact(Long id);

}
