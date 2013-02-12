package com.sp.dao;

import com.sp.SpContext;
import com.sp.dao.transformer.SearchResultTransformer;
import com.sp.dao.transformer.VehicleMobileTransformer;
import com.sp.dto.SearchResultDto;
import com.sp.dto.flex.VehicleNVDto;
import com.sp.dto.mobile.VehicleMobile;
import com.sp.dto.report.FleetUtilisationRecord;
import com.sp.dto.report.UtilisationReportRecord;
import com.sp.model.BaseVehicle;
import com.sp.model.LightVehicle;
import com.sp.model.MapUnitAccount;
import com.sp.model.Vehicle;
import com.sp.util.Constants;
import com.sp.util.TimePeriod;
import com.sp.util.Util;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: andrey
 */
public class VehicleDao extends BaseDao {

    private DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Set<Integer> findVehicleIdSetByAccountId(int accountId){
        Session session = getSession();
        Query query = session.createSQLQuery(
                "select unit_id from map_unit_account m" +
                        " join unit u on u.id = m.unit_id" +
                        " where account_id = ? and u.is_deleted = 0");
        return new HashSet<Integer>(query.setInteger(0,accountId).list());
    }

    public Set<Integer> findVehicleIdSetByGroupId(int groupId){
        Session session = getSession();
        Query query = session.createSQLQuery(
                "select vehicle_id from map_unit_view_unit m" +
                        " join unit u on u.id = m.vehicle_id" +
                        " where group_id = ? and u.is_deleted = 0");
        return new HashSet<Integer>(query.setInteger(0,groupId).list());
    }

    public List<Vehicle> findBySearchStr(String searchStr, boolean includeDeleted) {
        Session session = getSession();
        Criteria c = session.createCriteria(Vehicle.class);

        if (searchStr != null && searchStr.length() > 0) {
            c.add(Restrictions.ilike("registrationNumber", "%" + searchStr + "%"));
        }

        if (!includeDeleted) {
            c.add(Restrictions.eq("deleted", false));
        }

        c.addOrder(Order.asc("id"));
        return (List<Vehicle>) c.list();
    }


    public List<SearchResultDto> findBySearchStr(String searchStr, List restrictedVehicleIds, Integer userId) {
        Session session = getSession();
        Query query = session.createSQLQuery("select {vehicle.*}, " +
                "v_group.id as groupId, " +
                "v_group.descr as groupName, " +
                "v_group.notes as information, " +
                "acc.descr as accountName, " +
                "acc.id as accountId " +
                "from unit          {vehicle} " +
                "inner join map_unit_view_unit muvu on muvu.vehicle_id = vehicle.id " +
                "inner join unit_view     v_group on muvu.group_id = v_group.id " +
                "inner join account       acc     on (v_group.account_id = acc.id) " +
                (userId != null ? "inner join map_user_account muau ON muau.account_id = acc.id AND muau.user_id = :userId  " : " ") +
                "inner join reseller ru on ru.id = acc.reseller_id " +
                "inner join imei i on i.unit_id = vehicle.id " +


                "where vehicle.is_deleted = 0 and  muvu.group_id in (select id from unit_view where account_id = v_group.account_id) and acc.is_deleted = 0 and ru.is_deleted = 0 and i.is_deleted = 0 and ((vehicle.registration_number_no_spaces like :searchStr) or (vehicle.vin_no_spaces like :searchStr)) and vehicle.id not IN(:restricted_vehicle_ids) ").
                                                    addEntity("vehicle", Vehicle.class).
                                                    addScalar("groupId").
                                                    addScalar("groupName").
                                                    addScalar("information").
                                                    addScalar("accountName").
                                                    addScalar("accountId").
                                                    setResultTransformer(new SearchResultTransformer());
        if (userId != null) {
            query.setInteger("userId", userId);
        }
        return query.setString("searchStr", searchStr).setParameterList("restricted_vehicle_ids", restrictedVehicleIds) .list();
    }

