package com.sp.dao;

import com.sp.SpContext;
import com.sp.dto.report.TimeSheetJourneyRecord;
import com.sp.model.*;
import com.sp.util.TimePeriod;
import com.sp.util.Util;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
public class JourneyDao extends BaseDao {
    private DataSource dataSource;
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Journey> findLateStartsByPeriod(TimePeriod timePeriod, int unitViewId) {
        Session session = getSession();
        return session.createSQLQuery(
                "SELECT {j.*} " +
                        " FROM journey {j} " +
                        "  JOIN (SELECT" +
                        "           unit_id," +
                        "           min( start_date ) AS start_date" +
                        "       FROM journey jj " +
                        "          JOIN map_unit_view_unit muvu ON muvu.vehicle_id = jj.unit_id AND muvu.group_id=?" +
                        "       WHERE start_date BETWEEN ? AND ?" +
                        "       GROUP BY unit_id) s ON s.unit_id = j.unit_id AND s.start_date = j.start_date" +
                        " ORDER BY j.start_date")
                .addEntity("j", Journey.class)
                .setInteger(0, unitViewId)
                .setTimestamp(1, timePeriod.getStartDate())
                .setTimestamp(2, timePeriod.getEndDate())
                .list();
    }


    // we take journeys that are outside of the hours map
   private String getOutOfHoursQueryPart(Map<String,Map<String,Integer>> outOfHoursMap){
        if(outOfHoursMap!=null){
            List<String> prohibitWeekDayList = new ArrayList<String>();
            List<String> permittedWeekDayList = new ArrayList<String>();
            int endOfDayInSeconds = 86399; // 23:59:59
            Map<String,List<String>> timeStrWeekDayMap = new HashMap<String,List<String>>();
            for(String weekDayNumber : outOfHoursMap.keySet()){
                Map<String,Integer> timeDetails = outOfHoursMap.get(weekDayNumber);
                if(timeDetails == null){
                    prohibitWeekDayList.add(weekDayNumber);
                } else {
                    int startSeconds = timeDetails.get("start_hour") * 3600 + timeDetails.get("start_min") * 60;
                    int endSeconds = timeDetails.get("end_hour") * 3600 + timeDetails.get("end_min") * 60 + 59;
                    if(startSeconds==0 && endSeconds == endOfDayInSeconds){ //between 00:00 and 23:59
                        permittedWeekDayList.add(weekDayNumber);
                        continue;
                    }
                    String key = startSeconds + "|" + endSeconds;
                    List<String> weekDayList;
                    if(!timeStrWeekDayMap.containsKey(key)){
                        weekDayList = new ArrayList<String>();
                    }else {
                        weekDayList = timeStrWeekDayMap.get(key);
                    }
                    weekDayList.add(weekDayNumber);
                    timeStrWeekDayMap.put(key,weekDayList);
                }
            }
            StringBuilder sqlPart = new StringBuilder();
            String andOr = " AND (";
            int endBracketsCount = 0;
            if(permittedWeekDayList.size()>0){
                sqlPart.append(andOr);
                sqlPart.append(" (WEEKDAY(j.start_date) not in (");
                sqlPart.append(Util.joinListByComma(permittedWeekDayList));
                sqlPart.append(") AND WEEKDAY(j.end_date) not in (");
                sqlPart.append(Util.joinListByComma(permittedWeekDayList));
                sqlPart.append("))");
                endBracketsCount++;
            }
            if(prohibitWeekDayList.size()>0){
                sqlPart.append(andOr);
                sqlPart.append(" (WEEKDAY(j.start_date) in (");
                sqlPart.append(Util.joinListByComma(prohibitWeekDayList));
                sqlPart.append(") OR WEEKDAY(j.end_date) in (");
                sqlPart.append(Util.joinListByComma(prohibitWeekDayList));
                sqlPart.append("))");
                endBracketsCount++;
            }
            if(timeStrWeekDayMap.size()>0){
                andOr = permittedWeekDayList.size()>0 || prohibitWeekDayList.size()>0 ? " OR " : "AND ("; 
                if(!andOr.equals(" OR ")){
                    endBracketsCount++;
                }
                for(String fromToSecs : timeStrWeekDayMap.keySet()){
                    sqlPart.append(andOr);
                    String from = fromToSecs.split("\\|")[0];
                    String to = fromToSecs.split("\\|")[1];
                    sqlPart.append(" (WEEKDAY(j.start_date) in (");
                    sqlPart.append(Util.joinListByComma(timeStrWeekDayMap.get(fromToSecs)));
                    sqlPart.append(") AND (TIME_TO_SEC(j.start_date) BETWEEN 0 AND ");
                    sqlPart.append(from);
                    sqlPart.append(" OR TIME_TO_SEC(j.start_date) BETWEEN ");
                    sqlPart.append(to);
                    sqlPart.append(" AND ");
                    sqlPart.append(endOfDayInSeconds);
                    sqlPart.append(" OR TIME_TO_SEC(j.end_date) BETWEEN 0 AND ");
                    sqlPart.append(from);
                    sqlPart.append(" OR TIME_TO_SEC(j.end_date) BETWEEN ");
                    sqlPart.append(to);
                    sqlPart.append(" AND ");
                    sqlPart.append(endOfDayInSeconds);
                    sqlPart.append("))");
                    andOr = " OR";
                }
            }
            for(int i=0;i<endBracketsCount;i++){
                sqlPart.append(")");
            }
            return sqlPart.toString();
        }
        return "";
    }

