package com.sp.dao;

import com.sp.model.Aoi;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Date: 01.12.2009
 * Time: 14:37:53
 * To change this template use File | Settings | File Templates.
 */
public class AoiDao extends BaseDao {
    private final static Logger LOGGER = Logger.getLogger(AoiDao.class);

    public List<Aoi> findForUserId(int userId, Boolean active) {
        Session session = getSession();

        Query query = session.createSQLQuery(
                "SELECT {aoi.*} " +
                        " FROM aoi {aoi} " +
                        " JOIN account a ON a.id = aoi.account_id" +
                        " JOIN map_user_account mua ON mua.account_id = aoi.account_id AND mua.user_id = ? AND a.is_deleted = 0 " +
                        (active == null ? "" : " WHERE aoi.is_deleted=" + (active ? "0" : "1")))
                .addEntity("aoi", Aoi.class);

        return query.setInteger(0, userId).list();
    }

    public List<Aoi> findAoiListByGeometryIdList(List<Integer> geometryIdList){
        Criteria criteria = getSession().createCriteria(Aoi.class);
        criteria.add(Restrictions.in("geometryId", geometryIdList));
        return (List<Aoi>)criteria.list();
    }

    public List<Aoi> findNotSelectedByAlertId(int alertId, boolean orderByDescription) {
        Session session = getSession();
        String queryString = "SELECT {a.*}" +
                        " FROM aoi {a} " +
                        " where a.is_deleted = 0 and not exists" +
                        "(select null " +
                        " from map_alert_aoi " +
                        " where aoi_id = {a}.id and alert_id = ?)";
        if(orderByDescription)
        {
            queryString += " order by descr";
        }
        Query query = session.createSQLQuery(queryString).addEntity("a", Aoi.class);
        return query.setInteger(0, alertId).list();
    }

    public List<Aoi> findNotSelectedByUserIdAndAlertId(int userId, int alertId, boolean orderByDescription) {
        Session session = getSession();
        String queryString =  " SELECT {a.*}" +
                " FROM aoi {a} " +
                " join map_user_account mua ON mua.account_id = a.account_id" +
                " where a.is_deleted = 0 AND mua.user_id = ? and not exists" +
                "(select null " +
                " from map_alert_aoi" +
                " where aoi_id = {a}.id and alert_id = ?)";
        if(orderByDescription)
        {
            queryString += " order by descr";
        }
        Query query = session.createSQLQuery(queryString).addEntity("a", Aoi.class);
        return query.setInteger(0, userId).setInteger(1, alertId).list();
    }

    public List<Aoi> findByTimestamp(Date date,boolean active) {
        Criteria criteria = getSession().createCriteria(Aoi.class)
                .createAlias("account", "a").createAlias("a.reseller", "r");
        criteria.add(Restrictions.eq("deleted", !active));
        criteria.add(Restrictions.or(Restrictions.gt("timestamp", date),
                Restrictions.or(Restrictions.gt("a.timestamp", date),
                        Restrictions.gt("r.timestamp", date)
                )
        )
        );

        return criteria.list();
    }
}
