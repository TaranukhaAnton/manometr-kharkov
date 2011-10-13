package br.com.javacoder.contact.service;

import java.util.List;

import br.com.javacoder.contact.model.ContactBr;

public interface ContactServiceBr {

	public void addContact(ContactBr contactBr);

	public List<ContactBr> listContact();

	public void removeContact(Long id);

}
