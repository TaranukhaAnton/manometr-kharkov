package com.sp.dao;

import com.sp.model.BaseModel;
import com.sp.model.BaseTrackingDevice;
import com.sp.model.TrackingDevice;
import com.sp.model.UnitImeiHistory;
import com.sp.util.Constants;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
public class TrackingDeviceDao extends BaseDao {
    public BaseTrackingDevice findByVehicleId(int vehicleId) {
        Session session = getSession();
        return (BaseTrackingDevice) session.createQuery("from com.sp.model.TrackingDevice  as obj where obj.vehicle.id = ?")
                .setInteger(0, vehicleId).uniqueResult();
    }

    public List<TrackingDevice> findByLicenseId(int licenseId) {
        Session session = getSession();
        return (List<TrackingDevice>) session.createQuery("from com.sp.model.TrackingDevice  as obj where obj.license.id = ?")
                .setInteger(0, licenseId).list();
    }

    public List<UnitImeiHistory> findUnitImeiHistoryListByUnitId(int unitId){
        Criteria c = getSession().createCriteria(UnitImeiHistory.class);
        c.add(Restrictions.eq("unitId", unitId));
        c.addOrder(Order.desc("id"));
        return (List<UnitImeiHistory>) c.list();
    }

    public UnitImeiHistory findLastUnitImeiHistoryItem(int unitId){
        Criteria c = getSession().createCriteria(UnitImeiHistory.class);
        c.add(Restrictions.eq("unitId", unitId));
        c.addOrder(Order.desc("id"));
        c.setMaxResults(1);
        return (UnitImeiHistory)c.uniqueResult();
    }

    public List<TrackingDevice> findForUserId(int userId, Boolean active) {
        Session session = getSession();

        Query query = session.createSQLQuery(
                "SELECT {td.*} " +
                " FROM imei {td} " +
                    " JOIN map_user_account mua ON mua.account_id = td.account_id AND mua.user_id = ?" +
                (active == null ? "" : " WHERE is_deleted=" + (active ? "0" : "1")))
                .addEntity("td", TrackingDevice.class);

        return query.setInteger(0, userId).list();
    }


    public List<TrackingDevice> findByResellerId(int resellerId, boolean active){
        Session session = getSession();
        Query query = session.createSQLQuery(
                "select {td.*} from imei {td} where reseller_id " +
                 (resellerId != -1 ?  " = ? " : " not in (select id from reseller where descr = 'Testing')") +
                " and is_deleted=" + (active ? "0" : "1")
        ).addEntity("td", TrackingDevice.class);
        if (resellerId != -1){
            return query.setInteger(0, resellerId).list();
        }
        return query.list();
    }

    public void updateRetainJourneyEndByReseller(int resellerId, boolean retainJourneyEnd){
        Session session = getSession();
        Query query = session.createSQLQuery(
            "update imei " +
            "set is_retain_journey_end = ? " +
            "where reseller_id = ? and box_type_id in (:fmList)"
        );
        query.setBoolean(0, retainJourneyEnd)
                .setInteger(1, resellerId)
                .setParameterList("fmList", Constants.FM2_BOX_TYPE_ID_LIST).executeUpdate();
    }

    public List<String> findBoxForUserId(int userId) {
        Session session = getSession();

        Query query = session.createSQLQuery(
            "SELECT DISTINCT box_type.descr " +
                "FROM box_type " +
                "JOIN imei i ON i.box_type_id = box_type.id " +
                "JOIN unit u ON i.unit_id = u.id " +
                "JOIN map_unit_view_unit muvu ON muvu.vehicle_id = u.id "  +
                "JOIN unit_view uv ON muvu.group_id = uv.id " +
                "JOIN map_user_account mua ON mua.account_id = uv.account_id AND mua.user_id = ? " +
                "WHERE i.is_deleted = 0"
        );
        return query.setInteger(0, userId).list();
    }

    public List<String> findBoxTypeByGroupId(int groupId) {
        Session session = getSession();

        Query query = session.createSQLQuery(
                "SELECT DISTINCT box_type.descr " +
                "FROM box_type " +
                "JOIN imei td ON td.box_type_id = box_type.id " +       
                "JOIN unit u ON td.unit_id = u.id " +
                "JOIN map_unit_view_unit muvu ON muvu.vehicle_id = u.id "  +
                "JOIN unit_view uv ON muvu.group_id = uv.id " +
                "WHERE uv.id = ? AND td.is_deleted = 0"
        );
        return query.setInteger(0, groupId).list();
    }

    public TrackingDevice findByImei(String imei) {
        Session session = getSession();
        return (TrackingDevice) session.createQuery("from com.sp.model.TrackingDevice  as obj where obj.imei = ?")
                .setString(0, imei).uniqueResult();
    }

    public List<TrackingDevice> findAccountId(int accountId) {
        Session session = getSession();
        return (List<TrackingDevice>) session.createQuery("from com.sp.model.TrackingDevice  as obj where obj.account.id = ?")
                .setInteger(0, accountId).list();
    }

    public int countByLicenseId(int licenseId, int deviceId) {
        Session session = getSession();

        Criteria criteria = session.createCriteria(TrackingDevice.class);
        criteria.add(Restrictions.eq("license.id", licenseId));
        if (deviceId > 0) {
            criteria.add(Restrictions.ne("id", deviceId));
        }
        criteria.setProjection(Projections.rowCount());
        
        return (Integer) criteria.uniqueResult();
    }

    public List<BaseTrackingDevice> findByTimestamp(Date date) {
        Criteria criteria = getSession().createCriteria(BaseTrackingDevice.class)
                .createAlias("account", "a").createAlias("a.reseller", "r");
        criteria.add(Restrictions.or(Restrictions.gt("timestamp", date),
                Restrictions.or(Restrictions.gt("a.timestamp", date),
                        Restrictions.gt("r.timestamp", date)
                )
        )
        );

        return criteria.list();       
    }

    public BaseModel findBySimId(int simId) {
        Query query = getSession().createSQLQuery(
                "SELECT {td.*} FROM imei {td} WHERE assigned_sim_id = :assignedSimId")
                .addEntity("td", BaseTrackingDevice.class);
        query.setInteger("assignedSimId", simId);
        if (query.list().size() > 1) {
            return (BaseModel) query.list().get(0);
        }
        return (BaseModel) query.uniqueResult();
    }
}
