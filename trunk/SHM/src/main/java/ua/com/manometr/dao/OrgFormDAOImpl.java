package ua.com.manometr.dao;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.manometr.model.OrgForm;

import java.util.List;

@Repository
public class OrgFormDAOImpl implements OrgFormDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addOrgForm(OrgForm orgForm) {
        sessionFactory.getCurrentSession().save(orgForm);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<OrgForm> listOrgForm() {
        return sessionFactory.getCurrentSession().createQuery("from OrgForm").list();
    }

    @Override
    public void removeOrgForm(Long id) {
        OrgForm orgForm = (OrgForm) sessionFactory.getCurrentSession().load(OrgForm.class, id);
        if (orgForm != null) {
            sessionFactory.getCurrentSession().delete(orgForm);
        }
    }

}