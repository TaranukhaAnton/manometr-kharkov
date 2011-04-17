package application.hibernate.generic;

import application.hibernate.HibernateSessionFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.*;

import java.lang.reflect.ParameterizedType;
import java.util.*;


public class GenericHibernateDAO<T>
        implements GenericDAO<T> {

    private Class<T> persistentClass;
    //  private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public GenericHibernateDAO() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        // sessionFactory = HibernateUtil.getSessionFactory();
    }

    //   public void setSessionFactory(SessionFactory sf) {
    //   this.sessionFactory = sf;
    // }

    protected Session getSession() {
//        if (sessionFactory == null)
//            throw new IllegalStateException(
//                    "sessionFactory has not been set on DAO before usage");
//       // return sessionFactory.openSession();
//        return sessionFactory.getCurrentSession();
        return HibernateSessionFactory.getSession();
    }

    public Class<T> getPersistentClass() {
        return persistentClass;
    }

//    public T findById(Long id) {
//        return findById(id, false);
//    }

    @SuppressWarnings("unchecked")
    public T findById(Long id) {
        //	System.out.println("findById");
        T entity = null;
        // if (lock)
        // entity = (T) getSession().load(getPersistentClass(), id,
        // LockMode.UPGRADE);
        // else

        Session session = null;

        try {
            session = getSession();
            // session.beginTransaction();
//            entity = (T) session.load(getPersistentClass(), id);


            //    System.out.println("findById  ");
//           entity =(T) session.createCriteria(getPersistentClass()).add( Restrictions.eq("id",id)).uniqueResult();


            entity = (T) session.get(getPersistentClass(), id);

//            System.out.println(session.getFlushMode());
            //session.getTransaction().commit();
            // getSession().
            // entity.toString(); //
        } catch (Exception e) {
            e.printStackTrace();
        }

//        finally {
//            if (session != null && session.isOpen()) {
//                session.close();
//            }
//        }
        return entity;
    }

    public List<T> findAll() {

//        Session session = null;
//        List<T> list = null;
//        try {
//            session = getSession();
////            System.out.println("getPersistentClass() = " + getPersistentClass().getSimpleName());
//            String className = persistentClass.getSimpleName();
//            //  session.beginTransaction();
//            list = session.createQuery("from  " + className).list();
//
////			Criteria crit = session.createCriteria(getPersistentClass());
////			Example example = Example.create(exampleInstance);
////			for (String exclude : excludeProperty) {
////				example.excludeProperty(exclude);
////			}
////			crit.add(example);
////			list = crit.list();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
////            if (session != null && session.isOpen()) {
////                session.close();
////            }
////        }
////
//     //  return list;
        return  getSession().createCriteria(getPersistentClass()).list();

    }

