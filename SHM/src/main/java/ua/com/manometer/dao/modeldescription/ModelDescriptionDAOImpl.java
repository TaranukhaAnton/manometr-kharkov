package ua.com.manometer.dao.modeldescription;

import org.hibernate.criterion.Restrictions;
import ua.com.manometer.model.modeldescription.ModelDescription;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ModelDescriptionDAOImpl implements ModelDescriptionDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void updateDescription(ModelDescription modeldescription) {
        sessionFactory.getCurrentSession().update(modeldescription);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<ModelDescription> listModelDescription() {
        return sessionFactory.getCurrentSession().createQuery("from ModelDescription").list();
    }

    @Override
    public void removeModelDescription(Integer id) {
        ModelDescription modeldescription = (ModelDescription) sessionFactory.getCurrentSession().load(ModelDescription.class, id);
        if (modeldescription != null) {
            sessionFactory.getCurrentSession().delete(modeldescription);
        }
    }

    @Override
    public List<ModelDescription> findListByIds(List<Integer> modelIds) {
        return sessionFactory.getCurrentSession().createCriteria(ModelDescription.class).add(Restrictions.in("id", modelIds)).list();
    }


}