    public List<Journey> findByPeriodAndUnitId(TimePeriod timePeriod,
                                               Collection<Integer> unitIdList,
                                               boolean excludeWeekends,
                                               float speedFilter,
                                               boolean removeZeroDistanceFilter,
                                               int idlingFilter,
                                               boolean onlyNegativeBehaviour,
                                               Map<String,Map<String,Integer>> outOfHoursMap,
                                               boolean driverFilter) {
        Session session = getSession();
        String queryText = "select {j.*} FROM journey {j} " +
                " where j.start_date >= ? and j.start_date <= ? " +
                " and j.end_log_id is not null" +
                " and j.end_date >= ? and j.end_date <= ? " +
                (idlingFilter > 0 ? " and j.idling > ?" : "") +
                (excludeWeekends ? " and DAYOFWEEK(j.start_date) not in (1,7) " : "") +
                (speedFilter > 0 ? " and j.max_speed > ?" : "") +
                (removeZeroDistanceFilter ? " and j.distance > 0" : "") +
                getOutOfHoursQueryPart(outOfHoursMap) +
                (onlyNegativeBehaviour ? " and (j.harsh_acceleration > 0 or j.harsh_braking > 0 or j.harsh_cornering > 0" +
                        " or j.excessive_speed > 0)" : "") +
                " and j.unit_id in (:unitIdList)" +
                (driverFilter ?  " and j.driver_id is not null order by j.driver_id, " : " order by j.unit_id, ") + " j.start_date";
        Query query = session.createSQLQuery(queryText)
                 .addEntity("j", Journey.class)
                .setTimestamp(0, timePeriod.getStartDate())
                .setTimestamp(1, timePeriod.getEndDate())
                .setTimestamp(2, timePeriod.getStartDate())
                .setTimestamp(3, timePeriod.getEndDate());
        if (speedFilter > 0) {
            query.setFloat(4, speedFilter);
        } else if (idlingFilter > 0){
            query.setInteger(4, idlingFilter);
        }
        query.setParameterList("unitIdList", unitIdList);
        return query.list();
    }

    public Integer findJourneysCountByPeriodAndUnitId(TimePeriod timePeriod,
                                               Collection<Integer> unitIdList,
                                               boolean excludeWeekends,
                                               float speedFilter,
                                               boolean removeZeroDistanceFilter) {
        Session session = getSession();
        long diffDays = Util.differenceInDays(timePeriod.getStartDate(), timePeriod.getEndDate());
        String queryText = "select count(*) FROM journey j " +
                " where j.start_date >= ? and j.start_date <= ? " +
                " and j.end_log_id is not null" +
                " and j.end_date >= ? and j.end_date <= ? " +
                " and j.unit_id in (:unitIdList)" +
                (excludeWeekends ? " and DAYOFWEEK(j.start_date) not in (1,7) " : "") +
                (speedFilter > 0 ? " and j.max_speed > ?" : "") +
                (removeZeroDistanceFilter ? " and j.distance > 0" : "") +
                " order by j.unit_id, j.start_date";
        Query query = session.createSQLQuery(queryText)
                .setTimestamp(0, timePeriod.getStartDate())
                .setTimestamp(1, timePeriod.getEndDate())
                .setTimestamp(2, timePeriod.getStartDate())
                .setTimestamp(3, timePeriod.getEndDate())
                .setParameterList("unitIdList", unitIdList);
        if (speedFilter > 0) {
            query.setFloat(4, speedFilter);
        }
        return Integer.parseInt(query.uniqueResult().toString());

    }

