package org.krams.tutorial.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.krams.tutorial.domain.BaseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BaseDao {
    @Autowired
    SessionFactory sessionFactory;


    public BaseModel findById(Class clazz, int id) {
        Session session = sessionFactory.getCurrentSession();
        return (BaseModel) session.get(clazz, id);
    }
}
