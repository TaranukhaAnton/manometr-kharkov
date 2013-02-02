package ua.com.manometer.dao;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.manometer.model.User;

import java.io.Serializable;
import java.util.List;

@Repository
public class BaseDAOImpl implements BaseDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void saveOrUpdate(Object object) {
        sessionFactory.getCurrentSession().saveOrUpdate(object);
    }


    @Override
    public void removeById(Serializable id, Class aClass) {
        Session currentSession = sessionFactory.getCurrentSession();
        Object o = currentSession.load(aClass, id);
        if (o != null) {
            sessionFactory.getCurrentSession().delete(o);
        }
    }

    @Override
    public Object getById(Serializable id, Class aClass) {
        return sessionFactory.getCurrentSession().get(aClass, id);
    }


}