    @SuppressWarnings("unchecked")
    public List<Journey> findTimeOnSiteByPeriodAndUnitIdPoiId(TimePeriod timePeriod,
                                                              List<Integer> unitIdList,
                                                              Set<Integer> poiIdSet,
                                                              Set<Integer> aoiIdSet,
                                                              boolean excludePois,
                                                              boolean excludeAois,
                                                              boolean orderByLocation,
                                                              boolean orderByName) {

        Session session = getSession();
        String poiAoiString;
        if (isIgnoreSet(aoiIdSet)
                && isIgnoreSet(poiIdSet) && !excludeAois && !excludePois) {
            poiAoiString = "";
        } else if ((isIgnoreSet(aoiIdSet) || excludePois) && !excludeAois) {
            poiAoiString = " and j.end_poi_descr " + (excludePois ? " is null" : " in (select descr from poi where id in (:poiIdSet))");
        } else if ((isIgnoreSet(poiIdSet) || excludeAois) && !excludePois) {
            poiAoiString = " and j.end_aoi_descr " + (excludeAois ? " is null " : " in (select CONCAT('AOI:', descr) from aoi where id in (:aoiIdSet))");
        } else {
            poiAoiString = " and (j.end_poi_descr " +
                    (excludePois ? " is null" :  " in (select descr from poi where id in (:poiIdSet)) ") +
                    (excludePois && excludeAois ? " and" : " or")   +
                  " j.end_aoi_descr " + (excludeAois ? " is null)" : " in (select CONCAT('AOI:', descr) from aoi where id in (:aoiIdSet))) ");
        }
        String queryText = "select {j.*} FROM journey {j} " +
                (orderByName ? " join unit u on u.id = j.unit_id" : "") +
                " where j.end_date between ? and  ? " +
                " and j.unit_id in (:unitIdList)" +
                 poiAoiString +
                " order by " +
                (orderByName ? " u.registration_number," : "")
                + (orderByLocation ? " end_aoi_descr, end_poi_descr, end_street_name, end_postcode, end_lat, end_lon," : "")
                + " unit_id, j.end_date ";
        Query query = session.createSQLQuery(queryText)
                 .addEntity("j", Journey.class)
                .setTimestamp(0, timePeriod.getStartDate())
                .setTimestamp(1, timePeriod.getEndDate())
                .setParameterList("unitIdList", unitIdList);
        if (!isIgnoreSet(aoiIdSet) && !excludeAois) {
            query.setParameterList("aoiIdSet", aoiIdSet);
        }
        if (!isIgnoreSet(poiIdSet) && !excludePois) {
            query.setParameterList("poiIdSet", poiIdSet);
        }

        return query.list();


       }

     private boolean isIgnoreSet(Set set) {
        return set == null || set.size() == 0;
     }

    public List<Vehicle> findNonReportingVehicleList(String repType) {
        Session session = getSession();
        return session.createSQLQuery(
                "SELECT {t.*} " +
                        " FROM unit {t} " +
                        " JOIN map_unit_account mua ON mua.unit_id=t.id" +
                        " JOIN map_user_account musa ON musa.account_id=mua.account_id AND musa.user_id=?" +
                        (repType.equals("NonJourney") ?
                                " WHERE not exists (select null" +
                                        " from journey j" +
                                        " where j.`end_date` > DATE_SUB(now(), INTERVAL 3 DAY) " +
                                        "  and j.unit_id = t.id)"
                                : " WHERE t.`renewal_date` < (now() - interval 4 hour)") +
                        " and t.is_deleted = 0"
        )
                .addEntity("t", Vehicle.class)
                .setInteger(0, SpContext.getUserDetailsInfo().getUserId())
                .list();
    }

