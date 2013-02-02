package ua.com.manometer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.manometer.dao.ContactDAO;
import ua.com.manometer.dao.ProfessionDAO;
import ua.com.manometer.model.Contact;
import ua.com.manometer.model.Profession;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactDAO contactDAO;
    @Autowired
    ProfessionDAO professionDAO;


    @Override
    @Transactional
    public Contact getContact(Long userId) {
        return contactDAO.getContact(userId);
    }

    @Override
    @Transactional
    public void addContact(Contact contact) {
        if (contact.getProfession() != null) {
            final Long id = contact.getProfession().getId();
            final Profession profession = professionDAO.getProfession(id);
            contact.setProfession(profession);
        }
        contactDAO.addContact(contact);
    }

    @Override
    @Transactional
    public List<Contact> listContact() {
        return contactDAO.listContact();
    }

    @Override
    @Transactional
    public void removeContact(Long id) {
        contactDAO.removeContact(id);
    }

}
