package ua.com.manometer.dao;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.com.manometer.model.Supplier;

import java.util.List;

@Repository
public class SupplierDAOImpl implements SupplierDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addSupplier(Supplier supplier) {
        sessionFactory.getCurrentSession().save(supplier);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Supplier> listSupplier() {
        return sessionFactory.getCurrentSession().createQuery("from Supplier").list();
    }

    @Override
    public void removeSupplier(Integer id) {
        Supplier supplier = (Supplier) sessionFactory.getCurrentSession().load(Supplier.class, id);
        if (supplier != null) {
            sessionFactory.getCurrentSession().delete(supplier);
        }
    }

    @Override
    public Supplier getDefSupplier() {
       return (Supplier)sessionFactory.getCurrentSession().createCriteria(Supplier.class).add(Restrictions.eq("defddd", "y")).uniqueResult();
    }

}