    public Journey findCurJorneyByUnitId(int unitId) {
        Session session = getSession();
        return (Journey) session.createSQLQuery(
                "SELECT {j.*} FROM journey {j} " +
                        " WHERE j.end_log_id IS NULL AND j.start_date = (SELECT MAX(start_date) FROM journey WHERE unit_id = ?)" +
                        " AND j.unit_id = ? " +
                        " LIMIT 1")
                .addEntity("j", Journey.class)
                .setInteger(0, unitId)
                .setInteger(1, unitId)
                .uniqueResult();
    }

    public List<MileageRecord> findMileageRecordsByPeriodAndUnitId(TimePeriod timePeriod,
                                                                   List<Integer> unitIdList,
                                                                   boolean excludeWeekends) {
        Session session = getSession();
        String paramsStr = Util.constructSqlINClause(unitIdList);

        List sqlResult = session.createSQLQuery(
                "  SELECT j.unit_id AS unit_id," +
                        "    cast(j.start_date as date) AS day," +
                        "    sum(j.distance) AS distance," +
                        "    CONCAT(su.first_name, ' ', su.last_name) as name" +
                        " FROM journey j " +
                        " LEFT JOIN security_user su on j.driver_id = su.id " +
                        " WHERE j.start_date BETWEEN ? AND ? " +
                        (excludeWeekends ? " AND DAYOFWEEK(j.start_date) NOT IN (1,7) " : "") +
                        " AND j.unit_id IN (" + paramsStr + ")" +
                        " GROUP BY j.unit_id, cast(j.start_date as date) , name")
                .setTimestamp(0, timePeriod.getStartDate())
                .setTimestamp(1, timePeriod.getEndDate())
                .list();
        List<MileageRecord> result = new ArrayList<MileageRecord>(sqlResult.size());
        for (Object record : sqlResult) {
            Object[] recordArr = (Object[]) record;
            MileageRecord mileageRecord = new MileageRecord();
            mileageRecord.setVehicleId((Integer) recordArr[0]);
            mileageRecord.setDay((Date) recordArr[1]);
            mileageRecord.setDistance(Math.round(((BigDecimal) recordArr[2]).intValue() * SpContext.getUserDetailsInfo().getUserPrefs().getDistanceMetricFactor() / 10) / 100f);
            mileageRecord.setDriver((String) recordArr[3]);
            mileageRecord.setVehReg(SpContext.getCachedVehicleById(mileageRecord.getVehicleId()).getRegistrationNumber());
            result.add(mileageRecord);
        }

        return result;
    }

    

    public List<DailyLogRecordDto> findDailyRecordsByPeriodAndUnitId(TimePeriod timePeriod,
                                                                  List<Integer> unitIdList,
                                                                  boolean excludeWeekends) {
        Session session = getSession();
        String paramsStr = Util.constructSqlINClause(unitIdList);

        List sqlResult = session.createSQLQuery(
                "  SELECT j.unit_id AS unit_id," +
                        "    cast(j.start_date as date) AS day," +
                        "    min(j.start_date) AS start_date," +
                        "    max(j.end_date) AS end_date," +
                        "    sum((time_to_sec(j.end_date) - time_to_sec(j.start_date))) AS driving_time," +
                        "    count(0) AS journey_count " +
                        " FROM journey j " +
                        " WHERE j.start_date BETWEEN ? AND ? " +
                        (excludeWeekends ? " AND DAYOFWEEK(j.start_date) NOT IN (1,7) " : "") +
                        " AND j.unit_id IN (" + paramsStr + ")" +
                        " GROUP BY j.unit_id, cast(j.start_date as date) " +
                        " ORDER BY unit_id, start_date")
                .setTimestamp(0, timePeriod.getStartDate())
                .setTimestamp(1, timePeriod.getEndDate())
                .list();
        List<DailyLogRecordDto> result = new ArrayList<DailyLogRecordDto>(sqlResult.size());
        for (Object record : sqlResult) {
            Object[] recordArr = (Object[]) record;
            DailyLogRecordDto dailyLogRecordDto = new DailyLogRecordDto();
            Integer unitIdInt = (Integer) recordArr[0];
            BaseTrackingDevice trackingDevice = SpContext.getCachedDeviceByVehicleId(unitIdInt);
            dailyLogRecordDto.setVehicle((BaseVehicle) findById(BaseVehicle.class, unitIdInt));
            dailyLogRecordDto.setDay((Date) recordArr[1]);
            dailyLogRecordDto.setStartDate(Util.getDaylightTimeUK((Date) recordArr[2], trackingDevice));
            dailyLogRecordDto.setEndDate(Util.getDaylightTimeUK((Date) recordArr[3], trackingDevice));
            dailyLogRecordDto.setDrivingTime(((BigDecimal) recordArr[4]).intValue());
            dailyLogRecordDto.setJourneyCount(((BigInteger) recordArr[5]).intValue());

            result.add(dailyLogRecordDto);
        }

        return result;
    }

