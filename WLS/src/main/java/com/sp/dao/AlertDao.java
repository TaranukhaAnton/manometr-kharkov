package com.sp.dao;

import com.sp.model.Alert;
import com.sp.model.BaseMapAlert;
import com.sp.model.MapAlertAoi;
import com.sp.model.MapAlertPoi;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Sanya
 * Date: 27.05.2009
 * Time: 15:59:57
 * To change this template use File | Settings | File Templates.
 */
public class AlertDao extends BaseDao {
    private final static Logger LOGGER = Logger.getLogger(AlertDao.class);

    public List<Alert> findByUserId(int userId) {
        Session session = getSession();
        return (List<Alert>) session.createQuery("from com.sp.model.Alert as obj where obj.securityUser.id = ?").setInteger(0, userId).list();
    }

    public List<Integer> findNotTriggeredByUserId(int userId){
        Session session = getSession();
        return (List<Integer>) session.createSQLQuery("select a.id from alert a left join schedule_mail_alert sa on sa.alert_id = a.id where sa.id is null and a.user_id = ?").setInteger(0, userId).list();
    }

     public List<Integer> findNotTriggeredAll(){
        Session session = getSession();
        return (List<Integer>) session.createSQLQuery("select a.id from alert a left join schedule_mail_alert sa on sa.alert_id = a.id where sa.id is null").list();
    }

    public void deleteMapByAlertId(int alertId) {
        Session session = getSession();
        Query query = session.createSQLQuery("delete from map_alert_poi where alert_id = ?");
        query.setInteger(0, alertId).executeUpdate();
    }

    public void deleteAoiMapByAlertId(int alertId) {
        Session session = getSession();
        Query query = session.createSQLQuery("delete from map_alert_aoi where alert_id = ?");
        query.setInteger(0, alertId).executeUpdate();
    }

    public List<MapAlertPoi> findSelectedByAlertId(int alertId) {
        Session session = getSession();
        Query query = session.createSQLQuery(
                "SELECT {map.*}" +
                        " FROM map_alert_poi {map} " +
                        " join poi p on p.id = map.poi_id" +
                        " where p.is_deleted = 0 and map.alert_id = ?")
                .addEntity("map", MapAlertPoi.class);
        return query.setInteger(0, alertId).list();
    }
    
    public List<MapAlertAoi> findSelectedAoiByAlertId(int alertId) {
        Session session = getSession();
        Query query = session.createSQLQuery(
                "SELECT {map.*}" +
                        " FROM map_alert_aoi {map} " +
                        " join aoi a on a.id = map.aoi_id" +
                        " where a.is_deleted = 0 and map.alert_id = ?")
                .addEntity("map", MapAlertAoi.class);
        return query.setInteger(0, alertId).list();
    }

    public List<Integer> findNotTriggeredByUserAccounts(int userId) {
        String sql = "SELECT DISTINCT a.id " +
                " FROM alert a" +
                "  JOIN map_user_account mua ON mua.user_id = a.user_id " +
                "  JOIN map_user_account own_mua ON own_mua.account_id = mua.account_id" +
                "  JOIN security_user u ON u.id = mua.user_id" +
                "  LEFT JOIN schedule_mail_alert sa on sa.alert_id = a.id " +
                " WHERE own_mua.user_id = ? AND u.access_level != 'ROLE_ADMIN' AND sa.id IS NULL";
        Query query = getSession().createSQLQuery(sql);
        return query.setInteger(0, userId).list();
    }

    public List<Alert> findByUserAccounts(int userId) {
        String sql = "SELECT DISTINCT {a.*} " +
                " FROM alert {a}" +
                "   JOIN map_user_account mua ON mua.user_id = a.user_id " +
                "   JOIN map_user_account own_mua ON own_mua.account_id = mua.account_id" +
                "   JOIN security_user u ON u.id = mua.user_id" +
                " WHERE own_mua.user_id = ? AND u.access_level != 'ROLE_ADMIN'";
        Query query = getSession().createSQLQuery(sql).addEntity("a", Alert.class);
        return query.setInteger(0, userId).list();
    }

    public List<BaseMapAlert> findMapAlertAoiByUnitIdAndAoiId(int unitId, int aoiId) {
        LOGGER.info("findMapAlertAoiByUnitIdAndAoiId() started, unitId=" + unitId + ", aoiId=" + aoiId);
        Session session = getSession();
        Query query = session.createSQLQuery(
                " SELECT {maa.*}" +
                        " FROM map_alert_aoi {maa} " +
                        " JOIN aoi aoi ON maa.aoi_id = aoi.id" +
                        " JOIN alert a ON a.id = maa.alert_id" +
                        " JOIN map_alert_vehicle mav ON mav.alert_id = maa.alert_id" +
                        " WHERE aoi.is_deleted = 0 AND" +
                        " aoi.id = ? AND" +
                        " mav.vehicle_id = ? AND" +
                        " a.is_deleted = 0")
                .addEntity("maa", MapAlertAoi.class);
        return query.setInteger(0, aoiId).setInteger(1, unitId).list();
    }

    public List<BaseMapAlert> findMapAlertPoiByUnitIdAndPoiId(int unitId, int poiId) {
        Session session = getSession();
        Query query = session.createSQLQuery(
                " SELECT {map.*}" +
                        " FROM map_alert_poi {map} " +
                        " JOIN poi ON map.poi_id = poi.id" +
                        " JOIN alert a ON a.id = map.alert_id" +
                        " JOIN map_alert_vehicle mav ON mav.alert_id = map.alert_id" +
                        " WHERE poi.is_deleted = 0 AND" +
                        " poi.id = ? AND" +
                        " mav.vehicle_id = ? AND" +
                        " a.is_deleted = 0")
                .addEntity("map", MapAlertPoi.class);
        return query.setInteger(0, poiId).setInteger(1, unitId).list();
    }
}