    public void markActiveInactive(int id, boolean active) {
        Session session = getSession();
        Query query = session.createSQLQuery("UPDATE unit" +
                " SET is_deleted=" + (active ? "0" : "1") +
                " , delete_date = " + (active ? null : " NOW() ")
                + " WHERE id=?").setInteger(0, id);
        query.executeUpdate();
    }

    public List<Vehicle> findNonExpiredByUnitViewIdRefreshed(int unitViewId,
                                                             Date refreshDate,
                                                             String repType,
                                                             String restrictedVehicles) {
        Session session = getSession();
        Query query = session.createSQLQuery(
                "SELECT {u.*} " +
                " FROM unit {u} " +
                        " JOIN map_unit_view_unit muvu ON muvu.vehicle_id = u.id " +
                        " JOIN imei i ON i.unit_id = u.id " +
                        " JOIN license l ON l.id = i.license_id " +
                " WHERE muvu.group_id=? AND u.is_deleted=0 " +
                 " AND CURDATE() BETWEEN l.activation_date AND l.end_date" +
                 (refreshDate == null ? "" : (" AND (u.timestamp >= ? OR " +
                         "(i.box_type_id =" + Constants.CALAMP_3000_BOX_TYPE_ID + " and u.timestamp < ? ))" )) +
                 (Constants.ReportType.BusinessPrivate.toString().equals(repType) ? " AND i.stealth_mode_input_number > 0" : "") +
                 (restrictedVehicles.length() > 0 ? " AND u.id NOT IN (" + restrictedVehicles + ")" : ""))
                .addEntity("u", Vehicle.class);

        query.setInteger(0, unitViewId);
        if (refreshDate != null) {
            query.setTimestamp(1, refreshDate);
            query.setTimestamp(2, new Date(Util.getCalampUnplaggedDelayTime()));
        }
        return query.list();
    }

    public List<Vehicle> findForUserId(int userId, Boolean active, String restrictedVehicles) {
        Session session = getSession();

        Query query = session.createSQLQuery(
                "SELECT {u.*} " +
                " FROM unit {u} " +
                    " JOIN map_unit_account muacc ON muacc.unit_id = u.id " +
                    " JOIN map_user_account mua ON mua.account_id = muacc.account_id AND mua.user_id = ?" +
                (active == null ? "" : " WHERE is_deleted=" + (active ? "0" : "1")) +
                (restrictedVehicles.length() > 0 ? " AND u.id NOT IN (" + restrictedVehicles + ")" : ""))
                .addEntity("u", Vehicle.class);

        return query.setInteger(0, userId).list();
    }

    public List<Vehicle> findForUserId(int userId, Boolean active) {
       return findForUserId(userId, active, "");
    }



    public List<Vehicle> findByAccountIdWithCabPhone(Integer accountId, int unitId, Integer userId) {
        Session session = getSession();
        Query query = session.createSQLQuery(
                " SELECT {u.*} " +
                " FROM unit {u} " +
                " JOIN map_unit_account muacc ON muacc.unit_id = u.id " +
                (accountId==null && userId != null ? " JOIN map_user_account mua ON mua.account_id = muacc.account_id AND mua.user_id = :userId" : "") +
                " WHERE is_deleted = 0 " +
                (accountId!=null ? " and muacc.account_id = :accountId" : "") +
                " and trim(ifnull(u.cab_phone,'')) <> '' and u.id <> :unitId")
                .addEntity("u", Vehicle.class);
        if(accountId!=null){
            query.setInteger("accountId",accountId);
        }else if(userId != null){
            query.setInteger("userId",userId);
        }
        return query.setInteger("unitId", unitId).list();
    }

    public List<MapUnitAccount> findMapVehicleAccountsByVehicleId(int vehicleId) {
        Session session = getSession();
        return session.createQuery("from com.sp.model.MapUnitAccount as obj where obj.vrehicleId = ?")
                .setInteger(0, vehicleId).list();
    }

    public List<Vehicle> findVehicleListByCabPhones(Set<String> cabPhones){
        Criteria c = getSession().createCriteria(Vehicle.class);
        c.add(Restrictions.in("cabPhone", cabPhones));
        return c.list();
    }