    public Date findDepartureDate(int unitId,
                                  Date arrivalDate,
                                  Date maxDepartureDate,
                                  String streetName) {
        Session session = getSession();
        return (Date) session.createSQLQuery(
                "SELECT min( `d`.`start_date` )" +
                        " FROM journey `d` " +
                        " WHERE d.`unit_id` = ? " +
                        " AND d.`start_date` BETWEEN ? AND ?" +
                        " AND d.`start_street_name` = ? ")
                .setInteger(0, unitId)
                .setTimestamp(1, arrivalDate)
                .setTimestamp(2, maxDepartureDate)
                .setString(3, streetName)
                .uniqueResult();
    }

    public List<TimeSheetJourneyRecord> findTimeSheetByDriverAndPeriod(TimePeriod timePeriod, int driverId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        Object[] params = {
                timePeriod.getStartDate(),
                timePeriod.getEndDate(),
                timePeriod.getEndDate(),
                driverId
        };
        String sql = "select  u.registration_number," +
                " u.id as unit_id," +
                " date(j.start_date) as date," +
                " min(j.start_date) as start_date," +
                " max(j.end_date) as end_date," +
                " TIME_TO_SEC(TIMEDIFF(max(j.end_date), min(j.start_date))) as duration," +
                " SUM(TIME_TO_SEC(TIMEDIFF(j.end_date, j.start_date))) as travelled_duration," +
                " TIME_TO_SEC(TIMEDIFF(TIMEDIFF(max(j.end_date), min(j.start_date)), SEC_TO_TIME(SUM(TIME_TO_SEC(TIMEDIFF(j.end_date, j.start_date)))))) as stopped_duration," +
                " sum(j.distance) as travelled_distance" +
                " from journey j " +
                " join unit u on j.unit_id = u.id " +
                " where j.start_date > ? and j.end_date < ? " +
                " and (u.delete_date is null or u.delete_date > ?) " +
                " and j.driver_id = ? "  +
                " GROUP by u.registration_number, date(j.start_date)" +
                " order by u.registration_number, date(j.start_date) asc";
        return jdbcTemplate.query(sql,
                                params,
                                new TimeSheetJourneyRecordRowMapper());
    }

    public List<TimeSheetJourneyRecord> findTimeSheetByUnitViewIdAndPeriod(TimePeriod timePeriod, int groupId, int vehicleId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        Object[] params = {
                vehicleId == 0 ? groupId : vehicleId,
                timePeriod.getStartDate(),
                timePeriod.getEndDate(),
                timePeriod.getEndDate()
        };
        String sql = "select  u.registration_number," +
                " u.id as unit_id," +
                " date(j.start_date) as date," +
                " min(j.start_date) as start_date," +
                " max(j.end_date) as end_date," +
                " TIME_TO_SEC(TIMEDIFF(max(j.end_date), min(j.start_date))) as duration," +
                " SUM(TIME_TO_SEC(TIMEDIFF(j.end_date, j.start_date))) as travelled_duration," +
                " TIME_TO_SEC(TIMEDIFF(TIMEDIFF(max(j.end_date), min(j.start_date)), SEC_TO_TIME(SUM(TIME_TO_SEC(TIMEDIFF(j.end_date, j.start_date)))))) as stopped_duration," +
                " sum(j.distance) as travelled_distance" +
                " from journey j " +
                " join unit u on j.unit_id = u.id ";
        sql += vehicleId == 0
                ? " join map_unit_view_unit muvu on muvu.vehicle_id = u.id and muvu.group_id = ? "
                : " and u.id = ?";
        sql += " where j.start_date > ? and j.end_date < ? " +
                " and (u.delete_date is null or u.delete_date > ?) "  +
                " GROUP by u.registration_number, date(j.start_date)" +
                " order by u.registration_number, date(j.start_date) asc";
        return jdbcTemplate.query(sql,
                                params,
                                new TimeSheetJourneyRecordRowMapper());
    }

