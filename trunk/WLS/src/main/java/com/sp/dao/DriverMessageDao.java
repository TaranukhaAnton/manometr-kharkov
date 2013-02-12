package com.sp.dao;

import com.sp.model.DriverMessage;
import com.sp.util.TimePeriod;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;

import java.math.BigInteger;
import java.util.List;


public class DriverMessageDao extends BaseDao {

    public List<DriverMessage> findMessagesByUnitViewAndDate(int unitViewId, TimePeriod timePeriod, String restrictedVehicles,
                                                          boolean isTask) {
        Session session = getSession();
        Query query = session.createSQLQuery(

                "SELECT {dm.*} " + " FROM driver_message {dm} "
                        + "JOIN map_unit_view_unit muvu on dm.unit_id = muvu.vehicle_id "
                        + "JOIN unit u on u.id = dm.unit_id "
                        + "WHERE dm.rec_date BETWEEN ? AND ? "
                        + "AND muvu.group_id = ? AND u.is_deleted=0 "
                        + "AND dm.message_type"
                        + (isTask ? " in"  : " not in ")
                        + "(:messageType) " 
                        + (restrictedVehicles.length() > 0 ? " AND dm.unit_id NOT IN (" + restrictedVehicles + ")" : "")
                        + "ORDER BY dm.unit_id, dm.rec_date").addEntity("dm", DriverMessage.class);
        query.setParameterList("messageType", DriverMessage.TASK_TYPE, Hibernate.STRING);
        return query.setTimestamp(0, timePeriod.getStartDate()).setTimestamp(1, timePeriod.getEndDate())
                .setInteger(2, unitViewId).list();
    }

    public List<DriverMessage> findMessagesByVehicleAndDate(int vehicleId,
                                                          boolean isTask) {
        Session session = getSession();
        Query query = session.createSQLQuery(

                "SELECT {dm.*} " + " FROM driver_message {dm} "
                        + "JOIN unit u on u.id = dm.unit_id "
                        + "WHERE dm.unit_id = ? AND u.is_deleted=0 "
                        + "AND dm.message_type"
                        + (isTask ? " in"  : " not in ")
                        + "(:messageType) "
                        + "ORDER BY dm.unit_id, dm.rec_date").addEntity("dm", DriverMessage.class);
        query.setParameterList("messageType", DriverMessage.TASK_TYPE, Hibernate.STRING);
        return query.setInteger(0, vehicleId).list();
    }

    public int findUnreadMessagesCount(int vehicleId, boolean isTask) {
        Session session = getSession();
        Query query = session.createSQLQuery(

                "SELECT count(*) FROM driver_message dm "
                        + "JOIN unit u on u.id = dm.unit_id "
                        + "WHERE dm.unit_id = ? AND u.is_deleted=0 AND dm.unread=1 "
                        + "AND dm.message_type "
                        + (isTask ? " in"  : " not in ")
                        + "(:messageType) "
                        + "ORDER BY dm.unit_id, dm.rec_date");
        query.setParameterList("messageType", DriverMessage.TASK_TYPE, Hibernate.STRING);
        return ((BigInteger) query.setInteger(0, vehicleId).uniqueResult()).intValue();
    }
}