    public Vehicle findByCabPhone(String cabPhone){
        Criteria c = getSession().createCriteria(Vehicle.class);
        c.add(Restrictions.eq("cabPhone", cabPhone));
        return (Vehicle)c.uniqueResult();
    }

    public List<Vehicle> findAlertVehiclesOptionsByUser(Integer userId, int alertId) {
        Session session = getSession();
        String sql = "select distinct {v.*} from unit {v}";
        if (userId != null) {
                sql += " join map_unit_account muacc on muacc.unit_id = v.id " +
                " join map_user_account mua on mua.account_id = muacc.account_id AND mua.user_id = ?";
        }
        sql += " where v.is_deleted = 0 and  v.id not in (select vehicle_id from map_alert_vehicle where alert_id = ?)";

        Query query = session.createSQLQuery(sql).addEntity("v", Vehicle.class);
        int alertParamIndex = 0;
        if (userId != null) {
            query = query.setInteger(0, userId);
            alertParamIndex = 1;
        }
        return (List<Vehicle>) query.setInteger(alertParamIndex, alertId).list();
    }

    public List<Vehicle> findByUnitViewIdAndDeviceTypeId(int unitViewId,
                                                                 Boolean active, int deviceTypeId
                                                                            , String restrictedVehicles) {
        Query query = getSession().createSQLQuery(
               "SELECT {u.*}" +
                       " FROM unit {u}" +
                       " JOIN map_unit_view_unit muvu ON muvu.vehicle_id = u.id " +
                       " JOIN imei i ON i.unit_id = u.id " +
                       " WHERE muvu.group_id=? AND i.is_can_enabled=1 AND" +
                       (active == null ? "" : " u.is_deleted=" + (active ? "0" : "1") + " AND ") +
                       "i.box_type_id=?"
                +
                 (restrictedVehicles.length() > 0 ? " AND u.id NOT IN (" + restrictedVehicles + ")" : "")).addEntity("u", Vehicle.class);
        return query.setInteger(0, unitViewId).setInteger(1, deviceTypeId).list();
    }

    public Vehicle findByRegistrationNumber(String registrationNumber) {
        Criteria criteria = getSession().createCriteria(Vehicle.class);
        criteria.add(Restrictions.eq("registrationNumber", registrationNumber));
        return (Vehicle) criteria.uniqueResult();
    }

    public MapUnitAccount findMapByAccountAndVehicleIds(MapUnitAccount mua){
        Session session = getSession();
        Query query = session.createSQLQuery("select {mua.*} from map_unit_account {mua} where account_id = ? and unit_id = ?").addEntity("mua",MapUnitAccount.class);
        return (MapUnitAccount)query.setInteger(0,mua.getAccountId()).setInteger(1,mua.getVrehicleId()).uniqueResult();
    }

    public List<BaseVehicle> findByTimestamp(Date date) {
        Criteria criteria = getSession().createCriteria(BaseVehicle.class);
        criteria.add(Restrictions.gt("timestamp", date));

        return criteria.list();
    }

    public List<LightVehicle> findWithImmobilisationDate(){
        Criteria criteria = getSession().createCriteria(Vehicle.class);
        criteria.add(Restrictions.isNotNull("immobilizationDate"));
        criteria.addOrder(Order.asc("immobilizationDate"));
        return criteria.list();
    }

