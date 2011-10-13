package br.com.javacoder.contact.dao;

import java.util.List;

import br.com.javacoder.contact.model.ContactBr;

public interface ContactBrDAO {

	public void addContactBr(ContactBr contactBr);

	public List<ContactBr> listContactBr();

	public void removeContactBr(Long id);

}
