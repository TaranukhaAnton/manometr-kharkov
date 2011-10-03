package ua.com.manometr.dao;

import br.com.javacoder.contact.model.Contact;

import java.util.List;

public interface ProfessionDAO {

	public void addContact(Contact contact);

	public List<Contact> listContact();

	public void removeContact(Long id);

}
