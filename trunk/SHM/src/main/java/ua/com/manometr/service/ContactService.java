package ua.com.manometr.service;

import ua.com.manometr.model.Contact;

import java.util.List;

public interface ContactService {

	public void addContact(Contact contact);

	public List<Contact> listContact();

	public void removeContact(Long id);

}
