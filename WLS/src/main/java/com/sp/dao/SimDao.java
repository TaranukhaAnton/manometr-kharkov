package com.sp.dao;

import com.sp.model.BaseSim;
import com.sp.model.Sim;
import com.sp.service.ResellerService;
import com.sp.service.SimService;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
public class SimDao extends BaseDao {
    public Sim findByNumber(String number) {

        Session session = getSession();
        return (Sim) session.createQuery("from com.sp.model.Sim  as obj where obj.number = ?").setString(0, number).uniqueResult();
    }

    public Sim findBySerialNum(String serialNum) {
        Session session = getSession();
        return (Sim) session.createQuery("from com.sp.model.Sim  as obj where obj.serialNum = ?").setString(0, serialNum).uniqueResult();
    }
   
    public List<Sim> findByUserId(int userId, Boolean active){
        Session session = getSession();
        Query query = session.createSQLQuery(
                "SELECT {s.*} " +
                " FROM sim {s} " +
                        "JOIN map_user_account mua ON mua.account_id = s.account_id AND mua.user_id = ?" +
                (active == null ? "" : " WHERE is_deleted=" + (active ? "0" : "1"))).addEntity("s", Sim.class);

        return query.setInteger(0, userId).list();
    }

    public List<Sim> findByFilterAndUserId(int userId, int filter, Boolean active){
        Session session = getSession();

        Date curDate = new Date();
        Calendar calendar = Calendar.getInstance();
        Query query;
        if (active == null){
            active = true;
        }

        switch(filter) {
            case SimService.FILTER_TYPE_CURRENT:
                  query = session.createSQLQuery(
                        "SELECT {s.*} " +
                        " FROM sim {s} " +
                                " JOIN map_user_account mua ON mua.account_id = s.account_id WHERE mua.user_id = ? " +
                                "and ? >= s.start_date and ? <= s.end_date and s.is_deleted = ?").addEntity("s", Sim.class);

                return query.setInteger(0,userId).setDate(1,curDate).setDate(2,curDate).setBoolean(3,!active).list();
            case SimService.FILTER_TYPE_EXPIRED_IN_6_LAST_MONTHS:
                calendar.add(Calendar.MONTH, -6);
                Date fromDate = calendar.getTime();
                query = session.createSQLQuery(
                        "SELECT {s.*} " +
                        " FROM sim {s} " +
                                " JOIN map_user_account mua ON mua.account_id = s.account_id WHERE mua.user_id = ? " +
                                "and s.end_date >= ? and s.end_date <= ? and s.is_deleted = ?").addEntity("s", Sim.class);
                 return query.setInteger(0,userId).setDate(1,fromDate).setDate(2,curDate).setBoolean(3,!active).list();
            case SimService.FILTER_TYPE_WILL_EXPIRE_IN_3_NEXT_MONTHS:
                calendar.add(Calendar.MONTH, 3);
                Date toDate = calendar.getTime();

                query = session.createSQLQuery(
                        "SELECT {s.*} " +
                        " FROM sim {s} " +
                                " JOIN map_user_account mua ON mua.account_id = s.account_id WHERE mua.user_id = ? " +
                                "and s.end_date >= ? and s.end_date <= ? and s.is_deleted = ?").addEntity("s", Sim.class);
                 return query.setInteger(0,userId).setDate(1,curDate).setDate(2,toDate).setBoolean(3,!active).list();

            default:
                throw new RuntimeException("Unknown filter=" + filter);
        }
    }

    public List<Sim> findByResellerAndFilter(int resellerId, int filter, Boolean active) {
        Session session = getSession();

        Date curDate = new Date();
        Calendar calendar = Calendar.getInstance();
        Query query = null;
        switch(filter) {
            case SimService.FILTER_TYPE_CURRENT:
                query = session.createQuery("from com.sp.model.Sim as obj where ? >= obj.startDate and ? <= obj.endDate and obj.deleted = ? and obj.resellerId"
                        + (resellerId != -1 ?  " = ? " : "!=" + ResellerService.TESTING_PARTNER_ID))
                        .setDate(0, curDate).setDate(1, curDate).setBoolean(2, !active);
                break;
            case SimService.FILTER_TYPE_EXPIRED_IN_6_LAST_MONTHS:
                calendar.add(Calendar.MONTH, -6);
                Date fromDate = calendar.getTime();

                query = session.createQuery("from com.sp.model.Sim as obj where obj.endDate >= ? and obj.endDate <= ? and obj.deleted = ? and obj.resellerId"
                        + (resellerId != -1 ?  " = ? " : "!=" + ResellerService.TESTING_PARTNER_ID))
                        .setDate(0, fromDate).setDate(1, curDate).setBoolean(2, !active);
                break;
            case SimService.FILTER_TYPE_WILL_EXPIRE_IN_3_NEXT_MONTHS:
                calendar.add(Calendar.MONTH, 3);
                Date toDate = calendar.getTime();

                query = session.createQuery("from com.sp.model.Sim as obj where obj.endDate >= ? and obj.endDate <= ? and obj.deleted = ? and obj.resellerId"
                        + (resellerId != -1 ?  " = ? " : "!=" + ResellerService.TESTING_PARTNER_ID))
                        .setDate(0, curDate).setDate(1, toDate).setBoolean(2, !active);
                break;
            default:
                throw new RuntimeException("Unknown filter=" + filter);
        }

        if (resellerId != -1) {
            query.setInteger(3, resellerId);
        }

        return query.list();
    }

    public List<BaseSim> findAllUnassigned() {
        Session session = getSession();
        Query query = session.createSQLQuery(
                "SELECT {s.*} " +
                " FROM sim {s} " +
                " WHERE NOT EXISTS (SELECT NULL FROM imei WHERE assigned_sim_id = s.id" +
                " UNION select null from handheld where sim_id = s.id)")
                .addEntity("s", Sim.class);

        return query.list();
    }

    public List<BaseSim> findAllUnassignedByUserId(int userId){
        Session session = getSession();
        Query query = session.createSQLQuery(
                "SELECT {s.*} " +
                " FROM sim {s} " +
                " JOIN map_user_account mua ON mua.account_id = s.account_id WHERE mua.user_id = ? " +
                " AND NOT EXISTS (SELECT NULL FROM imei WHERE assigned_sim_id = s.id"+
                " UNION select null from handheld where sim_id = s.id)")
                .addEntity("s", Sim.class);

        return query.setInteger(0,userId).list();
    }

    public List<Sim> findByTimestamp(Date date) {
        Criteria criteria = getSession().createCriteria(Sim.class);
        criteria.add(Restrictions.gt("timestamp", date));

        return criteria.list();
    }

    public List<Sim> findByResellerId(int resellerIdFilter, Boolean active) {
        Criteria criteria = getSession().createCriteria(Sim.class);
        if (resellerIdFilter == -1) {
            if (ResellerService.TESTING_PARTNER_ID != 0) {
                criteria.add(Restrictions.ne("resellerId", ResellerService.TESTING_PARTNER_ID));
            }
        } else {
            criteria.add(Restrictions.eq("resellerId", resellerIdFilter));
        }
        criteria.add(Restrictions.eq("deleted", !active));

        return criteria.list();
    }
}