    private class TimeSheetJourneyRecordRowMapper implements RowMapper {
        @Override
        public Object mapRow(ResultSet rs, int i) throws SQLException {
            TimeSheetJourneyRecord result = new TimeSheetJourneyRecord();
            result.setVehReg(rs.getString("registration_number"));
            result.setDate(rs.getTimestamp("date"));
            result.setUnitId(rs.getInt("unit_id"));
            result.setStartTime(rs.getTimestamp("start_date"));

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(result.getStartTime());

            result.setWeekday(new DateFormatSymbols(Locale.ENGLISH).getWeekdays()[calendar.get(Calendar.DAY_OF_WEEK)]);

            result.setEndTime(rs.getTimestamp("end_date"));

            result.setDuration(rs.getInt("duration") / 60);
            result.setTravelledDuration(rs.getInt("travelled_duration") / 60);
            result.setStoppedDuration(rs.getInt("stopped_duration") / 60);

            result.setTravelledDistance(rs.getInt("travelled_distance"));

            return result;
        }
    }

    public List<TimeSheetJourneyRecord> findTimeSheetByHandheldViewIdAndPeriod(TimePeriod timePeriod, int groupId) throws ParseException {
       JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        Object[] params = {
                groupId,
                timePeriod.getStartDate(),
                timePeriod.getEndDate(),
        };
        //todo: journey table does not have any relations to handheld table, this sql is incorrect
        String sql = "select  h.descr," +
                " date(j.start_date) as date," +
                " min(j.start_date) as start_date," +
                " max(j.end_date) as end_date," +
                " TIME_TO_SEC(TIMEDIFF(max(j.end_date), min(j.start_date))) as duration," +
                " SUM(TIME_TO_SEC(TIMEDIFF(j.end_date, j.start_date))) as travelled_duration," +
                " TIME_TO_SEC(TIMEDIFF(TIMEDIFF(max(j.end_date), min(j.start_date)), SEC_TO_TIME(SUM(TIME_TO_SEC(TIMEDIFF(j.end_date, j.start_date)))))) as stopped_duration," +
                " sum(j.distance) as travelled_distance" +
                " from journey j " +
                " join handheld h on j.unit_id = h.id " +
                " join map_handheld_view mhv on mhv.unit_id = h.id and mhv.group_id = ? ";
        sql += " where j.start_date > ? and j.end_date < ? " +
                " and (h.is_deleted=0) "  +
                " GROUP by h.descr, date(j.start_date)" +
                " order by h.descr, date(j.start_date) asc";
        return jdbcTemplate.query(sql,
                                params,
                                new RowMapper() {
                                    @Override
                                    public Object mapRow(ResultSet rs, int i) throws SQLException {
                                        TimeSheetJourneyRecord result = new TimeSheetJourneyRecord();
                                        result.setVehReg(rs.getString("desc"));
                                        result.setDate(rs.getTimestamp("date"));
                                        result.setStartTime(rs.getTimestamp("start_date"));

                                        Calendar calendar = Calendar.getInstance();
                                        calendar.setTime(result.getStartTime());

                                        result.setWeekday(new DateFormatSymbols(Locale.ENGLISH).getWeekdays()[calendar.get(Calendar.DAY_OF_WEEK)]);

                                        result.setEndTime(rs.getTimestamp("end_date"));

                                        result.setDuration(rs.getInt("duration") / 60);
                                        result.setTravelledDuration(rs.getInt("travelled_duration") / 60);
                                        result.setStoppedDuration(rs.getInt("stopped_duration") / 60);

                                        result.setTravelledDistance(rs.getInt("travelled_distance"));

                                        return result;
                                    }
                                });
    }
}