    public List<FleetUtilisationRecord> findFleetUtilisationRecordList(int accountId, TimePeriod timePeriod){
        MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("toDate", timePeriod.getEndDate());
		parameters.addValue("fromDate",timePeriod.getStartDate());
        parameters.addValue("accountId",accountId);

        String sql= " SELECT u.registration_number as reg_num," +
                    "       GROUP_CONCAT(distinct uv.descr SEPARATOR '/') as group_names," +
                    "       IFNULL(IFNULL(CONCAT(sucd.first_name,' ',sucd.last_name),CONCAT(surd.first_name,' ',surd.last_name)),'-') as vehicle_driver_descr," +
                    "       COUNT(distinct j.id) as journey_count," +
                    "       (SUM(j.distance) * 0.000621371192) / (select COUNT(*) from map_unit_view_unit muvu where muvu.vehicle_id = j.unit_id) AS miles_distance," +
                    "       SUM(TIME_TO_SEC(TIMEDIFF(j.end_date, j.start_date))) / (select COUNT(*) from map_unit_view_unit muvu where muvu.vehicle_id = j.unit_id) as total_time," +
                    "       SUM(j.idling / (select COUNT(*) from map_unit_view_unit muvu where muvu.vehicle_id = j.unit_id)) as total_idling," +
                    "       SUM(TIME_TO_SEC(TIMEDIFF(j.end_date, j.start_date)) - j.idling) / (select COUNT(*) from map_unit_view_unit muvu where muvu.vehicle_id = j.unit_id) as total_driving_time," +
                    "       DATEDIFF(" +
                    "                case when IFNULL(DATEDIFF(u.last_use_date,:toDate),0) >= 0" +
                    "                     then :toDate" +
                    "                     else u.last_use_date" +
                    "                end" +
                    "                ," +
                    "                case when IFNULL(DATEDIFF(u.first_use_date,:fromDate),0) <= 0" +
                    "                     then :fromDate" +
                    "                     else u.first_use_date" +
                    "                end" +
                    "                " +
                    "       ) as active_days," +
                    "        case when IFNULL(DATEDIFF(u.last_use_date,:toDate),0) >= 0" +
                    "                     then :toDate" +
                    "                     else u.last_use_date" +
                    "                end as end_date," +
                    "                case when IFNULL(DATEDIFF(u.first_use_date,:fromDate),0) <= 0" +
                    "                     then :fromDate" +
                    "                     else u.first_use_date" +
                    "                end as start_date" +
                    " FROM journey j " +
                    " JOIN map_unit_account mua on mua.unit_id = j.unit_id" +
                    " JOIN map_unit_view_unit mug on mug.vehicle_id = j.unit_id" +
                    " JOIN unit_view uv on uv.id = mug.group_id" +
                    " JOIN unit u on u.id = j.unit_id" +
                    " LEFT JOIN security_user sucd on sucd.id = u.current_driver_id" +
                    " LEFT JOIN security_user surd on surd.id = u.associated_regular_driver_id" +
                    " WHERE j.start_date BETWEEN" +
                    "	    CASE WHEN IFNULL(DATEDIFF(u.first_use_date,:fromDate),0) <= 0" +
                    "        THEN :fromDate" +
                    "        ELSE u.first_use_date" +
                    "	    END" +
                    "	AND" +
                    "	    CASE WHEN IFNULL(DATEDIFF(u.last_use_date,:toDate),0) >= 0" +
                    "        THEN :toDate" +
                    "        ELSE u.last_use_date" +
                    "	    END" +
                    "	AND mua.account_id = :accountId" +
                    " GROUP BY j.unit_id";
        return new NamedParameterJdbcTemplate(new JdbcTemplate(dataSource)).query(sql,parameters,new ParameterizedRowMapper<FleetUtilisationRecord>() {
             public FleetUtilisationRecord mapRow(ResultSet rs, int row) throws SQLException {
                FleetUtilisationRecord r = new FleetUtilisationRecord();
                r.setActiveDays(rs.getInt("active_days") + 1);
                r.setTotalDrivingTimeSecs(rs.getLong("total_driving_time"));
                r.setTotalIdlingSecs(rs.getLong("total_idling"));
                r.setTotalTimeSecs(rs.getLong("total_time"));
                r.setJourneysCount(rs.getInt("journey_count")); 
                r.setDriverDescr(rs.getString("vehicle_driver_descr"));
                r.setMileage(rs.getDouble("miles_distance"));
                r.setVehGroups(rs.getString("group_names"));
                r.setVehReg(rs.getString("reg_num")); 
                r.setTimePeriod(new TimePeriod(rs.getDate("start_date"), Util.getEndOfDay(rs.getDate("end_date"))));
                return r;
            }
        });
    }

