package com.avrora.bdutils;


import com.avrora.GUI.ChannelState;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.beans.Expression;
import java.util.List;

public class DAO {


    public static void saveChannelState(ChannelState channelState) {
        Session session = null;
        Transaction transaction = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.saveOrUpdate(channelState);

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null)
                transaction.rollback();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
    }

    public static List<ChannelState> getChannelStates() {
        Session session = null;

        List<ChannelState> result = null;


        try {
            session = HibernateUtil.getSessionFactory().openSession();
            result = session.createCriteria(ChannelState.class).list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return result;

    }


    /*   public static void saveSettingsBean(SettingsBean settingsBean) {
        Session session = null;
        settingsBean.setId(1L);
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.saveOrUpdate(settingsBean);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
    }


   public static SettingsBean getSettingsBean() {
        Session session = null;

        SettingsBean res = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            res = (SettingsBean) session.get(SettingsBean.class, 1L);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return res;
    }


    public static void save(ChannelProps channelProps) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.saveOrUpdate(channelProps);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
    }


    public static void save(List<ChannelProps> channelProps) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            for (ChannelProps channelProp : channelProps) {
                session.update(channelProp);
            }

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
    }

    public static void save(AnalogSignals item) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(item);

            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
    }

    public static List<AnalogSignals> findAnalogSignalsByDate(Date date) {
        Session session = null;
        List<AnalogSignals> list = null;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Criteria crit = session.createCriteria(AnalogSignals.class);

            Date loDate = c.getTime();

            c.add(Calendar.DAY_OF_YEAR,1);
            Date hiDate = c.getTime();
            crit.add(Expression.between("time", loDate,hiDate));

            list = crit.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return list;

    }

    public static List<ChannelProps> findAllChannelProps() {

        Session session = null;
        List list = new LinkedList();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
//            System.out.println("getPersistentClass() = " + getPersistentClass().getSimpleName());
            list = session.createQuery("from org.anton.data.ChannelProps").list();

//			Criteria crit = session.createCriteria(getPersistentClass());
//			Example example = Example.create(exampleInstance);
//			for (String exclude : excludeProperty) {
//				example.excludeProperty(exclude);
//			}
//			crit.add(example);
//			list = crit.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return list;


        //	return findByCriteria();
    }


    public static List<ChannelProps> findChannelPropsByGroupName(Integer groupNum) {

        Session session = null;
        List<ChannelProps> list = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Criteria crit = session.createCriteria(ChannelProps.class);


            crit.add(Expression.eq("groupNum", groupNum));
            // Expression.
            list = crit.list();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return list;
    }

    public static List<LogMassage> filterLogMess(List<Integer> types) {


        Session session = null;
        List list = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Criteria crit = session.createCriteria(LogMassage.class);


            crit.add(Expression.in("type", types));

            list = crit.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return list;
    }

    public static List getAll(Class aClass) {

        Session session = null;
        List list = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            Criteria crit = session.createCriteria(aClass);

            // crit.add(Expression.like(paramName, example + "%"));

            list = crit.list();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return list;
    }*/


}
