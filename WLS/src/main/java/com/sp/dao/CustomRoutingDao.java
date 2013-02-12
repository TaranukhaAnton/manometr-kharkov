package com.sp.dao;

import com.sp.dto.report.CurIncomingLogStat;
import com.sp.dto.report.MonthlyDeviceSection;
import com.sp.model.Reseller;
import com.sp.util.CustomRoutingDataSource;
import com.sp.util.TimePeriod;
import com.sp.util.Util;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.jdbc.object.SqlQuery;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.*;

/**
 * Created by Alexander
 */
public class CustomRoutingDao extends SimpleJdbcDaoSupport {
    private Set<SqlQuery> findCurIncomingLogStatQuerySet = new HashSet<SqlQuery>();
    private SqlQuery findMonthlyServiceReportQuery;

    public void setDataSourceExt(CustomRoutingDataSource dataSource) {
        super.setDataSource(dataSource);
        findMonthlyServiceReportQuery = new FindMonthlyServiceReportQuery(dataSource);
        findCurIncomingLogStatQuerySet.add(new IncomingLogDao.FindCurIncomingLogStatQuery(dataSource.getDefaultDataSource()));
        for(BasicDataSource source : dataSource.getTargetDataSourceMap().values()){
            findCurIncomingLogStatQuerySet.add(new IncomingLogDao.FindCurIncomingLogStatQuery(source));
        }
    }

    public List<CurIncomingLogStat> findCurIncomingLogStat(int periodStartMoveBackDays){
        List<CurIncomingLogStat> curIncomingLogStats = new ArrayList<CurIncomingLogStat>();
        for(SqlQuery query : findCurIncomingLogStatQuerySet){
             curIncomingLogStats.addAll(query.execute(new Object[] { periodStartMoveBackDays }));
        }
        return curIncomingLogStats;
    }

	public Collection<TimePeriod> findAllActiveImeiDates() {
        final Map<Integer, List<TimePeriod>> imeiPeriodsMap = new HashMap<Integer, List<TimePeriod>>();
        String sql = "select ilh.imei_id, ilh.created_date, l.activation_date, l.end_date, ilh.license_id " +
                " from imei_license_history ilh " +
                " left join license l on ilh.license_id = l.id " +
                " join account a on a.id = l.account_id" +
                " join reseller r on r.id = a.reseller_id " +
                " where l.activation_date is not null " +
                " and l.end_date is not null " +
                " and ilh.license_id is not null " +
                " and r.is_deleted = false " +
                " order by ilh.imei_id, ilh.created_date asc";
		getJdbcTemplate().query(sql,new ParameterizedRowMapper<Object>() {
            public Object mapRow(ResultSet rs, int row) throws SQLException {
                Integer imeiId = rs.getInt(1);
                Date createdDate = rs.getDate(2);
                Date activationDate = rs.getDate(3);
                Date endDate = rs.getDate(4);
                Integer licenseId = rs.getInt(5);

                List<TimePeriod> periods = imeiPeriodsMap.get(imeiId);
                if(periods == null){
                    imeiPeriodsMap.put(imeiId, periods = new ArrayList<TimePeriod>());
                }

                if(periods.size() > 0){
                    TimePeriod lastPeriod = periods.get(periods.size()-1);
                    lastPeriod.setEndDate( Util.min(lastPeriod.getEndDate(), createdDate) );
                    if(lastPeriod.getEndDate().getTime() <= lastPeriod.getStartDate().getTime()){
                        periods.remove(periods.size()-1);
                    }
                }

                if(licenseId != null){
                    Date startDate = Util.max(activationDate, createdDate);
                    if(endDate.getTime() > startDate.getTime())
                    {
                        TimePeriod newPeriod = new TimePeriod();
                        newPeriod.setStartDate(startDate);
                        newPeriod.setEndDate(endDate);
                        periods.add(newPeriod);
                    }
                }
                return null;
            }
        });
		Collection<TimePeriod> result = new HashSet<TimePeriod>();
		for (List<TimePeriod> timePeriods : imeiPeriodsMap.values()){
			result.addAll(timePeriods);
		}
		return result;
	}
   
    public Reseller findResellerForMonthleyRportById(int resellerId){
        String sql = "select descr,billing_contact_name,billing_contact_email,billing_contact_phone,address_line1,address_line2,address_line3,address_line4,postcode,country" +
                     " from reseller" +
                     " where id = ?";
                         return getSimpleJdbcTemplate().queryForObject(sql, new ParameterizedRowMapper<Reseller>() {
                             public Reseller mapRow(ResultSet rs, int row) throws SQLException {
                                Reseller reseller = new Reseller();
                                reseller.setDescr(rs.getString("descr"));
                                reseller.setBillingContactName(rs.getString("billing_contact_name"));
                                reseller.setBillingContactEmail(rs.getString("billing_contact_email"));
                                reseller.setBillingContactPhone(rs.getString("billing_contact_phone"));
                                reseller.setAddressLine1(rs.getString("address_line1"));
                                reseller.setAddressLine2(rs.getString("address_line2"));
                                reseller.setAddressLine3(rs.getString("address_line3"));
                                reseller.setAddressLine4(rs.getString("address_line4"));
                                reseller.setPostcode(rs.getString("postcode"));
                                reseller.setCountry(rs.getString("country")); 
                                return reseller;
                            }
                        }, resellerId);
    }


    public Collection<MonthlyDeviceSection> findResellersActiveDevices(int resellerId, TimePeriod timePeriod) {
        Object[] params = {timePeriod.getStartDate(), timePeriod.getEndDate(), resellerId};
        return findMonthlyServiceReportQuery.execute(params);


    }

    public static class FindMonthlyServiceReportQuery extends MappingSqlQuery {
        public FindMonthlyServiceReportQuery(DataSource ds) {
            super(ds, "call monthly_service_report(?, ?, ?)");
            declareParameter(new SqlParameter("startDate", Types.TIMESTAMP));
            declareParameter(new SqlParameter("endDate", Types.TIMESTAMP));
            declareParameter(new SqlParameter(Types.INTEGER));
            compile();
        }


        protected Object mapRow(ResultSet rs, int rowNumber) throws SQLException {
            MonthlyDeviceSection result = new MonthlyDeviceSection();
            result.setPartnerId(rs.getInt(1));
            result.setPartner(rs.getString(2));
            result.setAccountCount(rs.getInt(3));
            result.setDeviceCount(rs.getInt(4));
            return result;
        }
    }
}