    public List<UtilisationReportRecord> findUtilisationReportRecordList(int notUsedDays) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        Object[] params = new Object[SpContext.getUserDetailsInfo().isUserAdmin() ? 1 : 2];
        String sql;
        if(SpContext.getUserDetailsInfo().isUserAdmin()){
            params[0] = notUsedDays;
            sql = "SELECT GROUP_CONCAT(a.descr order by a.descr asc) as accounts,t.id, t.registration_number," +
                    " jj.end_street_name,jj.end_postcode,jj.end_poi_descr,jj.end_aoi_descr,jj.end_date," +
                    " IFNULL(IFNULL(CONCAT(sucd.first_name,' ',sucd.last_name),CONCAT(surd.first_name,' ',surd.last_name)),'-') as vehicle_driver_descr," +
                    " DATEDIFF(now(),jj.end_date) as elapsed" +
                    " FROM unit t" +
                    " JOIN map_unit_account mua ON mua.unit_id=t.id" +
                    " JOIN account a ON mua.account_id = a.id" +
                    " join journey jj on jj.unit_id = t.id" +
                    " left join security_user sucd on sucd.id = t.current_driver_id" +
                    " left join security_user surd on surd.id = t.associated_regular_driver_id" +
                    " where jj.end_date < DATE_SUB(now(), INTERVAL ? DAY)" +
                    " and jj.id = (select max(j2.id) from journey j2" +
                    " where j2.unit_id = t.id)" +
                    " and t.is_deleted = 0" +
                    " group by t.id" +
                    " order by elapsed desc";
        }else{
            params[0] = SpContext.getUserDetailsInfo().getUserId();
            params[1] = notUsedDays;
            sql = "SELECT GROUP_CONCAT(a.descr order by a.descr asc)  as accounts,t.id, t.registration_number," +
                           " jj.end_street_name,jj.end_postcode,jj.end_poi_descr,jj.end_aoi_descr,jj.end_date," +
                           " IFNULL(IFNULL(CONCAT(sucd.first_name,' ',sucd.last_name),CONCAT(surd.first_name,' ',surd.last_name)),'-') as vehicle_driver_descr," +
                           " DATEDIFF(now(),jj.end_date) as elapsed" +
                           " FROM unit t" +
                           " JOIN map_unit_account mua ON mua.unit_id=t.id" +
                           " JOIN map_user_account musa ON musa.account_id=mua.account_id AND musa.user_id=?" +
                           " JOIN account a ON musa.account_id = a.id" +
                           " join journey jj on jj.unit_id = t.id" +
                           " left join security_user sucd on sucd.id = t.current_driver_id" +
                           " left join security_user surd on surd.id = t.associated_regular_driver_id" +
                           " where jj.end_date < DATE_SUB(now(), INTERVAL ? DAY)" +
                           " and jj.id = (select max(j2.id) from journey j2" +
                           " where j2.unit_id = t.id)" +
                           " and t.is_deleted = 0" +
                           " group by t.id" +
                           " order by elapsed desc";
        }
        return jdbcTemplate.query(sql,
                                params,
                                new RowMapper() {
                                    @Override
                                    public Object mapRow(ResultSet rs, int i) throws SQLException {
                                        UtilisationReportRecord result = new UtilisationReportRecord();
                                        result.setVehicleId(rs.getInt("id"));
                                        result.setAccountNames(rs.getString("accounts"));
                                        result.setRegNumber(rs.getString("registration_number"));
                                        result.setElapsedDays(rs.getInt("elapsed"));
                                        result.setLastJourneyEndDate(rs.getTimestamp("end_date"));
                                        result.setLocationPostcode(Util.getJoinedStrs(rs.getString("end_street_name"), rs.getString("end_postcode")));
                                        result.setPoiAoi(Util.getJoinedStrs(rs.getString("end_poi_descr"), rs.getString("end_aoi_descr")));
                                        result.setVehicleDriverDescr(rs.getString("vehicle_driver_descr"));
                                        return result;
                                    }
                                });
    }

    /**
     * Returns list of units permitted for user connected to system by REST service.
     * Also returns string of unit view ids for unit
     * @param userId
     * @param active
     * @param restrictedVehicles
     * @return
     */
    public List<VehicleMobile> findForUserMobile(int userId, Boolean active, String restrictedVehicles) {
        Session session = getSession();

        Query query = session.createSQLQuery(
                "SELECT {u.*}, GROUP_CONCAT(distinct cast(muvu.group_id as char) SEPARATOR ',') as unitViewIds " +
                        " FROM unit {u} " +
                        " JOIN map_unit_account muacc ON muacc.unit_id = u.id " +
                        " JOIN map_user_account mua ON mua.account_id = muacc.account_id " +
                        (SpContext.getUserDetailsInfo().isUserAdmin() ? " " : " AND mua.user_id = ? ") +
                        " JOIN account a on a.id = mua.account_id " +
                        " JOIN map_unit_view_unit muvu on muvu.vehicle_id = u.id " +
                        " JOIN imei i ON i.unit_id = u.id " +
                        " JOIN license l ON l.id = i.license_id " +
                        (active == null ? "" : " WHERE u.is_deleted=" + (active ? "0" : "1")) +
                        " AND CURDATE() BETWEEN l.activation_date AND l.end_date" +
                        (SpContext.getUserDetailsInfo().isUserAdmin() ? " " : " AND a.is_ios_application_allowed = 1  ") +
                        " AND a.is_deleted = 0 " +
                        (restrictedVehicles.length() > 0 ? " AND u.id NOT IN (" + restrictedVehicles + ")" : " ") +
                        " GROUP BY muvu.vehicle_id").addEntity("u", Vehicle.class).addScalar("unitViewIds").setResultTransformer(new VehicleMobileTransformer());
        if (!SpContext.getUserDetailsInfo().isUserAdmin()) {
            query.setInteger(0, userId);
        }
        return query.list();
    }

    public void updateNetworkVehicles(List<Integer> vehicles, boolean networkActive, boolean networkArchived) {
        Session session = getSession();
        Query query = session.createSQLQuery("UPDATE unit" +
                " SET is_network_archived=" + (networkArchived ? "1" : "0") +
                " , is_network_active = " + (networkActive ? "1" : "0")
                + " WHERE id IN (:idList)").setParameterList("idList", vehicles);
        query.executeUpdate();
    }

    public List<VehicleNVDto> getAllNetworkVehicles(int anPostAccountId) {
        Session session = getSession();
        String s = "SELECT " +
                "u.id as id, " +
                "u.descr as description, " +
                "ut.descr as type, " +
                "u.registration_number as registrationNumber, " +
                "u.fleet_id as fleet,  " +
                "u.is_network_active as networkActive, " +
                "u.is_network_archived as networkArchived, " +
                "u.is_deleted as deleted, " +
                "'' as vehicleBase "+
                "FROM unit u " +
                " JOIN map_unit_account muacc ON " +
                "muacc.unit_id = u.id AND  " +
                "(NOT u.is_deleted OR u.is_network_archived) AND " +
                "muacc.account_id = ? JOIN unit_type ut on ut.id = u.type_id";
        Query query = session.createSQLQuery(s).
                addScalar("id", Hibernate.INTEGER).
                addScalar("description", Hibernate.STRING).
                addScalar("registrationNumber", Hibernate.STRING).
                addScalar("type", Hibernate.STRING).
                addScalar("fleet", Hibernate.STRING).
                addScalar("vehicleBase", Hibernate.STRING).
                addScalar("networkActive", Hibernate.BOOLEAN).
                addScalar("networkArchived", Hibernate.BOOLEAN).
                addScalar("deleted", Hibernate.BOOLEAN).
                setResultTransformer(Transformers.aliasToBean(VehicleNVDto.class));

        return query.setInteger(0, anPostAccountId).list();
    }
    public List<VehicleNVDto> getAvailableNetworkVehicles(int anPostAccountId) {

        Session session = getSession();
        String s = "SELECT " +
                "u.id as id, " +
                "u.descr as description, " +
                "ut.descr as type, " +
                "u.registration_number as registrationNumber, " +
                "u.fleet_id as fleet, " +
                "u.is_network_active as networkActive, " +
                "u.is_network_archived as networkArchived, " +
                "'' as vehicleBase "+
                "FROM unit u " +
                " JOIN map_unit_account muacc ON " +
                "muacc.unit_id = u.id AND  " +
                "NOT u.is_deleted AND " +
                "NOT u.is_network_active AND " +
                "NOT u.is_network_archived AND " +
                "muacc.account_id = ? JOIN unit_type ut on ut.id = u.type_id";
        System.out.println(s);

        Query query = session.createSQLQuery(s).
                addScalar("id", Hibernate.INTEGER).
                addScalar("description", Hibernate.STRING).
                addScalar("registrationNumber", Hibernate.STRING).
                addScalar("type", Hibernate.STRING).
                addScalar("fleet", Hibernate.STRING).
                addScalar("vehicleBase", Hibernate.STRING).
                addScalar("networkActive", Hibernate.BOOLEAN).
                addScalar("networkArchived", Hibernate.BOOLEAN).
                setResultTransformer(Transformers.aliasToBean(VehicleNVDto.class));

        return query.setInteger(0, anPostAccountId).list();
    }
    public List<VehicleNVDto> getActiveNetworkVehicles(int anPostAccountId) {
        Session session = getSession();
        String s = "SELECT " +
                "u.id as id, " +
                "u.descr as description, " +
                "ut.descr as type, " +
                "u.registration_number as registrationNumber, " +
                "u.fleet_id as fleet, " +
                "u.is_network_active as networkActive, " +
                "u.is_network_archived as networkArchived, " +
                "'' as vehicleBase "+
                "FROM unit u " +
                " JOIN map_unit_account muacc ON " +
                "muacc.unit_id = u.id AND  " +
                "NOT u.is_deleted AND " +
                "u.is_network_active AND " +
                "NOT u.is_network_archived AND " +
                "muacc.account_id = ? JOIN unit_type ut on ut.id = u.type_id";
        System.out.println(s);

        Query query = session.createSQLQuery(s).
                addScalar("id", Hibernate.INTEGER).
                addScalar("description", Hibernate.STRING).
                addScalar("registrationNumber", Hibernate.STRING).
                addScalar("type", Hibernate.STRING).
                addScalar("fleet", Hibernate.STRING).
                addScalar("vehicleBase", Hibernate.STRING).
                addScalar("networkActive", Hibernate.BOOLEAN).
                addScalar("networkArchived", Hibernate.BOOLEAN).
                setResultTransformer(Transformers.aliasToBean(VehicleNVDto.class));

        return query.setInteger(0, anPostAccountId).list();
    }
    public List<VehicleNVDto> getArchivedNetworkVehicles(int anPostAccountId) {
        Session session = getSession();
        String s = "SELECT " +
                "u.id as id, " +
                "u.descr as description, " +
                "ut.descr as type, " +
                "u.registration_number as registrationNumber, " +
                "u.fleet_id as fleet, " +
                "u.is_network_active as networkActive, " +
                "u.is_network_archived as networkArchived, " +
                "'' as vehicleBase "+
                "FROM unit u " +
                " JOIN map_unit_account muacc ON " +
                "muacc.unit_id = u.id AND  " +
                "NOT u.is_deleted AND " +
                "NOT u.is_network_active AND " +
                "u.is_network_archived AND " +
                "muacc.account_id = ? JOIN unit_type ut on ut.id = u.type_id";
        System.out.println(s);
        Query query = session.createSQLQuery(s).
                addScalar("id", Hibernate.INTEGER).
                addScalar("description", Hibernate.STRING).
                addScalar("registrationNumber", Hibernate.STRING).
                addScalar("type", Hibernate.STRING).
                addScalar("fleet", Hibernate.STRING).
                addScalar("vehicleBase", Hibernate.STRING).
                addScalar("networkActive", Hibernate.BOOLEAN).
                addScalar("networkArchived", Hibernate.BOOLEAN).
                setResultTransformer(Transformers.aliasToBean(VehicleNVDto.class));

        return query.setInteger(0, anPostAccountId).list();
    }
}