//    @SuppressWarnings("unchecked")
//    public List<T> findByExampleQ(List<SimpleExpression> excludeProperty) {
//
//        Session session = null;
//        List<T> list = null;
//        try {
//            session = getSession();
//
//
//            Criteria crit = session.createCriteria(getPersistentClass());
//            //session.createFilter()
//
//
//            for (SimpleExpression exclude : excludeProperty) {
////				example.excludeProperty(exclude);
//                crit.add(exclude);
//
//            }
//            //   example.excludeNone();
//
//
//            // crit.
//            list = crit.list();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
////        finally {
////            if (session != null && session.isOpen()) {
////                session.close();
////            }
////        }
//        return list;
//    }


    @SuppressWarnings("unchecked")
    public List<T> findByExample(T exampleInstance, List<String> excludeProperty) {

        Session session = null;
        List<T> list = null;
        try {
            session = getSession();
            // session.beginTransaction();
            Criteria crit = session.createCriteria(getPersistentClass());
            Example example = Example.create(exampleInstance);


            for (String exclude : excludeProperty) {
                example.excludeProperty(exclude);

            }
            //   example.excludeNone();

            crit.add(example);
            list = crit.list();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        finally {
//            if (session != null && session.isOpen()) {
//                session.close();
//            }
//        }
        return list;
    }


    public T findUniqueByExample(T exampleInstance) {

        Session session = null;
        T object = null;
        try {
            session = getSession();
            Criteria crit = session.createCriteria(getPersistentClass());
            Example example = Example.create(exampleInstance);

            crit.add(example);
            object = (T) crit.uniqueResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        finally {
//            if (session != null && session.isOpen()) {
//                session.close();
//            }
//        }
        return object;
    }


   synchronized public T makePersistent(T entity) {

        //System.out.println("makePersistent");
        Session session = null;
        // Transaction tr = null;
        try {
            session = getSession();

            Transaction tx = session.getTransaction();
         //   Boolean tr = tx.isActive();
          //  System.out.println(tx.isActive() +" "+tx.wasCommitted());
           // tx.
         //   if (!tr)
                tx.begin();

            session.saveOrUpdate(entity);

          //  if (!tr)
           // {
           //     System.out.println("комитим");
            tx.commit();
        //}

            // tr = session.beginTransaction();


            //   tr.commit();
        } catch (Exception e) {
//            tr.rollback();
            e.printStackTrace();
        }

        return entity;
    }

//    public T create(T entity) {
//        getSession().save(entity);
//        return entity;
//    }

//    public T update(T entity) {
//        getSession().update(entity);
//        return entity;
//    }


    public void delete(T entity) {
        Session session = null;
        //  Transaction tr = null;
        try {
            session = getSession();


            Transaction tx = session.getTransaction();
            Boolean tr = tx.isActive();
            if (tr)
                System.out.println("!!!!!!!!!!!!!!!!!!!Осталась транзакция не закомиченая!!!!");

                 tx.begin();

                session.delete(entity);
                  
           // session.saveOrUpdate(entity);

         //   if (!tr)
                tx.commit();


        } catch (Exception e) {
            // tx.rollback();
            e.printStackTrace();
        }
//        finally {
//            if (session != null && session.isOpen()) {
//                session.close();
//            }
//        }
    }

    public void flush() {
        getSession().flush();
    }

    public void clear() {
        getSession().clear();
    }

    public void evict() {
        HibernateSessionFactory.getSessionFactory().evict(getPersistentClass());
//        getSession().evict(ent);
    }

    /**
     * Use this inside subclasses as a convenience method.
     */
    @SuppressWarnings("unchecked")
    protected List<T> findByCriteria(Criterion... criterion) {
        //	System.out.println("findByCriteria");
        Session session = null;
        List<T> list = null;
        try {

            session = getSession();
            Criteria crit = session.createCriteria(getPersistentClass());
            for (Criterion c : criterion) {
                crit.add(c);
            }
            list = crit.list();


        } catch (Exception e) {
            e.printStackTrace();
        }
//        finally {
//            if (session != null && session.isOpen()) {
//                session.close();
//            }
//        }

        return list;
    }


    public List<T> findByParameterExample(String paramName, String example) {

        Session session = null;
        List<T> list = null;
        try {
            session = getSession();
            Criteria crit = session.createCriteria(getPersistentClass());

            crit.add(Expression.like(paramName, example + "%"));

            list = crit.list();
        } catch (Exception e) {
            e.printStackTrace();
        }

//        finally {
//            if (session != null && session.isOpen()) {
//                session.close();
//            }
//        }
        return list;
    }

/*	public T findByParameter(String paramName, Object parameter) {

        Session session = null;
        T entity = null;
        try {
            session = getSession();

            entity = (T) session.createCriteria(getPersistentClass()).add(
                    Expression.eq(paramName, parameter)).uniqueResult();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return entity;
    }*/


    public List<T> findListByParameter(String paramName, Object parameter) {

        Session session = null;
        List<T> list = null;
        try {
            session = getSession();

            list = session.createCriteria(getPersistentClass()).add(
                    Expression.eq(paramName, parameter)).list();

        } catch (Exception e) {
            e.printStackTrace();
        }
//        finally {
//            if (session != null && session.isOpen()) {
//                session.close();
//            }
//        }
        return list;
    }


    @SuppressWarnings("unchecked")
    public List<T> findByExample(Map map) {

        Session session = null;
        List<T> list = null;
        try {
            session = getSession();
            String query = "from " + persistentClass.getSimpleName();
            if (!map.isEmpty()) {
                query += " where ";
                Set<String> set = map.keySet();
                Iterator<String> it = set.iterator();
                query += it.next() + " =?";
                while (it.hasNext()) {
                    query += " and " + it.next() + " =?";
                }
                Query q = session.createQuery(query);
                Collection values = map.values();
                int i = 0;
                for (Iterator iterator = values.iterator(); iterator.hasNext();) {
                    Object o = iterator.next();
                    q.setParameter(i++, o);
                }

                list = q.list();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        finally {
//            if (session != null && session.isOpen()) {
//                session.close();
//            }
//        }
        return list;
    }

    @SuppressWarnings("unchecked")
    public List<T> findByQuery(Map<String, Object> eq,
                               Map<String, Object> larger,
                               Map<String, Object> less,
                               Map<String, List> inList
    ) {

        Session session = null;
        List<T> result = new LinkedList<T>();


        try {
            session = getSession();
            String query = "from " + persistentClass.getSimpleName();
            List<String> ql = new LinkedList<String>();


            for (String s : eq.keySet())
                ql.add(s + " =?");
            for (String s : larger.keySet())
                ql.add(s + " >= ?");
            for (String s : less.keySet())
                ql.add(s + " <= ?");
            // if (inList)
            for (String s : inList.keySet()) {
                String tmp = "";


                List l = inList.get(s);
                for (int i = 0; i < l.size(); i++) {
//                    Object o =  l.get(i);
//                      tmp+="'"+o+"'";
                    tmp += "?";
                    if (i < (l.size() - 1)) tmp += ", ";
                }

                ql.add(s + " in (" + tmp + ")");

            }

            if (!ql.isEmpty())
                query += " where ";

            for (int i = 0; i < ql.size(); i++) {
                String s = ql.get(i);
                query += s;
                if (i < (ql.size() - 1)) query += " and ";
            }


          //  System.out.println("query = " + query);
            Query q = session.createQuery(query);
            int i = 0;
            for (Object o : eq.values())
                q.setParameter(i++, o);
            for (Object o : larger.values())
                q.setParameter(i++, o);
            for (Object o : less.values())
                q.setParameter(i++, o);

            for (List l : inList.values())
                for (int k = 0; k < l.size(); k++)
                    q.setParameter(i++, l.get(k));


//            for (Object o : inList.values())
//                q.setParameter(i++, o);

            result = q.list();

        } catch (Exception e) {
            e.printStackTrace();
        }

//        finally {
//            if (session != null && session.isOpen()) {
//                session.close();
//            }
//        }
        return result;
    }

}
