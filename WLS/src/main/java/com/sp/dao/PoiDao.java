package com.sp.dao;

import com.sp.model.Poi;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
public class PoiDao extends BaseDao {

    public List<Poi> findForUserId(int userId, Boolean active) {
        Session session = getSession();

        Query query = session.createSQLQuery(
                "SELECT {p.*} " +
                " FROM poi {p} " +
                    " JOIN account a ON a.id = p.account_id" +
                    " JOIN map_user_account mua ON mua.account_id = p.account_id AND mua.user_id = ? AND a.is_deleted = 0 " +
                (active == null ? "" : " WHERE p.is_deleted=" + (active ? "0" : "1")))
                .addEntity("p", Poi.class);

        return query.setInteger(0, userId).list();
    }

    public List<Poi> findNearLocation(double lat, double lon, int accountId) {
        Session session = getSession();

        Query query = session.createSQLQuery(
                "SELECT {p.*} FROM `poi` {p}" +
                        " WHERE is_deleted=0 AND account_id=?" +
                        "  AND ((`p`.`lat` = ? AND `p`.`lon` = ? )" +
                        "   OR `lat_lon_to_distance`( `p`.`lat`, `p`.`lon`, ?, ?) <= `p`.`radius`)")
                .addEntity("p", Poi.class);

        return query.setInteger(0, accountId)
                .setDouble(1, lat)
                .setDouble(2, lon)
                .setDouble(3, lat)
                .setDouble(4, lon).list();
    }

    public List<Poi> findAll(Boolean active){
        Session session = getSession();

        Query query = session.createSQLQuery(
                "SELECT {p.*} " +
                " FROM poi {p} " +
                    " JOIN account a ON a.id = p.account_id AND a.is_deleted = 0" +
                        (active == null ? "" : " WHERE p.is_deleted=" + (active ? "0" : "1")))
                .addEntity("p", Poi.class);

        return query.list();

    }

    public List<Poi> findNotSelectedByAlertId(int alertId, boolean orderByDescription) {
        Session session = getSession();
        String queryString = "SELECT {p.*}" +
                        " FROM poi {p} " +
                        " where p.is_deleted = 0 and not exists" +
                        "(select null " +
                        " from map_alert_poi " +
                        " where poi_id = {p}.id and alert_id = ?)";
        if(orderByDescription)
        {
            queryString += " order by descr";
        }
        Query query = session.createSQLQuery(queryString).addEntity("p", Poi.class);
        return query.setInteger(0, alertId).list();
    }


    public List<Poi> findNotSelectedByUserIdAndAlertId(int userId, int alertId, boolean orderByDescription) {
        Session session = getSession();
        String queryString = "SELECT {p.*}" +
                        " FROM poi {p} " +
                        " join map_user_account mua ON mua.account_id = p.account_id" +
                        " where p.is_deleted = 0 AND mua.user_id = ? and not exists" +
                        "(select null " +
                        " from map_alert_poi" +
                        " where poi_id = {p}.id and alert_id = ?)";
        if(orderByDescription)
        {
            queryString += " order by descr";
        }
        Query query = session.createSQLQuery(queryString).addEntity("p", Poi.class);
        return query.setInteger(0, userId).setInteger(1, alertId).list();
    }

    public List<Poi> findByTimestamp(Date date, boolean active) {
        Criteria criteria = getSession().createCriteria(Poi.class)
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
