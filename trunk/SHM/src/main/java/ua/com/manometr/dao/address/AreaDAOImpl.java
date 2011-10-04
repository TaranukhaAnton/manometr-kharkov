package ua.com.manometr.dao.address;
import ua.com.manometr.model.address.Area;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AreaDAOImpl implements AreaDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void addArea(Area area) {
        sessionFactory.getCurrentSession().save(area);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Area> listArea() {
        return sessionFactory.getCurrentSession().createQuery("from Area").list();
    }

    @Override
    public void removeArea(Long id) {
        Area area = (Area) sessionFactory.getCurrentSession().load(Area.class, id);
        if (area != null) {
            sessionFactory.getCurrentSession().delete(area);
        }
    }

}
