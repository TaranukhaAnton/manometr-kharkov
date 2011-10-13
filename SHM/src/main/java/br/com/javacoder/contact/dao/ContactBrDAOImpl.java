package br.com.javacoder.contact.dao;

import java.util.List;

import br.com.javacoder.contact.model.ContactBr;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ContactBrDAOImpl implements ContactBrDAO {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public void addContactBr(ContactBr contactBr) {
		sessionFactory.getCurrentSession().save(contactBr);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ContactBr> listContactBr() {
		return sessionFactory.getCurrentSession().createQuery("from ContactBr")
				.list();
	}

	@Override
	public void removeContactBr(Long id) {
		ContactBr contactBr = (ContactBr) sessionFactory.getCurrentSession().load(
				ContactBr.class, id);
		if (contactBr != null) {
			sessionFactory.getCurrentSession().delete(contactBr);
		}
	}

}
