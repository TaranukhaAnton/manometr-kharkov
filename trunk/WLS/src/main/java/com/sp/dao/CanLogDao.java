package com.sp.dao;

import com.sp.model.CanLogRecord;
import com.sp.model.CanSensorName;
import com.sp.model.Journey;
import com.sp.util.TimePeriod;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CanLogDao extends BaseDao {
    public List<CanLogRecord> findByPeriodAndUnit(TimePeriod timePeriod, int unitId) {
        Session session = getSession();
        Query query = session.createSQLQuery(
                "SELECT {l.*} FROM can_log {l} " +
                        " WHERE created_date BETWEEN ? AND ? " +
                        " AND  l.unit_id = ? " +
                        " ORDER BY l.created_date").addEntity("l", CanLogRecord.class);
        return query.setTimestamp(0, timePeriod.getStartDate())
                .setTimestamp(1, timePeriod.getEndDate())
                .setInteger(2, unitId)
                .list();
    }

    public List<CanSensorName> findByDeviceId(int deviceId) {
         Session session = getSession();
        Query query = session.createSQLQuery(
                "SELECT {csn.*} FROM can_sensor_name {csn} " +
                        " WHERE csn.imei_id = ? ").addEntity("csn", CanSensorName.class);
        return query.setInteger(0, deviceId)
                .list();
    }

    public List<Journey> findCanJourneyByPeriodAndUnitId(TimePeriod timePeriod,
                                                         Integer unitId) {
        Session session = getSession();
        Query query = session.createSQLQuery(
                "SELECT {j.*} FROM journey {j} " +
                        "WHERE j.start_date >= ? AND j.start_date <= ? " +
                        "AND j.end_date >= ? and j.end_date <= ? " +
                        "AND j.unit_id = ? " +
                        " ORDER BY j.start_date").addEntity("j", Journey.class);
        return query.setTimestamp(0, timePeriod.getStartDate())
                .setTimestamp(1, timePeriod.getEndDate())
                .setTimestamp(2, timePeriod.getStartDate())
                .setTimestamp(3, timePeriod.getEndDate())
                .setInteger(4, unitId).list();
    }

    public Map<String, String> findCanJourneyValuesDescription(Integer unitId) {

         Session session = getSession();
           Query query = session.createSQLQuery(
                   "SELECT DISTINCT sensor_name, sensor_descr FROM can_journey_value " +
                   "WHERE unit_id = ?" +
                   " ORDER BY sensor_index");
          List<Object> objects = query.setInteger(0, unitId).list();
          Map<String, String> result = new LinkedHashMap<String, String>();
          for (Object o : objects) {
            result.put(((Object[])o)[1].toString(), ((Object[])o)[0].toString());
          }
          return  result;

    }

    public Map<Integer, String> findUnprocessedMessages() {
        Criteria criteria = getSession().createCriteria(CanLogRecord.class);
        criteria.add(Restrictions.isNull("unitNumber"));
        criteria.addOrder(Order.asc("id"));
        criteria.setMaxResults(100);

        List<CanLogRecord> list = criteria.list();
        Map<Integer, String> result = new HashMap<Integer, String>(list.size());
        for (CanLogRecord canLogRecord : list) {
            result.put(canLogRecord.getId(), canLogRecord.getMsgText());
        }

        return result;
    }
}
