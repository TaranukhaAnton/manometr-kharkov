package com.sp.dao;

import com.sp.SpContext;
import com.sp.dto.FastSmsInboundDto;
import com.sp.dto.flex.LocationRecord;
import com.sp.dto.report.CurIncomingLogStat;
import com.sp.dto.report.DigitalInputEventRecord;
import com.sp.dto.report.DriverLeagueTableRecord;
import com.sp.dto.report.IterativeIncomingLogStat;
import com.sp.exception.ServiceException;
import com.sp.model.*;
import com.sp.util.TimePeriod;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.exception.LockAcquisitionException;
import org.hibernate.transform.Transformers;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlQuery;

import javax.sql.DataSource;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
public class IncomingLogDao extends BaseDao {
    private SqlQuery findNearestSevenDigitPostcode;
    private SqlQuery findCurIncomingLogStatQuery;
    private SqlQuery findIterativeIncomingLogStatQuery;
    private SqlQuery findDigitalInputQuery;
    private SqlQuery processIncomingLogQuery;
    private SqlQuery processIncomingLogQueryCustom;
    private SqlQuery findDigitalInputEventQuery;
    private DataSource dataSource;

    private final static Logger LOGGER = Logger.getLogger(IncomingLogDao.class);

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        findCurIncomingLogStatQuery = new FindCurIncomingLogStatQuery(dataSource);
        findDigitalInputQuery = new FindDigitalInputQuery(dataSource);
        findIterativeIncomingLogStatQuery = new FindIterativeIncomingLogStat(dataSource);
        findDigitalInputEventQuery = new FindDigitalInputEventQuery(dataSource);
        processIncomingLogQueryCustom = new MappingSqlQuery(dataSource, "call generate_journeys_protrack()") {
            @Override
            protected Object mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getInt(1);
            }
        };
        processIncomingLogQuery = new MappingSqlQuery(dataSource, "call generate_journeys()") {
            @Override
            protected Object mapRow(ResultSet resultSet, int i) throws SQLException {
                return resultSet.getInt(1);
            }
        };
        findNearestSevenDigitPostcode = new FindNearetSevenDigitPostcodeQuery(dataSource);
    }

    public List<IncomingLogRecord> findByPeriodAndNodeId(TimePeriod timePeriod, List<String> nodeIdList) {
        Session session = getSession();
        Criteria criteria = session.createCriteria(IncomingLogRecord.class);
        criteria.add(Restrictions.between("recDate", timePeriod.getStartDate(), timePeriod.getEndDate()));
        criteria.add(Restrictions.in("nodeId", nodeIdList));

        return (List<IncomingLogRecord>) criteria.list();
    }

    public IncomingLogRecord findLastIncLogByNodeId(String imei) {
        Criteria criteria = getSession().createCriteria(IncomingLogRecord.class);
        criteria.add(Restrictions.eq("nodeId", imei));
        criteria.addOrder(Order.desc("recDate"));
        criteria.setMaxResults(1);
        return (IncomingLogRecord) criteria.uniqueResult();
    }

    public List<IncomingLogRecord> findByJourneyIdList(List<Integer> journeyIds) {
        if (journeyIds.size() == 0) {
            return new ArrayList<IncomingLogRecord>();
        }
        Session session = getSession();
        Criteria criteria = session.createCriteria(IncomingLogRecord.class);
        criteria.add(Restrictions.in("journeyId", journeyIds));
        criteria.add(Restrictions.ne("lat", 0d));
        criteria.add(Restrictions.ne("lon", 0d));
        criteria.addOrder(Order.asc("journeyId"));
        criteria.addOrder(Order.asc("recDate"));

        return (List<IncomingLogRecord>) criteria.list();
    }

    public List<IncomingLogRecord> findByJourneyId(int journeyId) {
        Session session = getSession();
        Criteria criteria = session.createCriteria(IncomingLogRecord.class);
        criteria.add(Restrictions.eq("journeyId", journeyId));
        criteria.add(Restrictions.ne("lat", 0d));
        criteria.add(Restrictions.ne("lon", 0d));
        criteria.addOrder(Order.asc("recDate"));

        return (List<IncomingLogRecord>) criteria.list();
    }



    public Map<String, String> getPreviousDallasKeyCodeMap() {
        String sql = "select i.NodeID , i.dallas_key_code FROM incoming_log i " +
                "join " +
                "(select b.unit_id , max(b.rec_date) max_date from incoming_log b where b.rec_date > DATE_SUB(now(),INTERVAL 7 day) GROUP BY b.NodeID) " +
                "as t on t.unit_id = i.unit_id " +
                "where t.max_date = i.rec_date " +
                "and TRIM(IFNULL(i.dallas_key_code,'')) <> ''";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        Map<String, String> result = (Map<String, String>) jdbcTemplate.query(sql, new ResultSetExtractor() {
            public Object extractData(ResultSet rs) throws SQLException {
                Map<String, String> map = new ConcurrentHashMap<String, String>();
                while (rs.next()) {
                    String dallasKeyCode = rs.getString("dallas_key_code");
                    String nodeID = rs.getString("NodeID");
                    map.put(nodeID, dallasKeyCode);
                }
                return map;
            }
        }
        );
        return result;
    }

    public List<VehicleIncomingLogRecord> findBaseVehicleRecordsByPeriodAndUnitId(TimePeriod timePeriod, int unitId) {
        Session session = getSession();
        return session.createSQLQuery(
                "SELECT {t.*} FROM incoming_log {t} " +
                        " WHERE unit_id = ?" +
                        " AND rec_date BETWEEN ? AND ? " +
                        " ORDER BY rec_date desc")
                .addEntity("t", VehicleIncomingLogRecord.class)
                .setInteger(0, unitId)
                .setTimestamp(1, timePeriod.getStartDate())
                .setTimestamp(2, timePeriod.getEndDate())
                .list();
    }



    public List<IncomingLogRecord> findIncomingLogRecordsByPeriodAndUnitId(TimePeriod timePeriod, int unitId) {
       return findIncomingLogRecordsByPeriodAndUnitId(timePeriod,unitId,"desc");
    }


     // todo migrate to Criteria
    public List<IncomingLogRecord> findIncomingLogRecordsByPeriodAndUnitId(TimePeriod timePeriod, int unitId, String direction) {
        Session session = getSession();
        return session.createSQLQuery(
                "SELECT {t.*} FROM incoming_log {t}" +
                        " WHERE unit_id = ?" +
                        " AND rec_date BETWEEN ? AND ?" +
                        " ORDER BY rec_date "+direction)
                .addEntity("t", IncomingLogRecord.class)
                .setInteger(0, unitId)
                .setTimestamp(1, timePeriod.getStartDate())
                .setTimestamp(2, timePeriod.getEndDate())
                .list();
    }

    public Map<String, IncomingLogRecord> findIncLogRecordsInfoBySmsInboundList(List<FastSmsInboundDto> inboundDtos, Map<String, Vehicle> phoneVehicleMap) {
        StringBuilder builder = new StringBuilder();
        String union = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (FastSmsInboundDto dto : inboundDtos) {
            builder.append(union);
            builder.append("(select '");
            builder.append(dto.getFromNumber());
            builder.append(dto.getDeliveredDateStr());
            builder.append("', i.streetName, i.postcode from incoming_log i where i.rec_date <= '");
            builder.append(dateFormat.format(dto.getDeliveredDate()));
            builder.append("' and i.unit_id = ");
            builder.append(phoneVehicleMap.get(dto.getFromNumber()).getId());
            builder.append(" order by i.rec_date desc limit 1)");
            union = " union ";
        }
        if (builder.length() == 0) {
            return new HashMap<String, IncomingLogRecord>();
        }
        List list = getSession().createSQLQuery(builder.toString()).list();
        Map<String, IncomingLogRecord> res = new HashMap<String, IncomingLogRecord>();
        for (Object o : list) {
            IncomingLogRecord rec = new IncomingLogRecord();
            rec.setStreetName(((Object[]) o)[1] != null ? ((Object[]) o)[1].toString() : null);
            rec.setPostcode(((Object[]) o)[2] != null ? ((Object[]) o)[2].toString() : null);
            res.put(((Object[]) o)[0].toString(), rec);
        }
        return res;
    }
    //TODO DBLT - fix 3 grids.
    public List<DriverLeagueTableRecord> findDriverLeagueTable(Set<Integer> vehicleSet,TimePeriod timePeriod){
        StringBuilder sql = new StringBuilder();
        sql.append("select d.unit_id unitId,d.accelerationOrange,d.accelerationRed,d.breakingOrange,d.breakingRed,d.corneringOrange,d.corneringRed,");
        sql.append("(case when imei.is_green_driving_allowed then (d.dist*0.001) else -1 end) miles");   // -1 miles for drivers with disallowed green driving
        sql.append(",(d.accelerationOrange+d.accelerationRed) accelerationTotal,(d.breakingOrange+d.breakingRed) breakingTotal,(d.corneringOrange+d.corneringRed) corneringTotal ");
        sql.append(",(d.accelerationOrange*2+d.accelerationRed*3) accelerationPoints,(d.breakingOrange*2+d.breakingRed*3) breakingPoints,(d.corneringOrange*2+d.corneringRed*3) corneringPoints ");
        sql.append(",(d.accelerationOrange+d.breakingOrange+d.corneringOrange) totalsOrange ");
        sql.append(",(d.accelerationRed+d.breakingRed+d.corneringRed) totalsRed ");
        sql.append(",(d.accelerationOrange+d.accelerationRed+d.breakingOrange+d.breakingRed+d.corneringOrange+d.corneringRed) totalsTotal ");
        sql.append(",(d.accelerationOrange*2+d.accelerationRed*3+d.breakingOrange*2+d.breakingRed*3+d.corneringOrange*2+d.corneringRed*3) totalsPoints ");
        sql.append(",case when (d.accelerationOrange*2+d.accelerationRed*3+d.breakingOrange*2+d.breakingRed*3+d.corneringOrange*2+d.corneringRed*3) = 0 then (d.dist*0.001) else d.dist*0.001 " +
                "/(d.accelerationOrange*2+d.accelerationRed*3+d.breakingOrange*2+d.breakingRed*3+d.corneringOrange*2+d.corneringRed*3) end  score ");
        sql.append(",concat(su.first_name,' ',su.last_name) driverName,u.registration_number vehicleReg ");
        sql.append("from ");
        sql.append("( ");
        sql.append("  select j.driver_id driver_id,i.unit_id unit_id, ");
        sql.append("  sum(case when i.harsh_acceleration > 0 and i.harsh_acceleration >= im.max_acceleration_force_orange_val and i.harsh_acceleration < im.max_acceleration_force_red_val then 1 else 0 end) accelerationOrange, ");
        sql.append("  sum(case when i.harsh_acceleration > 0 and i.harsh_acceleration >= im.max_acceleration_force_red_val then 1 else 0 end) accelerationRed, ");
        sql.append("  sum(case when i.harsh_braking > 0 and i.harsh_braking >= im.max_braking_force_orange_val and i.harsh_braking < im.max_braking_force_red_val then 1 else 0 end) breakingOrange, ");
        sql.append("  sum(case when i.harsh_braking > 0 and i.harsh_braking >= im.max_braking_force_red_val then 1 else 0 end) breakingRed, ");
        sql.append("  sum(case when i.harsh_cornering > 0 and i.harsh_cornering >=im.max_cornering_orange_val and i.harsh_cornering<im.max_cornering_red_val then 1 else 0 end) corneringOrange, ");
        sql.append("  sum(case when i.harsh_cornering > 0 and i.harsh_cornering >=im.max_cornering_red_val then 1 else 0 end) corneringRed, ");
        sql.append("  jd.dst dist ");
        sql.append("  from incoming_log i use index(idx_tbincominglog_unit_rec_date) ");

        sql.append("  join journey j on i.journey_id = j.id ");
        sql.append("  join (select  driver_id, sum(distance) dst from journey where start_date between :startDate and :endDate " +
                "                 group by driver_id) jd on jd.driver_id = j.driver_id ");
        sql.append("  join imei im on im.unit_id = i.unit_id ");
        sql.append("  where  ");
        sql.append("  i.unit_id in (:vehicleSet) ");
        sql.append("  and i.rec_date between :startDate and :endDate ");
        sql.append("  and j.driver_id is not null ");
        sql.append("  group by j.driver_id,i.unit_id ");
        sql.append("  order by j.driver_id, dist desc ");
        sql.append(") d  ");
        sql.append("join security_user su on su.id = d.driver_id ");
        sql.append("join unit u on u.id = d.unit_id ");
        sql.append("join imei on imei.unit_id = d.unit_id ");
        sql.append("order by score desc ");
        Session session = getSession();
        Query query = session.createSQLQuery(sql.toString())
                .addScalar("unitId", Hibernate.INTEGER)
                .addScalar("driverName", Hibernate.STRING)
                .addScalar("vehicleReg", Hibernate.STRING)
                .addScalar("score", Hibernate.DOUBLE)
                .addScalar("miles", Hibernate.DOUBLE)
                .addScalar("accelerationOrange", Hibernate.INTEGER)
                .addScalar("accelerationRed", Hibernate.INTEGER)
                .addScalar("accelerationTotal", Hibernate.INTEGER)
                .addScalar("accelerationPoints", Hibernate.INTEGER)
                .addScalar("breakingOrange", Hibernate.INTEGER)
                .addScalar("breakingRed", Hibernate.INTEGER)
                .addScalar("breakingTotal", Hibernate.INTEGER)
                .addScalar("breakingPoints", Hibernate.INTEGER)
                .addScalar("corneringOrange", Hibernate.INTEGER)
                .addScalar("corneringRed", Hibernate.INTEGER)
                .addScalar("corneringTotal", Hibernate.INTEGER)
                .addScalar("corneringPoints", Hibernate.INTEGER)
                .addScalar("totalsOrange", Hibernate.INTEGER)
                .addScalar("totalsRed", Hibernate.INTEGER)
                .addScalar("totalsTotal", Hibernate.INTEGER)
                .addScalar("totalsPoints", Hibernate.INTEGER)
                .setResultTransformer(Transformers.aliasToBean(DriverLeagueTableRecord.class));
        List<DriverLeagueTableRecord> r = new ArrayList<DriverLeagueTableRecord>();
        try{
            r = query.setParameterList("vehicleSet",vehicleSet).setParameter("startDate",timePeriod.getStartDate()).setParameter("endDate",timePeriod.getEndDate()).list();
        } catch (Exception e){
            LOGGER.error(e);
        }
        return r;
    }

    public List<IncomingLogRecord> findDiagnosticHistoryByPeriodAndGroupId(TimePeriod timePeriod,
                                                                           int groupId,
                                                                           String orderField,
                                                                           List<Integer> filterBoxTypeIdList,
                                                                           String restrictedVehicles) {
        Session session = getSession();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT {t.*} FROM incoming_log {t} ");
        sql.append("JOIN map_unit_view_unit muvu on t.unit_id = muvu.vehicle_id ");
        sql.append(" WHERE rec_date BETWEEN ? AND ? AND muvu.group_id = ? ");
        if (filterBoxTypeIdList != null && !filterBoxTypeIdList.isEmpty()) {
            sql.append("AND t.box_type_id in (:boxTypeIdList)");
        }
        if (restrictedVehicles.length() > 0) {
            sql.append("AND t.unit_id NOT IN (").append(restrictedVehicles).append(")");
        }
        sql.append(" ORDER BY ");
        sql.append(orderField);
        Query query = session.createSQLQuery(sql.toString())
                .addEntity("t", IncomingLogRecord.class)
                .setTimestamp(0, timePeriod.getStartDate())
                .setTimestamp(1, timePeriod.getEndDate())
                .setInteger(2, groupId);
        if (filterBoxTypeIdList != null && !filterBoxTypeIdList.isEmpty()) {
            query.setParameterList("boxTypeIdList", filterBoxTypeIdList, Hibernate.INTEGER);
        }
        return query.list();
    }

    public IncomingLogRecord findUnitHealthStateByNodeId(String imei) {
        return null;
    }

    public List<IterativeIncomingLogStat> findIterativeIncomingLogStat() {
        return findIterativeIncomingLogStatQuery.execute();
    }

    public List<CurIncomingLogStat> findCurIncomingLogStat(int periodStartMoveBackDays) {
        return findCurIncomingLogStatQuery.execute(new Object[]{periodStartMoveBackDays});
    }

    public List<DigitalInputReportRecord> findDigitalInputsValues(Date startDate, Date endDate, List<Integer> vehicleIds,
                                                                  boolean hideIgnition) {
        Object[] params = {startDate, endDate, hideIgnition, StringUtils.join(vehicleIds.toArray(), ","), SpContext.getUserDetailsInfo().getUserId()};
        return findDigitalInputQuery.execute(params);
    }

    public List<DigitalInputEventRecord> findDigitalInputEventValues(Date startDate, Date endDate, List<Integer> vehicleIds,
                                                                     boolean hideIgnition) {
        Object[] params = {startDate, endDate, hideIgnition, StringUtils.join(vehicleIds.toArray(), ","), SpContext.getUserDetailsInfo().getUserId()};
        return findDigitalInputEventQuery.execute(params);
    }

    public int processIncomingLog() throws ServiceException {
        Integer result = (Integer) processIncomingLogQuery.execute().get(0);
        return result;
    }

    public int processIncomingLogCustom() throws ServiceException {
        Integer result = (Integer) processIncomingLogQueryCustom.execute().get(0);
        return result;
    }

    public List<HandheldIncomingLogRecord> takeHHIncomingLogRecordsToProcessing(Set<Integer> resellerIdList, Date fromDate) {
        LOGGER.info("takeHHIncomingLogRecordsToProcessing() started, resellerIdList=" + resellerIdList);
        Session session = getSession();
        String sql = "select {hhl.*} from handheld_incoming_log {hhl} " +
                "join map_handheld_account ma on hhl.handheld_id = ma.unit_id " +
                "join account a on a.id = ma.account_id " +
                "join reseller r on r.id = a.reseller_id " +
                "where r.id in (:resellerIdList) " +
                "and hhl.processing_status is null " +
                "and hhl.rec_date > :fromDate " +
                "limit 1000";
        Query query = session.createSQLQuery(sql).addEntity("hhl", HandheldIncomingLogRecord.class).setParameterList("resellerIdList", resellerIdList)
                .setTimestamp("fromDate", fromDate);
        List<HandheldIncomingLogRecord> result = query.list();
        LOGGER.info("takeHHIncomingLogRecordsToProcessing() finished, result.size()=" + result.size());
        return result;
    }

    public List<Integer> takeIncomingLogRecordsToProcessing(Set<Integer> resellerIdList,
                                                            List<Integer> excludeDeviceIdList) {
        LOGGER.info("takeIncomingLogRecordsToProcessing() started, resellerIdList=" + resellerIdList);
        Session session = getSession();
        Query query = session.createSQLQuery(
                "SELECT il.id FROM incoming_log_processing_queue il " +
                        "  JOIN imei i ON i.id = il.tracking_device_id " +
                        "  JOIN account a ON a.id = i.account_id " +
                        " WHERE a.reseller_id IN (:resellerIdList) " +
                        (excludeDeviceIdList == null || excludeDeviceIdList.size() == 0
                                ? "" : " AND il.tracking_device_id NOT IN (:excludeDeviceIdList)") +
                        " ORDER BY il.rec_date, il.id " +
                        " LIMIT 500")
                .setParameterList("resellerIdList", resellerIdList);
        if (excludeDeviceIdList != null && excludeDeviceIdList.size() > 0) {
            query.setParameterList("excludeDeviceIdList", excludeDeviceIdList);
        }
        List<Integer> result = (List<Integer>) query.list();
        if (result.size() > 0) {
            query = session.createSQLQuery("DELETE FROM incoming_log_processing_queue WHERE id IN (:incomingLogIdList)")
                    .setParameterList("incomingLogIdList", result);

            try {
                query.executeUpdate();
            } catch (LockAcquisitionException e) {
                LOGGER.error("takeIncomingLogRecordsToProcessing(): Cannot delete from incoming_log_processing_queue, incomingLogIdList=" + result);
                throw e;
            }
        }

        LOGGER.info("takeIncomingLogRecordsToProcessing() finished, result.size()=" + result.size());
        return result;
    }

    public List<IncomingLogRecord> findIncomingLogRecordsToReplicate() {
        List<IncomingLogRecord> incomingLogRecordList = (List<IncomingLogRecord>) getSession().createSQLQuery(
                "SELECT {il.*} FROM incoming_log {il} JOIN replication_log rl ON rl.id = il.id" +
                        " ORDER BY NodeID, rec_date, il.id " +
                        " LIMIT 500")
                .addEntity("il", IncomingLogRecord.class)
                .list();

        return incomingLogRecordList;
    }

    public List<VehicleIncomingLogRecord> findLastSafetyCarRecordByPorts(List<String> safetyImeis,
                                                                         boolean isCollected) {
        List<VehicleIncomingLogRecord> incomingLogRecords = (List<VehicleIncomingLogRecord>) getSession().createSQLQuery(

                "SELECT {il1.*} " +
                        "FROM incoming_log {il1} " +
                        "LEFT OUTER JOIN incoming_log il2 " +
                        "ON (il1.id <= il2.id and il1.NodeId = il2.NodeId " +
                        (isCollected ? " and il1.processing_status = il2.processing_status  " : " ") +
                        " ) " +
                        "WHERE il1.NodeId in (:safetyImeis) and il1.rec_date between date_sub(now(), interval 10 minute) and now() " +
                        (isCollected ? " and il1.processing_status = 4 " : " ") +
                        "GROUP BY il1.id " +
                        "HAVING COUNT(*) = 1 " +
                        "ORDER BY il1.id, il1.processing_status, il1.rec_date ")
                .addEntity("il1", IncomingLogRecord.class)
                .setParameterList("safetyImeis", safetyImeis)
                .list();

        return incomingLogRecords;
    }

    public List<IncomingLogRecord> findLogRecordListAfterLastGeneratedJourney(List<Integer> unitIdList){
        String sql = " select {i.*} from incoming_log {i} " +
                " left join (select (case when j.end_log_id is not null then max(j.end_date) else curdate() end) end_date,j.unit_id unit_id from journey j  where j.unit_id in (:unitIdList) " +
                " and j.start_date > curdate() group by j.unit_id order by j.start_date) sub " +
                " on sub.unit_id = i.unit_id " +
                " where i.unit_id in (:unitIdList) and i.rec_date > curdate() and i.is_ignition_active = 1 " +
                " and i.rec_date> (case when sub.end_date is not null then sub.end_date else curdate() end)" +
                " order by i.unit_id, i.rec_date";
        return (List<IncomingLogRecord>) getSession().createSQLQuery(sql).addEntity("i",IncomingLogRecord.class).setParameterList("unitIdList", unitIdList).list();
    }

    public List<IncomingLogRecord> findBackToTheFutureLogList(Date pastDate, List<Integer> vehicleIdList) {
        String sql = "select {l.*} "+
                    "from incoming_log {l} "+
                    "join "+
                    "( "+
                    "select i.unit_id as sub_unit_id, max(i.rec_date) mrd "+
                    "from incoming_log i "+
                    "where i.rec_date < ? "+
                    "and i.unit_id in (:vehicleIdList) "+
                    "GROUP BY i.unit_id "+
                    ") as sub "+
                    "on l.rec_date = sub.mrd and l.unit_id = sub.sub_unit_id " +
                    "group by l.unit_id";
        List<IncomingLogRecord> incomingLogRecordList = (List<IncomingLogRecord>) getSession().createSQLQuery(sql)
                .addEntity("l", IncomingLogRecord.class)
                .setTimestamp(0, pastDate)
                .setParameterList("vehicleIdList", vehicleIdList)
                .list();
        return incomingLogRecordList;
    }

    public void deleteFromReplicationLog(List<Integer> idList) {
        Query query = getSession().createSQLQuery("delete from replication_log where id in (:idList)");
        query.setParameterList("idList", idList, Hibernate.INTEGER).executeUpdate();
    }

    public void saveResetUncompletedProcessing(Set<Integer> resellerIdList) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) - 1);
        Query query = getSession().createSQLQuery(
                "INSERT IGNORE INTO incoming_log_processing_queue" +
                        " SELECT l.id, l.rec_date, l.tracking_device_id " +
                        " FROM incoming_log l " +
                        " JOIN imei i ON i.id = l.tracking_device_id" +
                        " JOIN account a ON a.id = i.account_id" +
                        " WHERE l.processing_status IS NULL " +
                        " AND l.rec_date > :fromDate " +
                        " AND l.unit_id IS NOT NULL " +
                        " AND a.reseller_id IN (:resellerIdList)");
        query.setParameterList("resellerIdList", resellerIdList, Hibernate.INTEGER).setTimestamp("fromDate", calendar.getTime()).executeUpdate();
    }

    public List<VehicleIncomingLogRecord> findByIdList(List<Integer> idList) {
        List<VehicleIncomingLogRecord> result = (List<VehicleIncomingLogRecord>) getSession().createSQLQuery(
                "SELECT {il.*} FROM incoming_log {il} WHERE il.id IN (:ids)")
                .addEntity("il", VehicleIncomingLogRecord.class)
                .setParameterList("ids", idList)
                .list();

        return result;
    }

    private class FindIterativeIncomingLogStat extends MappingSqlQuery {

        private FindIterativeIncomingLogStat(DataSource dataSource) {
            super(dataSource,
                    "select " +
                            "i.NodeID," +
                            "i.rec_date," +
                            "(SELECT u.registration_number " +
                            "FROM  unit u " +
                            "WHERE u.id = i.unit_id) AS registration_number," +
                            "i.unit_id, " +
                            "(SELECT b.descr " +
                            "FROM box_type b " +
                            "WHERE b.id = i.box_type_id) AS box_descr," +
                            "max(i.id) as max_id," +
                            "count(i.id) as cnt" +
                            " from incoming_log i " +
                            " where i.rec_date > DATE_SUB(NOW(),INTERVAL 2 HOUR) " +
                            " GROUP BY i.rec_date,i.NodeID " +
                            " HAVING cnt > 2");
            compile();
        }

        protected Object mapRow(ResultSet rs, int rowNumber) throws SQLException {
            IterativeIncomingLogStat res = new IterativeIncomingLogStat();
            res.setUnitBoxTypeDescr(rs.getString("box_descr"));
            res.setUnitRegNumber(rs.getString("registration_number"));
            res.setIncomingLogsCount(rs.getInt("cnt"));
            res.setMaxIncomingLogId(rs.getInt("max_id"));
            res.setNodeId(rs.getString("NodeID"));
            res.setRecDate(rs.getDate("rec_date"));
            res.setUnitId(rs.getInt("unit_id"));
            return res;
        }
    }

    public static class FindCurIncomingLogStatQuery extends MappingSqlQuery {
        public FindCurIncomingLogStatQuery(DataSource ds) {
            super(ds, "call cur_incoming_log_stat_new(?)");
            declareParameter(new SqlParameter(Types.INTEGER));
            compile();
        }


        protected Object mapRow(ResultSet rs, int rowNumber) throws SQLException {
            CurIncomingLogStat result = new CurIncomingLogStat();
            result.setDescr(rs.getString("descr"));
            result.setIncomingLogProcessorServiceId(rs.getInt("incoming_log_processor_service_id"));
            result.setBoxTypeDescr(rs.getString("box_type_descr"));

            result.setMsgCount(rs.getInt("incoming_log_cnt"));
            result.setProcessedMsgCount(rs.getInt("incoming_log_processed_cnt"));
            result.setJourneyAssignedMsgCount(rs.getInt("incoming_log_journey_assigned_cnt"));
            result.setMaxMsgDate(rs.getTimestamp("max_rec_date"));

            result.setJourneyCount(rs.getInt("journey_cnt"));
            result.setNodeCount(rs.getInt("node_cnt"));
            result.setMaxJourneyStartDate(rs.getTimestamp("max_journey_start_date"));
            result.setMaxJourneyEndDate(rs.getTimestamp("max_journey_end_date"));
            result.setResellerId(rs.getInt("reseller_id"));

            result.setBoxTypeId(rs.getInt("box_type_id"));
            result.setTotalDevices(rs.getInt("total_devices"));
            result.setCollector(rs.getString("collector"));

            return result;
        }
    }

    private class FindDigitalInputEventQuery extends MappingSqlQuery {
        public FindDigitalInputEventQuery(DataSource ds) {
            super(ds, "call process_digital_input_events(?,?,?,?,?)");
            declareParameter(new SqlParameter("startDate", Types.TIMESTAMP));
            declareParameter(new SqlParameter("endDate", Types.TIMESTAMP));
            declareParameter(new SqlParameter("hideIgnition", Types.TINYINT));
            declareParameter(new SqlParameter("commaseparated_unit_ids", Types.VARCHAR));
            declareParameter(new SqlParameter("in_user_id", Types.INTEGER));
            compile();
        }

        protected Object mapRow(ResultSet rs, int rowNumber) throws SQLException {
            DigitalInputEventRecord result = new DigitalInputEventRecord();
            result.setPostcode(rs.getString("postcode"));
            result.setPoiDescr(rs.getString("poi_descr"));
            result.setAoiDescr(rs.getString("aoi_descr"));
            result.setStreetName(rs.getString("street_name"));
            result.setRecDate(rs.getTimestamp("rec_date"));
            result.setDigitalInputName(rs.getString("digital_input_name"));
            result.setVehicleId(rs.getInt("vehicle_id"));
            result.setInputIndex(rs.getInt("input_index"));
            result.setIgnition(rs.getBoolean("is_ignition"));
            result.setDistance(rs.getFloat("distance"));
            result.setJourneyId(rs.getInt("journey_id"));

            return result;
        }
    }

    private class FindDigitalInputQuery extends MappingSqlQuery {
        public FindDigitalInputQuery(DataSource ds) {
            super(ds, "call process_digital_inputs(?,?,?,?,?)");
            declareParameter(new SqlParameter("startDate", Types.TIMESTAMP));
            declareParameter(new SqlParameter("endDate", Types.TIMESTAMP));
            declareParameter(new SqlParameter("hideIgnition", Types.TINYINT));
            declareParameter(new SqlParameter("commaseparated_unit_ids", Types.VARCHAR));
            declareParameter(new SqlParameter("in_user_id", Types.INTEGER));
            compile();
        }


        protected Object mapRow(ResultSet rs, int rowNumber) throws SQLException {
            DigitalInputReportRecord result = new DigitalInputReportRecord();
            result.setStartPostcode(rs.getString("start_postcode"));
            result.setStartPoiDescr(rs.getString("start_poi_descr"));
            result.setStartAoiDescr(rs.getString("start_aoi_descr"));
            result.setStartStreetName(rs.getString("start_street_name"));
            result.setStartDate(rs.getTimestamp("start_date"));
            result.setEndPostcode(rs.getString("end_postcode"));
            result.setEndPoiDescr(rs.getString("end_poi_descr"));
            result.setEndAoiDescr(rs.getString("end_aoi_descr"));
            result.setEndStreetName(rs.getString("end_street_name"));
            result.setEndDate(rs.getTimestamp("end_date"));
            result.setDigitalInputName(rs.getString("digital_input_name"));
            result.setVehicleId(rs.getInt("vehicle_id"));
            result.setInputIndex(rs.getInt("input_index"));
            result.setIgnition(rs.getBoolean("is_ignition"));

            return result;
        }
    }

    public List<LocationRecord> findLocationRecordsByPeriodAndUnitId(TimePeriod timePeriod, int viewId,
                                                                     int distance, float lat, float lon) {


        Session session = getSession();
        List sqlResult = session.createSQLQuery(
                "SELECT u.registration_number AS registration_number," +
                        " ir.rec_date AS rec_date," +
                        " ir.streetname AS streetname," +
                        " ir.speed AS speed," +
                        " MIN(lat_lon_to_distance(?, ?, ir.lat, ir.lon)) as distance " +
                        " FROM incoming_log ir " +
                        " JOIN unit u on ir.unit_id = u.id " +
                        " JOIN map_unit_view_unit muvu on ir.unit_id = muvu.vehicle_id " +
                        " WHERE ir.rec_date between ? and ? " +
                        " AND muvu.group_id = ? " +
                        " AND lat_lon_to_distance(?, ?, ir.lat, ir.lon) < ? " +
                        " GROUP BY streetname, ir.unit_id " +
                        " ORDER by distance"
        ).setFloat(0, lat)
                .setFloat(1, lon)
                .setTimestamp(2, timePeriod.getStartDate())
                .setTimestamp(3, timePeriod.getEndDate())
                .setInteger(4, viewId)
                .setFloat(5, lat)
                .setFloat(6, lon)
                .setInteger(7, distance)
                .list();
        List<LocationRecord> result = new ArrayList<LocationRecord>(sqlResult.size());
        for (Object record : sqlResult) {
            Object[] recordArr = (Object[]) record;
            LocationRecord locationRecord = new LocationRecord();

            locationRecord.setVehicleReg((String) recordArr[0]);
            locationRecord.setDate((Date) recordArr[1]);
            locationRecord.setLocation((String) recordArr[2]);
            locationRecord.setSpeed((Integer) recordArr[3]);
            locationRecord.setDistance(((BigInteger) recordArr[4]).intValue());
            locationRecord.setUserId(SpContext.getUserDetailsInfo().getUserId());
            result.add(locationRecord);
        }
        return result;
    }

    public String findSevenDigitPostcode(double lat, double lon){
        //need string values for INDEX search into DB. (performance issue GSUP-81)
        final String index_lat = String.valueOf(lat).substring(0,5);
        final String index_lon = String.valueOf(lon).substring(0,5);

        List res = findNearestSevenDigitPostcode.execute(new Object[] { lat,lat,lon, index_lat, index_lon,lat,lat,lon });
        return (String)(res.size()> 0 ? res.get(0) : null);
    }

    private class FindNearetSevenDigitPostcodeQuery extends MappingSqlQuery {
        @Override
        protected Object mapRow(ResultSet resultSet, int i) throws SQLException {
            return resultSet.getString(1);
        }

        public FindNearetSevenDigitPostcodeQuery(DataSource ds) {
            super(ds,"select postcode, acos(sin(radians(?))*sin(radians(lat)) + cos(radians(?))*cos(radians(lat))*cos(radians(lon)-radians(?)))*6371 as d " +
                    "from postcode_seven_digits " +
                    "where index_lat = ? " +
                    "and " +
                    "index_lon = ? and " +
                    "acos(sin(radians(?))*sin(radians(lat)) + cos(radians(?))*cos(radians(lat))*cos(radians(lon)-radians(?)))*6371 < 0.1 " +
                    "order by d " +
                    "limit 1");
            declareParameter(new SqlParameter("lat", Types.DOUBLE));
            declareParameter(new SqlParameter("lat", Types.DOUBLE));
            declareParameter(new SqlParameter("lon", Types.DOUBLE));
            declareParameter(new SqlParameter("index_lat", Types.VARCHAR));
            declareParameter(new SqlParameter("index_lon", Types.VARCHAR));
            declareParameter(new SqlParameter("lat", Types.DOUBLE));
            declareParameter(new SqlParameter("lat", Types.DOUBLE));
            declareParameter(new SqlParameter("lon", Types.DOUBLE));
            compile();
        }
    }

}
