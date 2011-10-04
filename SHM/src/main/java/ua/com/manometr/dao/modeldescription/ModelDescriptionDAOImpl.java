package ua.com.manometr.dao.modeldescription;
import ua.com.manometr.model.modeldescription.ModelDescription;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ModelDescriptionDAOImpl implements ModelDescriptionDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addModelDescription(ModelDescription modeldescription) {
        sessionFactory.getCurrentSession().save(modeldescription);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ModelDescription> listModelDescription() {
        return sessionFactory.getCurrentSession().createQuery("from ModelDescription").list();
    }

    @Override
    public void removeModelDescription(Long id) {
        ModelDescription modeldescription = (ModelDescription) sessionFactory.getCurrentSession().load(ModelDescription.class, id);
        if (modeldescription != null) {
            sessionFactory.getCurrentSession().delete(modeldescription);
        }
    }

}
