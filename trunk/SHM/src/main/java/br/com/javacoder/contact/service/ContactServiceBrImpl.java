package br.com.javacoder.contact.service;

import java.util.List;

import br.com.javacoder.contact.dao.ContactBrDAO;
import br.com.javacoder.contact.model.ContactBr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContactServiceBrImpl implements ContactServiceBr {

	@Autowired
	private ContactBrDAO contactBrDAO;

	@Override
	@Transactional
	public void addContact(ContactBr contactBr) {
		contactBrDAO.addContactBr(contactBr);
	}

	@Override
	@Transactional
	public List<ContactBr> listContact() {
		return contactBrDAO.listContactBr();
	}

	@Override
	@Transactional
	public void removeContact(Long id) {
		contactBrDAO.removeContactBr(id);
	}

}
