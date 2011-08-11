package ua.com.manometer.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import ua.com.manometer.model.User;

import java.util.List;

public class UserDao {
    private HibernateTemplate hibernateTemplate;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.hibernateTemplate = new HibernateTemplate(sessionFactory);
    }

    public Integer createUser(final User user) {
        return (Integer) hibernateTemplate.execute(new HibernateCallback<Object>() {
            public Object doInHibernate(Session session) {
                return session.save(user);
            }
        });
    }

    public void deleteUser(final User user) {
        hibernateTemplate.execute(new HibernateCallback<User>() {
            public User doInHibernate(Session session) {
                session.delete(user);
                return user;
            }
        });
    }

    public User getUserById(final int userId) {
        return hibernateTemplate.execute(new HibernateCallback<User>() {
            public User doInHibernate(Session session) {
                Query query = session.createQuery("from ua.com.manometer.model.User userEntity where userEntity.id=:userId");
                query.setParameter("userId", userId);
                List list = query.list();
                if (list != null && !list.isEmpty()) {
                    return (User) list.get(0);
                }
                return null;
            }
        });
    }


}
