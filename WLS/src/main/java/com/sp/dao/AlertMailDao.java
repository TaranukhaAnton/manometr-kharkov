package com.sp.dao;

import com.sp.model.ScheduleMailAlert;
import com.sp.util.ScheduleAlertAwareContainer;
import com.sp.util.TimePeriod;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 */
public class AlertMailDao extends BaseDao {
    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<ScheduleMailAlert> findLastSheduleMails(int limit, int hours) {
        Session session = getSession();
        Query query = session.createSQLQuery(

                "SELECT {sma.*} "
                        + " FROM schedule_mail_alert {sma}"
                        + " WHERE sma.created_date > DATE_SUB(NOW(), INTERVAL ? HOUR)"
                        + " AND is_sent=0"
                        + " limit ?").addEntity("sma", ScheduleMailAlert.class);
        return query.setInteger(0, hours).setInteger(1, limit).list();
    }

    public List<ScheduleMailAlert> findForUserId(int viewId, TimePeriod timePeriod) {
        Session session = getSession();
        Query query = session.createSQLQuery(
                "SELECT {sma.*} " +
                        " FROM schedule_mail_alert {sma} " +
                        " JOIN map_unit_view_unit muvu on sma.unit_id = muvu.vehicle_id " +
                        "WHERE rec_date BETWEEN ? AND ?" +
                        " AND muvu.group_id = ? and item_type='vehicle' " +
                        "ORDER BY unit_id, id, rec_date ")
                .addEntity("sma", ScheduleMailAlert.class);
        return query.setTimestamp(0, timePeriod.getStartDate()).setTimestamp(1, timePeriod.getEndDate()).setInteger(2, viewId).list();
    }

    public ScheduleMailAlert findPrevSubsiquentScheduleAlert(int vehicleId,int geofenceAlertId,boolean isEnterAlert,boolean isAoi,int poiAoiId){
        Criteria criteria = getSession().createCriteria(ScheduleMailAlert.class);
        criteria.add(Restrictions.eq("vehicle.id", vehicleId));
        criteria.add(Restrictions.eq("alert.id", geofenceAlertId));
        criteria.add(Restrictions.eq("isEntered", !isEnterAlert));
        criteria.add(Restrictions.eq(isAoi ? "aoiId" : "poiId", poiAoiId));
        criteria.addOrder(Order.desc("recDate"));
        criteria.setMaxResults(1);
        return (ScheduleMailAlert) criteria.uniqueResult();
    }

    public Map<Integer, Map<Integer,Integer>> fillPreviousPoiAoiIdMap(boolean isAoi) {
        final String entityField = isAoi ? "aoi_id" : "poi_id";
        String sql =
                "select sm.unit_id unit_id,sm."+ entityField +" " + entityField + " from schedule_mail_alert sm " +
                "join ( " +
                    " select max(rec_date) sub_rec_date, unit_id ,"+ entityField +
                    " from schedule_mail_alert " +
                    " where created_date > DATE_SUB(now(),INTERVAL 5 MONTH) " +
                    " and " + entityField + " is not null " +
                    " group by unit_id, "+ entityField +") as t " +
                "where t.sub_rec_date = sm.`rec_date` and t.unit_id = sm.unit_id and t." + entityField + " = sm." + entityField + " and sm.is_entered = 1";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        return (ConcurrentHashMap<Integer, Map<Integer,Integer>>)jdbcTemplate.query(sql, new ResultSetExtractor() {
                public Object extractData(ResultSet rs) throws SQLException {
                    ConcurrentHashMap<Integer, Map<Integer,Integer>> map = new ConcurrentHashMap<Integer, Map<Integer,Integer>>();
                    while (rs.next()) {
                        int unitId = rs.getInt("unit_id");
                        if(!map.containsKey(unitId)){
                            map.put(unitId,new HashMap<Integer,Integer>());
                        }
                        int poiAoiId = rs.getInt(entityField);
                        map.get(unitId).put(poiAoiId, ScheduleAlertAwareContainer.EXITED_START_VALUE);// means vehicle is inside POI/AOI and we are going to track exit event
                    }
                    return map;
                }
            }
            );
        }
    }
